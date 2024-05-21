package com.andrew.vsb.webserver;

/**
 * @author chrischen
 */
public class TomcatWebServer implements WebServer{
    @Override
    public void start() {
        System.out.println("TomcatWebServer...");
    }
}
