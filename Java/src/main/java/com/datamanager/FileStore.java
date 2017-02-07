package com.datamanager;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

/**
 * @author : pgajjar
 * @since  : 2/6/17
 */
public class FileStore <T> {
    private final Multimap<String, T> fileStore = HashMultimap.create();
    private static FileStore handle;

    private FileStore() {}

    @NonNull
    public synchronized static FileStore getHandle() {
        return (handle == null) ? (handle = new FileStore()) : handle;
    }

    public boolean put(@NonNull final String key, @NonNull final T value) {
        return fileStore.put(key, value);
    }

    public boolean putAll(Multimap<? extends String, ? extends T> map) {
        return fileStore.putAll(map);
    }

    @NonNull
    public Collection<T> get(@NonNull final String key) {
        return fileStore.get(key);
    }

    @NonNull
    public Collection<T> get(@NonNull final T data) {
        if (data instanceof DataFile) {
            return fileStore.get(((DataFile) data).md5());
        }
        throw new IllegalArgumentException("Only DataFile object is supported for now here.");
    }

    public boolean keyPresent(@NonNull final String key) {
        return fileStore.containsKey(key);
    }

    public boolean put(@NonNull final T data) {
        if (data instanceof DataFile) {
            DataFile dataFile = (DataFile) data;
            return put(dataFile.md5(), data);
        }
        throw new IllegalArgumentException("Only DataFile object is supported for now here.");
    }

    public boolean isExists(@NonNull final T data) {
        if (data instanceof DataFile) {
            return fileStore.containsKey(((DataFile) data).md5());
        }
        throw new IllegalArgumentException("Only DataFile object is supported for now here.");
    }

    public int getDuplicates(@NonNull final String key) {
        return fileStore.get(key).size();
    }

    public int getDuplicates(@NonNull final T data) {
        if (data instanceof DataFile) {
            return fileStore.get(((DataFile) data).md5()).size();
        }
        throw new IllegalArgumentException("Only DataFile object is supported for now here.");
    }

    public int fileCount() {
        return fileStore.size();
    }

    public int distinctFileCount() {
         return keySet().size();
    }

    @NonNull
    public Collection<String> keySet() {
        return fileStore.keySet();
    }
}
