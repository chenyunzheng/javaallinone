package com.chris.allinone.jvm.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author chrischen
 * @date 2025/7/14
 * @desc MyClassLoader
 */
public class MyClassLoader extends URLClassLoader {

    private static final String MY_CLASS_PREFIX = "com.chris.allinone.jvm";

    public MyClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Objects.requireNonNull(name);
        if (name.startsWith(MY_CLASS_PREFIX)) {
            //String classFile = name.replace('.', '/').concat(".class");
            //Arrays.stream(getURLs()).filter(url -> url.)
            return findClass(name);
        } else {
            return super.loadClass(name);
        }
    }
}
