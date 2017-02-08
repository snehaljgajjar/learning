package com.datamanager;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : pgajjar
 * @since  : 2/6/17
 */
public class FileStore<T extends DataFile> {
    private final Multimap<String, T> fileStore = HashMultimap.create();
    private final Multimap<String, String> fileTypeStore = HashMultimap.create();
    private static FileStore handle;
    private static FileStore duplicateHandle;

    private FileStore() {
    }

    @NonNull
    public synchronized static FileStore getHandle() {
        return (handle == null) ? (handle = new FileStore()) : handle;
    }

    @Nullable
    public synchronized static FileStore getDuplicateFileHandle() {
        if (handle != null) {
            if (duplicateHandle == null) {
                duplicateHandle = new FileStore();
                Set<? extends String> keys = handle.fileStore.keySet();
                for (String key : keys) {
                    Collection values = handle.fileStore.get(key);
                    if (values.size() > 1) {
                        // definite duplicates.
                        duplicateHandle.put(key, values);
                    } else {
                        // TODO: other ways of duplication goes here.
                    }
                }
            }
        }
        return duplicateHandle;
    }

    public boolean put(@NonNull final String key, @NonNull final T value) {
        fileTypeStore.put(value.fileType(), key);
        return fileStore.put(key, value);
    }

    public void put(@NonNull final String key, @NonNull final Collection<T> values) {
        for (T value : values) {
            put(key, value);
        }
    }

    public void putAll(Multimap<? extends String, ? extends T> map) {
        for (Map.Entry<? extends String, ? extends T> entry : map.entries()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean put(@NonNull final T data) {
        return put(data.md5(), data);
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

    public int duplicateFileCount() {
        return fileCount() - distinctFileCount();
    }

    @NonNull
    public Collection<String> keySet() {
        return fileStore.keySet();
    }

    @NonNull
    public Collection<String> fileTypes() {
        return fileTypeStore.keySet();
    }

    @NonNull
    public Collection<T> filesOfType(@NonNull final String key) {
        return fileTypeStore.get(key).stream().map(k -> fileStore.get(k)).flatMap(Collection::stream).collect(Collectors.toList());
        // final Collection<T> files = Lists.newArrayList();
        //fileTypeStore.get(key).stream().map(k -> files.addAll(fileStore.get(k)));
        //return files;
    }
}
