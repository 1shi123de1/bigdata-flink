package test;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName MyLinked
 * @Description TODO 自己实现链表 , 需要实现的方法：
 *                         addHead(val) 添加头结点
 *                         add(val) 在末尾添加结点
 *                         add(post,val) 在位置post添加一个节点val
 *                         printAllNode() 打印所有结点
 *                         size()    统计链表的长度
 *                         remove()   删除末尾结点
 *                         remove(post) 删除在第post个位置的结点
 *                         removeHead() 删除头结点
 * @Author ylqdh
 * @Date 2020/4/10 9:26
 */
class MyNode<E>{
    E data;
    MyNode<E> next = null;

    MyNode (E data) {
        this.data = data;
    }

}
public class MyLinked<E>{
    MyNode<E> head = null;

    // 初始化,在头结点的前面人为的添加了一个head，数据为?,下一个节点为null
    MyLinked () {
        head = new MyNode(0);
        head.next = null;
    }

    /**
     *   返回链表的头结点，不是自己虚构的null，head.next才是链表的真正头结点
     * @return
     */
    public MyNode getHead() {
        return this.head.next;
    }

    /**
     * 添加节点,末尾添加
     * @param e 要添加的值
     * @return
     */
    public boolean add (E e) {
        MyNode tmp = head;
        MyNode<E> cur = new MyNode(e);
        while (tmp.next != null) {
            tmp = tmp.next;
        }

        tmp.next = cur;

        return true;
    }

    /**
     *  添加节点,添加在头结点
     * @param e  要添加的值
     * @return
     */
    public boolean addHead (E e) {
        MyNode<E> cur = new MyNode(e);

        cur.next = head.next;  // 注意这一行和下一行不能调换
        head.next = cur;
        return true;
    }

    /**
     *  添加结点,在中间某个位置添加
     * @param post  添加的位置，从1开始计数
     * @param e     添加的值
     * @return
     */
    public boolean add (int post, E e) {
        // 位置不能小于0
        if (post < 0 | post > size()) {
            return false;
        }
        MyNode<E> cur = new MyNode(e);
        MyNode<E> tmp = head;
        int i = 0;
        while (i < post-1) {
            tmp = tmp.next;
            i++;
        }

        cur.next = tmp.next;
        tmp.next = cur;

        return true;
    }

    // 打印所有结点的值
    public void printAllNode () {
        MyNode<E> tmp = head;
        while (tmp.next != null) {
            System.out.print(tmp.next.data+" --> ");  // 注意这里是打印tmp.next的值
            tmp = tmp.next;
        }
        System.out.println(" null");
    }

    // 统计结点的个数
    public int size() {
        int sum = 0;
        MyNode<E> tmp = head;
        while (tmp.next != null) {
            sum++;
            tmp = tmp.next;
        }
        return sum;
    }

    // 删除,删除头结点
    public boolean removeHead() {
        head.next = head.next.next;
        return true;
    }

    /**
     *   删除,删除指定位置的结点
     * @param post 要删除的位置
     * @return
     */
    public boolean remove(int post) {
        if (post < 0 || post > size()) {
            System.out.println("删除的位置小于0或者大于链表的长度,删除操作不执行");
            return false;
        }
        int i = 0;
        MyNode<E> tmp = head;
        while (i < post-1) {
            tmp = tmp.next;
            i++;
        }
        tmp.next = tmp.next.next;
        return true;
    }

    /**
     *  删除，不加参数，就是删除末尾的结点
     * @return
     */
    public boolean remove() {
        if (head.next == null) {
            return false;
        }
        int i = 0;
        MyNode<E> tmp = head;
        while (i < size()-1) {
            tmp = tmp.next;
            i++;
        }
        tmp.next = null;
        return true;
    }

    /**
     *   反转链表
     */
    public void reverseLinked() {
        MyNode preNode = null;
        MyNode curNode = head.next;
        MyNode nextNode = head.next.next;

        while (nextNode != null) {
            curNode.next = preNode;
            preNode = curNode;
            curNode = nextNode;
            nextNode = nextNode.next;
        }

        // 最后要把链接上
        curNode.next = preNode;

        // head
        head.next = curNode;
    }

    /**
     *   检查链表是否有环
     *      快指针和慢指针，快指针每次走两步，慢指针每次走一步，
     *      有环的话，快指针会追上慢指针
     * @return
     */
    public boolean isCircle() {
        MyNode fast = head;
        MyNode slow = head;

        while (fast != null && slow != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     *  把每次结点存进set集合中，如果有环的话，set存不进
     * @return
     */
    public boolean isCircle2() {
        Set<MyNode> set = new HashSet<>();

        MyNode tmp = head;

        while (tmp.next != null) {
            if (set.contains(tmp)) {
                return true;
            } else {
                set.add(tmp);
                tmp = tmp.next;
            }
        }

        return false;
    }

    /**
     *   删除链表的倒数第n个结点,两次遍历法，先遍历一次得到链表的长度，再根据长度计算删除倒数第n个结点
     * @param n  正整数n
     * @return
     */
    public boolean delReverseNum(int n) {
        int sum = this.size();

        if (sum < n) {
            System.out.println("输入的n不能大于链表长度...");
            return false;
        }
        int i = 0;
        MyNode<E> tmp = head.next;

        while (i < sum - n - 1) {
            tmp = tmp.next;
            i++;
        }

        tmp.next = tmp.next.next;

        return true;
    }

    /**
     *   删除倒数第n个结点
     *   先让fast指针先走n+1步；
     *   然后两个指针一起走，当fast指针是null时，slow指针就是倒数第n个结点的前一个结点
     *   此时，把slow的next指向改掉
     * @param n
     * @return
     */
    public boolean delReverNode(int n) {
        MyNode<E> fast = head.next;
        MyNode<E> slow = head.next;

        for (int i = 0; i <= n ; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return true;
    }

    /**
     *  找链表的中间结点
     * @return
     */
    public MyNode midNode () {
        if (this.head.next == null) {
            return null;
        }
        MyNode<E> slow = head.next;
        MyNode<E> fast = head.next;

        // 奇数时
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // 偶数时
            if (fast == null) {
                break;
            }
        }

        return slow;
    }
}