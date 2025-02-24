package com.chris.allinone.solution.dynamicload.plugindemo;

/**
 * @author chrischen
 * @date 2025/2/17
 * @desc TODO描述主要功能
 */
public abstract class Plugin implements OpenApi {

    private String name;
    private String version;

    protected Plugin(String name, String version) {
        this.name = name;
        this.version = version;
    }
}
