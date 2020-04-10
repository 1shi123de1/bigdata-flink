package test;

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
class MyNode<E> {
    E data;
    MyNode<E> next = null;

    MyNode() {

    }

    MyNode (E data) {
        this.data = data;
    }
}
public class MyLinked<E> {
    MyNode<E> head = null;

    // 初始化,在头结点的前面人为的添加了一个head，数据为?,下一个节点为null
    MyLinked () {
        head = new MyNode();
        head.next = null;
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
}