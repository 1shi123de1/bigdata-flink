package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName MyStack
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/3/31 16:58
 */
public class MyStack<E> {
    List<E> list;

    public MyStack(){
        list = new ArrayList<E>();
    }

    /*
    isEmpty()
    size()
    pop(E)
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
        if (list.size() == 0) {
            return null;
        } else {
            return list.remove(list.size()-1);
        }
    }

    public void push(E e) {
        list.add(e);
    }

    public String toString() {
        return list.toString();
    }
}
