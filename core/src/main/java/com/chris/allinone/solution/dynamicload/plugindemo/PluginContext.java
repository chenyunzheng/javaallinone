package com.chris.allinone.solution.dynamicload.plugindemo;

import com.chris.allinone.solution.dynamicload.plugindemo.listener.PluginListener;
import com.chris.allinone.solution.dynamicload.plugindemo.utils.PluginUtil;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chrischen
 * @date 2025/2/17
 * @desc TODO描述主要功能
 */
public class PluginContext {

    private final String pluginDirectory;
    private final FileFilter fileFilter;

    private final Map<String, PluginEntry> loadedPluginEntries = new ConcurrentHashMap<>();

    public PluginContext(String pluginDirectory) throws Exception {
        this.pluginDirectory = pluginDirectory;
        this.fileFilter = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".jar"));
        initializePlugins();
        initializeMonitor();
    }

    private void initializePlugins() {
        File dir = new File(pluginDirectory);
        File[] jarFileArray = dir.listFiles(fileFilter);
        if (jarFileArray == null) {
            return;
        }
        PluginEntry[] pluginEntries = PluginUtil.getPluginEntries(jarFileArray);
        if (pluginEntries.length == 0) {
            return;
        }
        URL[] jarEntryUrls = Arrays.stream(pluginEntries).map(PluginEntry::getJarEntryUrl).toArray(URL[]::new);
        try (PluginClassLoader pluginClassLoader = new PluginClassLoader("PluginClassLoader", jarEntryUrls)) {
            for (PluginEntry pluginEntry : pluginEntries) {
                Class<?> pluginClass = pluginClassLoader.loadClass(pluginEntry.getPluginClassName());
                pluginEntry.setPluginClass(pluginClass);
                pluginEntry.setPlugin((Plugin)pluginClass.getDeclaredConstructor().newInstance());
                loadedPluginEntries.put(pluginEntry.getPluginClassName(), pluginEntry);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeMonitor() throws Exception {
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(pluginDirectory, fileFilter);
        fileAlterationObserver.addListener(new PluginListener(loadedPluginEntries));
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(2000);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        fileAlterationMonitor.start();
    }

    public String getPluginDirectory() {
        return pluginDirectory;
    }

    public Plugin[] getLoadedPlugins() {
        return loadedPluginEntries.values().stream().map(PluginEntry::getPlugin).toArray(Plugin[]::new);
    }

}
