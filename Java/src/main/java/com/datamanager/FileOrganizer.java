package com.datamanager;

import com.datamanager.actions.DataManagerAction;
import com.datamanager.actions.DataManagerActionFactory;
import com.google.common.base.CharMatcher;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.datamanager.actions.DataManagerActionFactory.Type;

/**
 * @author : pgajjar
 * @since  : 2/6/17
 */
public class FileOrganizer {
    private static final Logger logger = Logger.getLogger(FileOrganizer.class.getName());
    private @NonNull final File directory;
    private @NonNull final FileStore fileStore = FileStore.getHandle();
    private @Nullable FileStore duplicateFileStore;
    private @NonNull final String reportFile;
    private final boolean shouldAttemptRename;

    private static final CharMatcher ALPHA_NUMERIC_MATCHER = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.inRange('0', '9')).or(CharMatcher.anyOf(". ")).precomputed();

    private static FilenameFilter validFilesFilter() {
        return (dir, name) -> {
            final String fileName = name.toLowerCase();
            return fileName.endsWith(".txt") ||
                    fileName.endsWith(".pdf") ||
                    fileName.endsWith(".mov") ||
                    fileName.endsWith(".mp4") ||
                    fileName.endsWith(".mpeg") ||
                    fileName.endsWith(".mpg") ||
                    fileName.endsWith(".avi") ||
                    fileName.endsWith(".m4v") ||
                    fileName.endsWith(".jpg") ||
                    fileName.endsWith(".png") ||
                    fileName.endsWith(".jpeg") ||
                    fileName.endsWith(".epub") ||
                    fileName.endsWith(".csv") ||
                    fileName.endsWith(".chm");
        };
    }

    public FileOrganizer(@NonNull final String directory) throws IOException {
        this(directory, directory + File.separator + "report.csv");
    }

    public FileOrganizer(@NonNull final String directory, @NonNull final String reportFile) throws IOException {
        this(directory, reportFile, false);
    }

    public FileOrganizer(@NonNull final String directory, @NonNull final String reportFile, boolean shouldAttemptRename) throws IOException {
        this.directory = new File(directory);
        if (isSymlink(this.directory)) {
            throw new IllegalArgumentException("Looks like " + directory + " is symlink, can't work on symlink for now.");
        }
        this.reportFile = reportFile;
        this.shouldAttemptRename = shouldAttemptRename;
    }

    public void process() throws IOException {
        createFileStore(directory);
        duplicateFileStore = FileStore.getDuplicateFileHandle();
        if (duplicateFileStore == null) {
            throw new IllegalStateException("FileStore is found in bad state, main FileStore is not yet constructed and trying to get the Dup FileStore.");
        }
        logger.info("Total " + fileStore.fileCount() + " found, [Unique Files: " + fileStore.distinctFileCount() + "] files in directory: " + directory.getAbsolutePath());
        processFileStore();
    }

    private void createFileStore(@NonNull final File directory) throws IOException {
        logger.info("Processing directory \t\"" + directory.getAbsolutePath() + "\"");
        if (!isSymlink(directory)) {
            for (final File fileEntry : directory.listFiles(validFilesFilter())) {
                if (fileEntry.isDirectory()) {
                    createFileStore(fileEntry);
                } else {
                    final DataFile dataFile = new DataFile(renameFileIfRequired(fileEntry));
                    fileStore.put(dataFile.md5(), dataFile.toString());
                }
            }
        } else {
            logger.info("Ignoring symlink: " + directory.getAbsolutePath() + " -> " + directory.getCanonicalPath());
        }
    }

    @NonNull
    private File renameFileIfRequired(@NonNull final File file) {
        if (shouldAttemptRename) {
            final String fileName = file.getName();
            if (!ALPHA_NUMERIC_MATCHER.matchesAllOf(fileName)) {
                final String newFileName = ALPHA_NUMERIC_MATCHER.retainFrom(fileName);
                final File newFile = new File(file.getParent(), newFileName);
                file.renameTo(newFile);
                return newFile;
            }
        }
        return file;
    }

    private void processFileStore() throws IOException {
        logger.info("Computing fileStore to find the duplicate files.");
        DataManagerAction actionHandle = DataManagerActionFactory.getInstance(Type.WriteReport, duplicateFileStore, this.reportFile);
        if (actionHandle != null) {
            actionHandle.action();
            actionHandle.stop();
            logger.info("Wrote a duplicate file report to file: " + this.reportFile);
        }
    }

    public static boolean isSymlink(@NonNull final File directory) throws IOException {
        return Files.isSymbolicLink(Paths.get(directory.getAbsolutePath()));
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args.length > 3) {
            System.out.println("usage: FileOrganizer <Directory> [Report File (optional)] [shouldAttemptRename flag (true/false)]");
            System.exit(0);
        }

        final FileOrganizer organizer;
        switch (args.length) {
            case 2:
                organizer = new FileOrganizer(args[0], args[1]);
                break;
            case 3:
                organizer = new FileOrganizer(args[0], args[1], Boolean.valueOf(args[2]));
                break;
            case 1:
            default:
                organizer = new FileOrganizer(args[0]);
                break;
        }
        if (organizer != null) {
            organizer.process();
        }
    }
}
