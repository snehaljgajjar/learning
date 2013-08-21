package freemarker;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * User: pgajjar Date: 8/16/13 Time: 3:01 PM
 */
public class HelloWorldFreeMarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreeMarkerStyle.class, "/");

        try {
            Template template = configuration.getTemplate("hello.ftl");
            Map<String, Object> helloMap = Maps.newHashMap();
            helloMap.put("firstname", "pradip");
            helloMap.put("lastname", "gajjar");
            StringWriter writer = new StringWriter();
            template.process(helloMap, writer);

            System.out.println(writer);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TemplateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
