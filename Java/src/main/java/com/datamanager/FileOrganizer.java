package com.datamanager;

import com.datamanager.actions.DataManagerAction;
import com.datamanager.actions.DataManagerActionFactory;
import com.google.common.base.CharMatcher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

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
    private @NonNull String reportFile;
    private boolean shouldAttemptRename;

    private static final CharMatcher ALPHA_NUMERIC_MATCHER = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.inRange('0', '9')).or(CharMatcher.anyOf(". ")).precomputed();

    private String[] validFileExntensions;

    public FileOrganizer(@NonNull final String directory) throws IOException {
        this.directory = new File(directory);
        if (isSymlink(this.directory)) {
            throw new IllegalArgumentException("Looks like " + directory + " is symlink, can't work on symlink for now.");
        }

        loadProperties();
        this.reportFile = (this.reportFile != null) ? this.reportFile : directory + File.separator + "/report.csv";
    }

    private void loadProperties() throws IOException {
        // read properties
        final Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("configuration.properties"));
        final String validFilerExtensions = properties.getProperty("validfilerextensions");
        if (validFilerExtensions != null) {
            final String[] extensions = validFilerExtensions.replaceAll("\\s+", "").split(",");
            final String[] uppercaseExts = Arrays.stream(extensions).map(p -> p.toUpperCase()).toArray(s -> new String[s]);
            this.validFileExntensions = ArrayUtils.addAll(extensions, uppercaseExts);
        }

        this.reportFile = properties.getProperty("reportfile");
        this.shouldAttemptRename = Boolean.valueOf(properties.getProperty("shouldattemptrename"));
    }

    public void process() throws IOException {
        createFileStore(directory);
        duplicateFileStore = FileStore.getDuplicateFileHandle();
        if (duplicateFileStore == null) {
            throw new IllegalStateException("FileStore is found in bad state, main FileStore is not yet constructed and trying to get the Dup FileStore.");
        }
        processFileStore();
    }

//    private void createFileStore(@NonNull final File directory) throws IOException {
//        logger.info("Processing directory \t\"" + directory.getAbsolutePath() + "\"");
//        if (!isSymlink(directory)) {
//            for (final File fileEntry : directory.listFiles(validFilesFilter(true))) {
//                if (fileEntry.isDirectory()) {
//                    createFileStore(fileEntry);
//                } else {
//                    final DataFile dataFile = new DataFile(renameFileIfRequired(fileEntry));
//                    fileStore.put(dataFile.md5(), dataFile.toString());
//                }
//            }
//        } else {
//            logger.info("Ignoring symlink: " + directory.getAbsolutePath() + " -> " + directory.getCanonicalPath());
//        }
//    }

    private void createFileStore(@NonNull final File directory) throws IOException {
        logger.info("Processing directory \t\"" + directory.getAbsolutePath() + "\"");
        if (!isSymlink(directory)) {
            for (final File fileEntry : FileUtils.listFiles(directory, validFileExntensions, true)) {
                final DataFile dataFile = new DataFile(renameFileIfRequired(fileEntry));
                fileStore.put(dataFile.md5(), dataFile.toString());
            }
            logger.info("Total " + fileStore.fileCount() + ", [Unique Files: " + fileStore.distinctFileCount() + "] files found in directory: " + directory.getAbsolutePath());
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
        if (args.length != 1) {
            System.out.println("usage: FileOrganizer <Directory>");
            System.exit(0);
        }

        final FileOrganizer organizer = new FileOrganizer(args[0]);
        organizer.process();
    }
}
