package test;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {

        MyLinked<Integer> myLinked = new MyLinked();

        myLinked.add(1);

        myLinked.printAllNode();

        myLinked.add(2);
        myLinked.add(3);
        myLinked.add(2,4);
        myLinked.addHead(5);

        myLinked.printAllNode();
        System.out.println("此时的长度： "+myLinked.size()+"\t");

        myLinked.removeHead();
        myLinked.remove(2);
        myLinked.printAllNode();

    }
}