package com.tengen;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.tengen.utils.MongoDBUtils;
import com.tengen.utils.MongoDBUtilsModule;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * User: pgajjar Date: 8/7/13 Time: 12:53 AM
 */
public class HelloWorldMongoDBStyle {
    private static final String HELLO_WORLD_CSS_STYLE_1 = "<style type=\"text/css\">\n" +
            "table.helloWorld {background-color:transparent;border-collapse:collapse;}\n" +
            "table.helloWorld td {border:1px solid black;padding:5px;width:50%;}\n" +
            "</style>";
    private final MongoDBUtils mongoDBUtils;

    @Inject
    public HelloWorldMongoDBStyle(MongoDBUtils mongoDBUtils) {
        this.mongoDBUtils = mongoDBUtils;
    }

    @SuppressWarnings("unused")
    public final class MongoObjectId {
        private final String $oid;

        private MongoObjectId(String $oid) {
            this.$oid = $oid;
        }

        public String getId() {
            return $oid;
        }
    }

    @SuppressWarnings("unused")
    public final class Name {
        private MongoObjectId _id;
        private String name;

        public String getObjectId() {
            return _id.getId();
        }

        public String getName() {
            return name;
        }
    }

    public void connect(String hostName, Integer portNo) {
        try {
            mongoDBUtils.connect(hostName, portNo);
        } catch (UnknownHostException e) {
            System.out.println("Invalid Host / Port: " + hostName + ":" + portNo);
            System.exit(-1);
        }
    }

    public String retrieveNames(String dbName, String collectionName) {
        Collection<Name> records = mongoDBUtils.fetchAll(dbName, collectionName, Name.class);
        StringBuilder dataBuffer = new StringBuilder();
        for (Name record : records) {
            dataBuffer.append("Name: ").append(record.getName()).append(" Object ID: ").append(record.getObjectId()).append(System.getProperty("line.separator"));
        }
        return dataBuffer.toString();
    }

    public String retrieveNamesInHtmlTableForm(String dbName, String collectionName) {
        Collection<Name> records = mongoDBUtils.fetchAll(dbName, collectionName, Name.class);
        StringBuilder dataBuffer = new StringBuilder();
        dataBuffer.append(HELLO_WORLD_CSS_STYLE_1);
        dataBuffer.append("<table class=\"helloWorld\">");
        dataBuffer.append("<tr>");
        dataBuffer.append("<td>");
        dataBuffer.append("<b>Object Id<b>");
        dataBuffer.append("</td>");
        dataBuffer.append("<td>");
        dataBuffer.append("<b>Name</b>");
        dataBuffer.append("</td>");
        dataBuffer.append("</tr>");
        for (Name record : records) {
            dataBuffer.append("<tr>");
            dataBuffer.append("<td>");
            dataBuffer.append(record.getObjectId());
            dataBuffer.append("</td>");
            dataBuffer.append("<td>");
            dataBuffer.append(record.getName());
            dataBuffer.append("</td>");
            dataBuffer.append("</tr>");
        }
        dataBuffer.append("</table>");
        return dataBuffer.toString();
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: HelloWorldMongoDBStyle <Mongod Server Host> <Mongod Server Port> <Database name> <Collection Name>");
            System.exit(-1);
        }

        Injector injector = Guice.createInjector(new MongoDBUtilsModule());
        HelloWorldMongoDBStyle hello = injector.getInstance(HelloWorldMongoDBStyle.class);
        hello.connect(args[0], Integer.valueOf(args[1]));
        System.out.println(hello.retrieveNames(args[2], args[3]));
    }
}
