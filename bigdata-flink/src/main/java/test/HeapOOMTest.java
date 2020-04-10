package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HeapOOMTest
 * @Description TODO Java heap(堆) OOM(内存溢出) 测试
 *              VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *                      -Xms20m 设置最大堆内存为20M
 *                      -Xmx20m 设置最小堆内存为20M ，两者设置为一样可避免自动扩展内存
 *                      +HeapDumpOnOutOfMemoryError 参数让虚拟机在出现内存溢出异常时Dump出当前的内存堆转储快照以便进行事后分析
 * @Author ylqdh
 * @Date 2020/2/27 11:02
 */
public class HeapOOMTest {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            System.out.println("add object ing...");
            list.add(new OOMObject());
        }
    }
}
