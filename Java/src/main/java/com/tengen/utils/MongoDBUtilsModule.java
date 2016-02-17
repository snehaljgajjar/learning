package com.tengen.utils;

import com.google.inject.AbstractModule;

/**
 * User: pgajjar Date: 8/9/13 Time: 12:27 AM
 */
public class MongoDBUtilsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MongoDBUtils.class).to(MongoDBUtilsImpl.class);
    }
}
