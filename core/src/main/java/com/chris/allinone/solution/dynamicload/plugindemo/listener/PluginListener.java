package com.chris.allinone.solution.dynamicload.plugindemo.listener;

import com.chris.allinone.solution.dynamicload.plugindemo.Plugin;
import com.chris.allinone.solution.dynamicload.plugindemo.PluginClassLoader;
import com.chris.allinone.solution.dynamicload.plugindemo.PluginEntry;
import com.chris.allinone.solution.dynamicload.plugindemo.utils.PluginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chrischen
 * @date 2025/2/24
 * @desc TODO描述主要功能
 */
@Slf4j
public class PluginListener extends FileAlterationListenerAdaptor {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private final Map<String, PluginEntry> loadedPluginEntries;

    public PluginListener(Map<String, PluginEntry> loadedPluginEntries) {
        this.loadedPluginEntries = loadedPluginEntries;
    }

    @Override
    public void onFileDelete(File file) {
        PluginEntry[] pluginEntries = PluginUtil.getPluginEntries(new File[]{file});
        for (PluginEntry pluginEntry : pluginEntries) {
            String pluginClassName = pluginEntry.getPluginClassName();
            PluginEntry removedPluginEntry = loadedPluginEntries.remove(pluginClassName);
            //help gc to unload class
            removedPluginEntry.setPlugin(null);
            removedPluginEntry.setPluginClass(null);
            log.info("Plugin: {} removed.", pluginClassName);
        }
    }

    @Override
    public void onFileCreate(File file) {
        PluginEntry[] pluginEntries = PluginUtil.getPluginEntries(new File[]{file});
        if (pluginEntries.length == 0) {
            return;
        }
        URL[] jarEntryUrls = Arrays.stream(pluginEntries).map(PluginEntry::getJarEntryUrl).toArray(URL[]::new);
        try (PluginClassLoader pluginClassLoader = new PluginClassLoader("PluginClassLoader-" + COUNTER.incrementAndGet(), jarEntryUrls)) {
            for (PluginEntry pluginEntry : pluginEntries) {
                String pluginClassName = pluginEntry.getPluginClassName();
                if (loadedPluginEntries.containsKey(pluginClassName)) {
                    PluginEntry removedPluginEntry = loadedPluginEntries.remove(pluginClassName);
                    //help gc to unload class
                    removedPluginEntry.setPlugin(null);
                    removedPluginEntry.setPluginClass(null);
                    log.info("Plugin: {} removed.", pluginClassName);
                }
                Class<?> pluginClass = pluginClassLoader.loadClass(pluginClassName);
                Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                pluginEntry.setPluginClass(pluginClass);
                pluginEntry.setPlugin(plugin);
                loadedPluginEntries.put(pluginClassName, pluginEntry);
                log.info("Plugin: {} loaded.", pluginClassName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFileChange(File file) {
        //替换
        onFileCreate(file);
    }
}