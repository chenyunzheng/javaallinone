package com.andrew.vsb;

import com.andrew.vsb.webserver.WebServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author chrischen
 */
public class VSpringApplication {

    public static void run(Class<?> clazz) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();

        startWebServer(applicationContext);
    }

    private static void startWebServer(AnnotationConfigApplicationContext applicationContext) {
        Map<String, WebServer> beansOfType = applicationContext.getBeansOfType(WebServer.class);
        if (beansOfType.size() == 0) {
            throw new IllegalStateException("no web server found");
        }
        if (beansOfType.size() > 1) {
            throw new IllegalStateException("more than one web server found");
        }
        beansOfType.values().stream().findFirst().get().start();
    }
}
