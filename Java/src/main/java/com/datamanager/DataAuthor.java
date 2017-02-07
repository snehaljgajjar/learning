package com.datamanager;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author : pgajjar
 * @since  : 2/6/17
 */
public class DataAuthor <T> {
    private final ObjectOutputStream writer;
    private final ObjectInputStream reader;

    private DataAuthor(@NonNull final String filePath) throws IOException {
        this.writer = new ObjectOutputStream(new FileOutputStream(filePath));
        this.reader = new ObjectInputStream(new FileInputStream(filePath));
    }

    @NonNull
    public static DataAuthor getInstance(@NonNull final String filePath) throws IOException {
        return new DataAuthor(filePath);
    }

    public void write(@Nullable final T data) throws IOException {
        if (data != null) {
            this.writer.writeObject(data);
        }
    }

    @NonNull
    public T read() throws IOException, ClassNotFoundException {
        return (T) this.reader.readObject();
    }
}
