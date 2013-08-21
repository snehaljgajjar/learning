package com.tengen.spark;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tengen.HelloWorldMongoDBStyle;
import com.tengen.utils.MongoDBUtilsModule;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * User: pgajjar Date: 8/16/13 Time: 3:34 PM
 */
public class HelloWorldSparkFreeMarkerStyle {
    private static final String HOST_NAME = "localhost";
    private static final String PORT_NUMBER = "27017";
    private static final String DATABASE_NAME = "course";
    private  static final String COLLECTION_NAME = "hello";
    private final Injector injector = Guice.createInjector(new MongoDBUtilsModule());
    HelloWorldMongoDBStyle hello = injector.getInstance(HelloWorldMongoDBStyle.class);
    private final Template template;
    private static final String FIRST_NAME = "Pradip";
    private static final String LAST_NAME = "Gajjar";

    public HelloWorldSparkFreeMarkerStyle() throws IOException {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreeMarkerStyle.class, "/");
        template = configuration.getTemplate("hellospark.ftl");
        hello.connect(HOST_NAME, Integer.valueOf(PORT_NUMBER));
    }

    private Object getHtmlPage(Map<String, Object> map) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        template.process(map, writer);
        return writer;
    }

    private Object constructPage(String msg) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("firstname", FIRST_NAME);
        map.put("lastname", LAST_NAME);
        map.put("msg", msg);
        try {
            return getHtmlPage(map);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TemplateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private void constructHelloPage() {
        Spark.get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return constructPage("Hello " + FIRST_NAME + " " + LAST_NAME);
            }
        });
    }

    private void constructInvalidPage() {
        Spark.get(new Route("*") {
            @Override
            public Object handle(Request request, Response response) {
                return constructPage(request.url() + " doesn't exist.");
            }
        });
    }

    private void constructMongoDataPage() {
        Spark.get(new Route("/mongo") {
            @Override
            public Object handle(Request request, Response response) {
                return constructPage(hello.retrieveNamesInHtmlTableForm(DATABASE_NAME, COLLECTION_NAME));
            }
        });
    }

    public void populateAllPages() {
        constructHelloPage();
        constructMongoDataPage();
        constructInvalidPage();
    }

    public static void main(String[] args) throws IOException {
        HelloWorldSparkFreeMarkerStyle helloWorldSparkFreeMarkerStyle = new HelloWorldSparkFreeMarkerStyle();
        helloWorldSparkFreeMarkerStyle.populateAllPages();
    }
}
