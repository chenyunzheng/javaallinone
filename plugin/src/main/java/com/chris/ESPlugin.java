//package com.chris;
//
//import com.chris.allinone.solution.dynamicload.plugindemo.AppContext;
//import com.chris.allinone.solution.dynamicload.plugindemo.Plugin;
//
///**
// * Hello world!
// *
// */
//public class ESPlugin extends Plugin {
//
//    public ESPlugin() {
//        super("es", "1.0");
//    }
//
//    @Override
//    public void execute(AppContext appContext) {
//        String[] contextData = appContext.getData();
//        for (String data : contextData) {
//            Warpper warpper = new Warpper(data);
//            String threadName = Thread.currentThread().getName();
//            System.out.println(String.format(">>> esplugin: thread=%s; data=%s", threadName, warpper.toString()));
//        }
//    }
//}
