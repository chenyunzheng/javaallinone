package com.chris.allinone.solution.dynamicload.plugindemo;

import lombok.Builder;
import lombok.Data;

import java.net.URL;

/**
 * @author chrischen
 * @date 2025/2/24
 * @desc TODO描述主要功能
 */
@Data
@Builder
public class PluginEntry {

    private URL jarEntryUrl;
    private String pluginClassName;
    private Class<?> pluginClass;
    private Plugin plugin;
}
