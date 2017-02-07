package com.datamanager.actions;

import com.datamanager.FileStore;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @author : pgajjar
 * @since  : 2/6/17
 */
public class DataManagerReportAction <T> implements DataManagerAction {
    private static final Logger logger = Logger.getLogger(DataManagerReportAction.class.getName());
    private final PrintWriter writer;
    private final FileStore<T> fileStore;

    DataManagerReportAction(@NonNull final FileStore fileStore, @NonNull final String reportFile) throws FileNotFoundException {
        this.fileStore = fileStore;
        this.writer = new PrintWriter(new File(reportFile));
    }

    @Override
    public void action() {
        long dupCount = 0;
        for (String md5 : fileStore.keySet()) {
            final Collection<T> dataFiles = fileStore.get(md5);
            if (dataFiles.size() > 1) {
                dupCount++;
                // definite duplicates
                writer.println(md5);
                for (T dupFile : dataFiles) {
                    // System.out.println("DUP (" + md5 + "): " + dupFile);
                    writer.println(dupFile);
                }
                writer.println();
            } else {
                // TODO: do more comparision with file name, file type and file size etc. to determine the possible duplicate
            }
        }
        logger.info("Found " + dupCount + " duplicate files.");
    }

    @Override
    public void stop() {
        writer.close();
    }
}
