package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyQueue
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/3/31 17:12
 */
public class MyQueue<E> {
    List<E> list;

    public MyQueue() {
        list = new ArrayList<E>();
    }

    /*
     isEmpty()
    size()
    pop()
    push(E)
    toString
     */

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public E pop() {
        if (list.isEmpty()) {
            return null;
        }
        return list.remove(0);
    }

    public void push(E e) {
        list.add(e);
    }

    public String toString() {
        return list.toString();
    }
}
