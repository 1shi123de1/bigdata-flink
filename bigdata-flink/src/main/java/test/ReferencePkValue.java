package test;

/**
 * @ClassName ReferencePkValue
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/3/12 9:59
 */
public class ReferencePkValue {
    public static void main(String[] args) {
        ReferencePkValue t = new ReferencePkValue();
        int a = 99;
        String[] s = new String[10];    // 观察默认值
        char [] c = new char[5];        // 观察默认值
        t.test1(a);
        System.out.println(a);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");

        String foo = "blue";
        String bar = foo;
        foo = "green";
        System.out.println(bar + "\t" + foo);  // 一开始"blue"的引用，foo的改变无关

        switch (foo) {
            case "blue":
                System.out.println("blue");
                break;
            case "green":
                System.out.println("green");
                break;
            case "other":
                System.out.println("other");
                break;
            default:
                System.out.println("not the color");
                break;
        }

    }

    public void test1(int a) {
        a = ++a;
        System.out.println(a);
    }
}
