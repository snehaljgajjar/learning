package com.tengen.utils;

import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: pgajjar Date: 8/9/13 Time: 12:45 AM
 */
public interface MongoDBUtils {
    void connect(String hostName, Integer portNo) throws UnknownHostException;

    @SuppressWarnings("unused")
    void reconnect(String hostName, Integer portNo) throws UnknownHostException;

    @SuppressWarnings("unused")
    void reconnect() throws UnknownHostException;

    @SuppressWarnings("unused")
    DBObject fetchOne(String dbName, String collectionName);

    <T> Collection<T> fetchAll(String dbName, String collectionName, Class<T> clazz);
}
