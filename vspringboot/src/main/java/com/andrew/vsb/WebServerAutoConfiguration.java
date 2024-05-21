package com.andrew.vsb;

import com.andrew.vsb.annotation.ConditionalOnClass;
import com.andrew.vsb.webserver.JettyWebServer;
import com.andrew.vsb.webserver.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chrischen
 */
@Configuration
public class WebServerAutoConfiguration implements AutoConfiguration {

    @Bean
    @ConditionalOnClass("org.apache.catalina.startup.Tomcat")
    public TomcatWebServer tomcatWebServer() {
        return new TomcatWebServer();
    }

    @Bean
    @ConditionalOnClass("org.eclipse.jetty.server.Server")
    public JettyWebServer jettyWebServer() {
        return new JettyWebServer();
    }
}
