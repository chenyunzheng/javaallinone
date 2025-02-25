//package com.chris;
//
//import com.chris.allinone.solution.dynamicload.plugindemo.AppContext;
//import com.chris.allinone.solution.dynamicload.plugindemo.Plugin;
//
///**
// * @author chrischen
// * @date 2025/2/24
// * @desc TODO描述主要功能
// */
//public class PgPlugin extends Plugin {
//
//    public PgPlugin() {
//        super("postgresql", "1.0");
//    }
//
//    @Override
//    public void execute(AppContext appContext) {
//        String[] contextData = appContext.getData();
//        for (String data : contextData) {
//            Warpper warpper = new Warpper(data);
//            String threadName = Thread.currentThread().getName();
//            System.out.println(String.format(">>> pgplugin: thread=%s; data=%s", threadName, warpper.toString()));
//        }
//    }
//}
