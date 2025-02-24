package com.chris.allinone.solution.dynamicload.plugindemo;

import jdk.internal.loader.Resource;
import jdk.internal.loader.URLClassPath;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;

/**
 * @author chrischen
 * @date 2025/2/17
 * @desc TODO描述主要功能
 */
public class PluginClassLoader extends URLClassLoader {

    /* The search path for classes and resources */
    private final URLClassPath ucp;

    /* The context to be used when loading classes and resources */
    private final AccessControlContext acc;

    public PluginClassLoader(String name, URL[] urls) {
        this(name, urls, ClassLoader.getSystemClassLoader());
    }

    public PluginClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
        this.acc = AccessController.getContext();
        this.ucp = new URLClassPath(urls, acc);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fullClassName = name.replace('.', '/').concat(".class");
        Resource resource = ucp.getResource(fullClassName, false);
        if (resource != null) {
            try {
                byte[] bytes = resource.getBytes();
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                resource = null;
            }
        }
        throw new ClassNotFoundException(name);
    }
}
