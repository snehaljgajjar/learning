package com.datamanager;

import com.datamanager.actions.DataManagerAction;
import com.datamanager.actions.DataManagerActionFactory;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
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

    public FileOrganizer(@NonNull final String directory) throws IOException {
        this(directory, directory + File.separator + "report.csv");
    }

    public FileOrganizer(@NonNull final String directory, @NonNull final String reportFile) throws IOException {
        this.directory = new File(directory);
        if (isSymlink(this.directory)) {
            throw new IllegalArgumentException("Looks like " + directory + " is symlink, can't work on symlink for now.");
        }
        this.reportFile = reportFile;
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
        logger.info("Processing \t\"" + directory.getAbsolutePath() + "\"");
        if (!isSymlink(directory)) {
            for (final File fileEntry : directory.listFiles()) {
                if (fileEntry.isDirectory()) {
                    createFileStore(fileEntry);
                } else {
                    final DataFile dataFile = new DataFile(fileEntry);
                    fileStore.put(dataFile.md5(), dataFile);
                }
            }
        } else {
            logger.info("Ignoring symlink: " + directory.getAbsolutePath() + " -> " + directory.getCanonicalPath());
        }
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
        if (args.length < 1 || args.length > 2) {
            System.out.println("usage: FileOrganizer <Directory> [Report File (optional)]");
            System.exit(0);
        }

        final FileOrganizer organizer = (args.length == 2) ? new FileOrganizer(args[0], args[1]) : new FileOrganizer(args[0]);
        organizer.process();
    }
}
