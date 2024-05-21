package com.andrew.vsb.webserver;

/**
 * @author chrischen
 */
public class JettyWebServer implements WebServer{
    @Override
    public void start() {
        System.out.println("JettyWebServer...");
    }
}
