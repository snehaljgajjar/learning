package com.tengen;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tengen.utils.MongoDBUtilsModule;
import junit.framework.TestCase;

/**
 * User: pgajjar Date: 8/14/13 Time: 2:49 PM
 */
public class HelloWorldMongoDBStyleTest extends TestCase {
    private static final String HOST_NAME = "localhost";
    private static final String PORT_NUMBER = "27017";
    private static final String DATABASE_NAME = "course";
    private  static final String COLLECTION_NAME = "hello";

    Injector injector = Guice.createInjector(new MongoDBUtilsModule());
    HelloWorldMongoDBStyle hello = injector.getInstance(HelloWorldMongoDBStyle.class);

    public HelloWorldMongoDBStyleTest() {
        hello.connect(HOST_NAME, Integer.valueOf(PORT_NUMBER));
    }

    public void testFetchedValues() {
        System.out.println(hello.retrieveNames(DATABASE_NAME, COLLECTION_NAME));
    }
}
