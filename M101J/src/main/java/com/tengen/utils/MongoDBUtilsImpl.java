package com.tengen.utils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * User: pgajjar Date: 8/7/13 Time: 1:59 PM
 */
public final class MongoDBUtilsImpl implements MongoDBUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private MongoClient client = null;
    private String hostName;
    private Integer portNo;

    @Inject
    public MongoDBUtilsImpl() {
    }

    public void connect(String hostName, Integer portNo) throws UnknownHostException {
        try {
            client = new MongoClient(new ServerAddress(hostName, portNo));
            this.hostName = hostName;
            this.portNo = portNo;
        } catch (UnknownHostException e) {
            System.out.println("Invalid Host / Port: " + hostName + ":" + portNo);
            throw e;
        }
    }

    public void reconnect(String hostName, Integer portNo) throws UnknownHostException {
        connect(hostName, portNo);
    }

    public void reconnect() throws UnknownHostException {
        connect(this.hostName, this.portNo);
    }

    public DBObject fetchOne(String dbName, String collectionName) {
        DB database = client.getDB(dbName);
        DBCollection collection = database.getCollection(collectionName);
        return collection.findOne();
    }

    public <T> Collection<T> fetchAll(String dbName, String collectionName, Class<T> clazz) {
        DB database = client.getDB(dbName);
        DBCollection collection = database.getCollection(collectionName);
        DBCursor dbCursor = collection.find();
        Collection<T> records = Lists.newArrayList();
        try {
            while (dbCursor.hasNext()) {
                T name = gson.fromJson(dbCursor.next().toString(), clazz);
                records.add(name);
            }
        } finally {
            dbCursor.close();
        }
        return records;
    }
}
