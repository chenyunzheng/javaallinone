package com.chris.allinone.designpattern.singleton;

/**
 * @author chrischen
 */
public class Singleton {

    private volatile static Singleton INSTANCE;

    /**
     * 私有化构造函数
     */
    private Singleton() {
        System.out.println("Singleton object created");
    }

    public void sayHello() {
        System.out.println("hello");
    }

    /**
     * 定义静态方法返回单例
     * @return
     */
    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    /**
     * DCL: Double-checked locking
     * 解决多线程问题，但需要volatile防止new过程中的指令重排
     * @return
     */
    public static Singleton getInstanceByDCL() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 静态方法 + synchronized
     * synchronized已做过优化
     * @return
     */
    public static synchronized Singleton getInstanceBySynchronized() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    /**
     * inner static class, 也是懒加载
     * Class not loaded : com.chris.allinone.designpattern.singleton.Singleton$InnerSingletonHolder
     */
    public static Singleton getInstanceByInnerStaticClass() {
        return Singleton.InnerSingletonHolder.SINGLETON;
    }

    public static class InnerSingletonHolder {
        public static Singleton SINGLETON = new Singleton();
    }

}
