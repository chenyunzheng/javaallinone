package com.chris.allinone.solution.dynamicload.plugindemo;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * @date 2025/2/17
 * @desc TODO描述主要功能
 */
@Data
public class AppContext {

    private String[] data;

    public static void main(String[] args) throws Exception {
        //basic environment
        AppContext appContext = new AppContext();
        appContext.setData(new String[]{"a", "b", "c"});
        String pluginDir = "D:\\cyzhope\\code\\non-project\\allinone\\core\\src\\main\\java\\com\\chris\\allinone\\solution\\dynamicload\\plugindemo\\plugins";
        PluginContext pluginContext = new PluginContext(pluginDir);

        while (true) {
            for (Plugin loadedPlugin : pluginContext.getLoadedPlugins()) {
                loadedPlugin.execute(appContext);
            }
            System.out.println("---------------------------------------");
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
