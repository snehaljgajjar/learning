package com.utils;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author : pgajjar
 * @since  : 2/7/17
 * TODO: Not working - need to understand and implement it.
 */
public class PropertiesMap<T> implements Map {
    private static final Logger logger = Logger.getLogger(PropertiesMap.class.getName());
    private static PropertiesMap propertiesMap;

    private final Map<T, Object> properties = Maps.newHashMap();

    public PropertiesMap(@NonNull final Properties... properties) {
        for (Properties property : properties) {
            loadProperties(property);
        }
    }

    public PropertiesMap(@NonNull final String... propertyFileNames) {
        for (String property : propertyFileNames) {
            final Properties properties = new Properties();
            try {
                properties.load(getClass().getClassLoader().getResourceAsStream(property));
                loadProperties(properties);
            } catch (IOException e) {
                logger.info("Couldn't load the property file: " + property);
                continue;
            }
        }
    }

    private void loadProperties(@NonNull final Properties property) {
        //properties.putAll(Maps.fromProperties(property));
    }

    @Override public int size() {
        return properties.size();
    }

    @Override public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    @Override public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    @Override public Object get(Object key) {
        return properties.get(key);
    }

    @Override public Object put(Object key, Object value) {
        return null;
    }

    @Override public Object remove(Object key) {
        return null;
    }

    @Override public void putAll(@NotNull Map m) {
        // do nothing
    }

    @Override public void clear() {
        // do nothing
    }

    @NotNull @Override public Set<T> keySet() {
        return properties.keySet();
    }

    @NotNull @Override public Collection<Object> values() {
        return properties.values();
    }

    @NotNull @Override public Set<Entry<T, Object>> entrySet() {
        return properties.entrySet();
    }
}
