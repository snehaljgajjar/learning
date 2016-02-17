package com.tengen.spark;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return "You are at root...";
            }
        });
        Spark.get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!!!";
            }
        });
        Spark.get(new Route("*") {
            @Override
            public Object handle(Request request, Response response) {
                return "\"" + request.url() + "\" doesn't exists.";
            }
        });
    }
}
