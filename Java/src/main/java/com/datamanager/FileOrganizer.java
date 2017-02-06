package com.datamanager;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author : pgajjar
 * @since  : 2/6/17
 */
public class FileOrganizer {
    private final File directory;
    private final Multimap<String, String> fileStore = HashMultimap.create();

    public FileOrganizer(@NonNull final String directory) {
        this.directory = new File(directory);
    }

    public void process() throws IOException {
        createFileStore(directory);
        // dumpFileStore();
        processFileStore();
    }

    private void dumpFileStore() {
        for (String md5 : fileStore.keySet()) {
            for (String dataFile : fileStore.get(md5)) {
                System.out.println(dataFile);
            }
        }
    }

    private void createFileStore(@NonNull final File directory) throws IOException {
        for (final File fileEntry : directory.listFiles()) {
            if (fileEntry.isDirectory()) {
                createFileStore(fileEntry);
            } else {
                final DataFile dataFile = new DataFile(fileEntry);
                fileStore.put(dataFile.md5(), dataFile.toString());
                //System.out.println(dataFile);
            }
        }
    }

    private void processFileStore() {
        for (String md5 : fileStore.keySet()) {
            final Collection<String> dataFiles = fileStore.get(md5);
            if (dataFiles.size() > 1) {
                for (String dupFile : dataFiles) {
                    System.out.println("DUP (" + md5 + "): " + dupFile);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("usage: FileOrganizer <Directory> <ReportFile>");
            System.exit(0);
        }

        final FileOrganizer organizer = new FileOrganizer(args[0]);
        organizer.process();
    }
}
