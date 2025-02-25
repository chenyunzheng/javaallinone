package com.chris.allinone.solution.dynamicload.plugindemo.utils;

import com.chris.allinone.solution.dynamicload.plugindemo.Constant;
import com.chris.allinone.solution.dynamicload.plugindemo.Plugin;
import com.chris.allinone.solution.dynamicload.plugindemo.PluginEntry;
import org.springframework.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author chrischen
 * @date 2025/2/24
 * @desc TODO描述主要功能
 */
public class PluginUtil {

    private static final String PLUGIN_CLASS_NAME = Plugin.class.getName().replace('.', '/');

    private PluginUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void clearTempDir() {
        File tempDir = new File(Constant.TEMP_DIR);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        File[] listFiles = tempDir.listFiles();
        if (listFiles != null) {
            Arrays.stream(listFiles).forEach(File::delete);
        }
    }

    public static File[] copyToTempDir(File[] jarFileArray) throws IOException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        for (int i = 0; i < jarFileArray.length; i++) {
            File file = jarFileArray[i];
            String fileName = file.getName().replace(".jar", Constant.TEMP_FLAG + timestamp + ".jar");
            file = Files.copy(file.toPath(), Path.of(Constant.TEMP_DIR, fileName)).toFile();
            jarFileArray[i] = file;
        }
        return jarFileArray;
    }

    public static PluginEntry[] getPluginEntries(File[] jarFileArray) {
        List<PluginEntry> pluginEntryList = new ArrayList<>();
        for (File file : jarFileArray) {
            try (JarFile jarFile = new JarFile(file)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (!entry.getName().endsWith(".class")) {
                        continue;
                    }
                    InputStream entryIs = jarFile.getInputStream(entry);
                    ClassReader classReader = new ClassReader(entryIs);
                    if (PLUGIN_CLASS_NAME.equals(classReader.getSuperName())) {
                        PluginEntry pluginEntry = PluginEntry.builder()
                                .pluginClassName(classReader.getClassName().replace('/', '.'))
                                .jarEntryUrl(file.toURI().toURL()).build();
                        pluginEntryList.add(pluginEntry);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pluginEntryList.toArray(new PluginEntry[]{});
    }
}
