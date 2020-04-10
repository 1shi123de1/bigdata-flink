package test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName DirectMemoryOOM
 * @Description TODO 直接内存 oom
 *              VM args: -Xmx20M -XX:MaxDirectMemorySize=10M
 *                     -Xmx20M 指定堆的最大内存为20M
 *                     -XX:MaxDirectMemorySize=10M 指定直接内存为10M
 * @Author ylqdh
 * @Date 2020/2/28 9:49
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024*1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsageField = Unsafe.class.getDeclaredFields()[0];
        unsageField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsageField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }

    }
}
