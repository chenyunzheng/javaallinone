package allinone.javacore;

import jdk.internal.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author chrischen
 */
public class UnsafeFactory {

    private UnsafeFactory() {
        throw new UnsupportedOperationException("cannot be initialized.");
    }

    /**
     * 获取 Unsafe对象
     * @return
     */
    public static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取对象字段的内存偏移量
     * @param unsafe
     * @param clazz
     * @param fieldName
     * @return
     */
    public static long getFieldOffset(Unsafe unsafe, Class<?> clazz, String fieldName) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
}
