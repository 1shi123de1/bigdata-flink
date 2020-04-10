package test;

import org.apache.flink.table.planner.expressions.E;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName JavaTest
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/3/18 10:16
 */
public class JavaTest {
    public static void main(String[] args) {
//        BaseConvert();
//        compuPoint();
//        IPAddr();
//     System.out.println(isPrime(13*13));

//        primeSum();
//        baoWenConver();
//        DeliverGroupMessage();
//        lis();
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("A");
        list.add("C");

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                char c1 = o1.toLowerCase().toCharArray()[0];
                char c2 = o2.toLowerCase().toCharArray()[0];
                return c1 - c2;
            }
        });
        System.out.println(list);
    }

    // 进制转换
    public static void BaseConvert() {
        int n = 123;

        System.out.println(n+"的二进制是："+Integer.toBinaryString(n));
        System.out.println(n+"的八进制是："+Integer.toOctalString(n));
        System.out.println(n+"的十六进制是："+Integer.toHexString(n));

        System.out.println(n+"的二进制中1的个数是："+Integer.bitCount(n));

        StringBuilder sb = new StringBuilder();

        while (n>0) {
            sb.insert(0,n%8);
            n /= 8;
        }

        System.out.println("sb: "+sb);
    }

    // 坐标移动
    public static void compuPoint() {
        String s = "W49;W92;A84;W33;A32;A52;D97;S14;S33;D79;A14;W25;D97;D21;D31;A66;S67;S4;A59;S62;W40;S26;S65;A58;S17;A67;D48;W23;D68;S99;S97;W63;W27;D12;D83;W8;S3;W6;A88;D23;W30;S91;D58;W74;D45;W3;D19;S72;D8;S79;S76;S49;W16;A29;W93;W99;W92;D82;A10;A4;D25;A90;D83;W45;W20;S68;D59;S48;A18;S0;W24;S48;W75;A39;W29;S28;W76;W78;D94;A57;A5;D51;S61;A39;W77;S70;A2;D8;S58;D51;S86;W30;A27;S62;D56;A51;D0;S58;W34;S39;A64;A68;A96;D37;S91;S16;A17;D35;A85;W88;S57;S61;A28;D12;A87;S39;A85;W22;D65;D72;A5;A78;A59;D75;D57;S66;A55;D84;D72;W87;S46;W64;D49;S46;W34;D60;S39;A30;W86;D20;W93;D25;W44;W86;A16;D4;A86;S86;S27;W7;W89;W93;S17;S39;W66;W72;D81;W93;A88;D46;S57;W45;S84;S57;D27;A11;D54;S8;W15;A50;A69;A4;D19;D69;A3;A28;D47;W18;A39;D47;W14;D77;W59;S84;A32;D56;S16;D99;A33;A51;A24;D65;W37;D98;A13;W6;D94;D28;A12;S18;W40;S23;W76;D6;S40;D26;W97;W7;W90;W75;S12;A89;S46;S36;D96;A49;A73;S53;D84;A87;D75;D48;W84;S14;W65;W79;W51;S9;S77;D51;S76;W16;W77;A90;S96;D78;S7;W71;D17;W10;W4;D96;S58;A88;S89;D41;W47;W13;S75;S85;W63;W87;S19;S80;W92;W95;W73;D43;D35;W60;S31;D72;A73;W90;S51;A55;S82;W81;S56;W83;W41;A77;S37;D63;A72;D44;W81;S26;S53;W32;A24;S90;S83;W34;D10;S53;D69;A87;W84;S32;D72;S63;D97;W27;D23;D25;D18;W86;D74;D42;A31;S1;D98;D76;S46;D67;W94;S6;S97;S40;W71;W12;D62;W57;A92;W59;W59;D15;A16;D21;S33;S45;S83;D89;W77;A93;S60;S6;W24;S93;D69;D79;S13;S8;W63;D99;S36;D35;W53;S5;S44;S19;D76;S14;A86;A86;W43;W65;D20;S19;W66;A54;A98;A17;D2;W98;W2;D77;A90;S8;S55;S67;W88;W19;S59;D9;S75;S56;W75;W54;A61;W47;S19;D67;S39;D55;S6;A89;A5;W58;W7;W88;W92;D85;S32;S32;A27;S42;D52;A55;S26;D27;W40;D76;A55;D38;W13;A71;D79;W59;A76;A33;A12;D1;D63;W63;W62;D7;W48;A84;D27;A80;D42;D27;D49;D4;D71;W90;W39;D24;W71;D16;S1;W88;W88;D25;D66;S39;S43;A99;A92;W19;W20;A90;A31;A6;A79;D4;D80;A77;D71;D36;S64;";

        String[] splits = s.split(";");

        int x = 0,y = 0,point = 0;

        for (int i = 0; i<splits.length; i++) {
            // (长度是2，第二位是数字) && (长度是3,后面两位是数字)
            if ( (splits[i].length() == 2 && Character.isDigit(splits[i].charAt(1)))
                    || (splits[i].length() == 3 && Character.isDigit(splits[i].charAt(1)) && Character.isDigit(splits[i].charAt(2))) ) {
                switch (splits[i].charAt(0)) {
                    case 'W':
                        point = Integer.parseInt(splits[i].substring(1));
                        y += point;
                        break;
                    case 'S':
                        point = Integer.parseInt(splits[i].substring(1));
                        y -= point;
                        break;
                    case 'A':
                        point = Integer.parseInt(splits[i].substring(1));
                        x -= point;
                        break;
                    case 'D':
                        point = Integer.parseInt(splits[i].substring(1));
                        x += point;
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println(x+","+y);

    }

    // IP地址判断ABCDE类地址...未完成
    public static void IPAddr() {
        int a = 0, b =0, c =0, d = 0, e = 0, f = 0;
        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {
            String s = scan.next();
            String[] ipAndMask = s.split("~");

            String ip = ipAndMask[0];
            String netMask = ipAndMask[1];



        }
    }

    // 计算最短步数,未完成
    public static void minStep() {
        int[] ints = {7,5,9,4,2,6,8,3,5,4,3,9};
        int min = 0;    //
        int step = 0;  // 步长

        for (step = 1;step<ints.length/2;step++) {

        }
    }

    // 找范围内的质数，求所有质数的十位数和个位数的和，取和的较小值
    public static void primeSum() {

        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        int x = Integer.parseInt(line.split(" ")[0]);
        int y = Integer.parseInt(line.split(" ")[1]);

        int sumS = 0;
        int sumG = 0;

        for (int i = x; i < y; i++) {
            if (isPrime(i)) {
                sumS +=  i % 100 / 10;
                sumG += i % 10;
            }
        }

        if (sumG < sumS) {
            System.out.println(sumG);
        } else {
            System.out.println(sumS);
        }

    }

    // 判断一个正整数是否是质数
    public static boolean isPrime(int n) {
        boolean flag = true;

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n%i == 0) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    // 报文转换，当遇到A 0x0A是转换为12 34，当遇到0x0B B是转换为AB CD
    public static void baoWenConver() {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();

        String[] lines = line.split(" ");

        int sum = Integer.parseInt(lines[0]);

        List<String> list = new ArrayList<String>();

        for (int i = 1; i < lines.length; i++) {
            if (lines[i].equals("0x0A") || lines[i].equals("A")) {
                list.add("12");
                list.add("34");
                sum++;
            } else if (lines[i].equals("0x0B") || lines[i].equals("B")) {
                list.add("AB");
                list.add("CD");
                sum++;
            } else {
                list.add(lines[i]);
            }
        }

        System.out.print(sum);
        for (String s : list) {
            System.out.print(" "+s);
        }

    }

    // 群发消息
    public static void DeliverGroupMessage() {
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        int num = scan.nextInt();

        Set<String> set = new HashSet<>();

        for (int i = 0; i < num; i++) {
            String s = scan.next();
            String[] names = s.split(",");

            for (String ns : names) {
                set.add(ns);
            }
        }
        System.out.println(set.size());
    }

    // 数组拼接，按顺序，把数组的前n位元素拼接起来
    public static void conCatArray() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        /*
            随着输入增加，要拼接的数组会增多，那么拼接的结果就会变化
            所以我定义了两个list，把所有的输入，存进一个list中; 然后另一个list每次只复制allList的值
            如果只定义一个的话，会把前面的值清空
         */
        List<String> allLine = new ArrayList<>();  // 随着输入增加，把所有的组以String存进list中
        List<String> line = new ArrayList<>();     // 把目前的输入情况放到这个list中

        scan.nextLine();  // 不知道为什么，如果不加这一行，后面的scan.nextLine()就读不到数据，有知道的可以告诉我一下

        while (scan.hasNext()) {
            // 把一行输入存储进All List中
            allLine.add(scan.nextLine());

            // 把当前的所有输入转到line中，随着后面的拼接，line里的内容会清空
            for (int i = 0; i < allLine.size(); i++) {
                line.add(allLine.get(i));
            }

            // 接下来就line里的所有数组进行拼接了；而所有结果都在all list中，不会造成影响

            StringBuilder result = new StringBuilder();
            String tmp;
            int flag = 0;
            int tmpSize = line.size();

            while (flag < tmpSize) {
                for (int j = 0; j < line.size(); j++) {
                    tmp = line.get(j);

                    // list的这个位置已经清空，不需要再进行拼接
                    if (tmp.length() == 0) {
                        flag++;
                        line.remove(j);
                        continue;
                    }

                    // 一行输入，需要以逗号分隔
                    String[] nums = tmp.split(",");

                    // 长度不足n位，则剩下的全部加到结果数组中
                    if (nums.length <= n) {
                        result.append(tmp).append(",");
                        line.set(j,"");
                        continue;
                    }

                    String sc = nums[0]+","+nums[1]+","+nums[2]+",";
                    result.append(sc);  // 有3位，直接把这3位数字拼接到数组末尾
                    line.set(j,tmp.substring(sc.length()));  // 原字符 则 减掉这3位
                }
            }

            // 输出结果，注意要把末尾的逗号去掉
            System.out.println(result.substring(0,result.length()-1));
        }
    }

    // 某个数n，找到两个质数，使得两个质数和为n
    public static void findTwoPrime () {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        int result = 0;
        for (int j = 1; j < n/2; j++) {
            if (isPrime(j) && isPrime(n-j)) {
                result++;
//                System.out.println(n+" = "+j+" + "+(n-j));
            }
        }
        System.out.println(result);
    }

    // 判断两个数是否互质
    public static boolean isDoublePrime(int x, int y) {
        boolean flag = false;

        int tmp = 1;
        // 辗转相除法
        while (x % y != 0) {
            tmp = x % y;
            x = y;
            y = tmp;
        }

        if (y == 1) {
            flag = true;
        }
        return flag;
    }

    // 求小于等于n的满足如下条件的三个数：a.三个数互为勾股数；b.三个数互质
    public static void primeGouGuNum() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        int result = 0;
        for (int a = 1; a <= n; a++) {
            for (int b = a+1; b <= n; b++) {
                double c = Math.sqrt(a*a+b*b);
                int zc = (int)c;
                if (a == b || zc - c != 0 || c > n) {
                    continue;
                }

                if (isDoublePrime(a,b) && isDoublePrime(a,zc) && isDoublePrime(b,zc)) {
                    result++;
                    System.out.println("a : "+a+" , b : "+b+" , c : "+zc);
                }
            }
        }

        System.out.println(result);
    }

    // 对一个正整数因式分解
    public static void yinShiFenJie() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        List<Integer> list = new ArrayList<>();

        int j = 2;
        while (j <= Math.sqrt(n)) {
            // n 除以 j 能除尽，且j是质数
            if (n % j == 0 && isPrime(j)) {
                list.add(j);
                if (isPrime(n / j)) {
                    list.add(n/j);
                    break;
                }
                n = n / j;
                j = 2;
                continue;
            }
            j++;
        }

        System.out.print(n+"的因式分解后的因子有：");
        for (int x : list) {
            System.out.print(x+"\t");
        }
    }

    // 未完成
    public static void JumpNum() {
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();    // 输入的数组长度
        int[] intss = new int[num]; // 要跳跃的数组
        for (int i = 0; i < num; i++) {
            intss[i] = scan.nextInt();
        }

    }

    // 未完成
    public static void lis() {
        int[] f = {0,1,1,1,1,1,1,1,1,0,0,0};
        int[] a = {0,1,5,3,4,6,9,7,8,0,0,0};
        int i,x,p,n = 8,ans = 0;

        for (x = 1; x <= n; x++) {
            for (p = 1; p < x; p++) {
                if (a[p] < a[x])
                    f[x] = Math.max(f[x],f[p]+1);
                System.out.println("f["+x+"] = "+f[p]);
            }
        }
        for (x = 1; x <= n; x++) {
            ans = Math.max(ans,f[x]);
        }
        System.out.println("ans : "+ans);
    }

    // 磁盘容量大小排序，M、G、T
    public static void sortPan() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        List<String> pan = new ArrayList<>();

        scan.nextLine();

        for (int i = 0; i < n; i++) {
            pan.add(scan.nextLine());
        }

        // 统一换成M的形式，方便比较
        Map<Integer,String> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String value = pan.get(i);
            if (value.endsWith("M")) {
                map.put(Integer.parseInt(value.substring(0,value.length()-1)),value);
            } else if (value.endsWith("G")) {
                int tmp = Integer.parseInt(value.substring(0,value.length()-1));
                map.put(tmp*1000,value);
            } else if (value.endsWith("T")) {
                int tmp = Integer.parseInt(value.substring(0,value.length()-1));
                map.put(tmp*1000*1000,value);
            }
        }

        List<Integer> key = new ArrayList<Integer>(map.keySet());

        Collections.sort(key);

        for (int i : key) {
            System.out.println(map.get(i));
        }
    }

    // 一个字符串，里面的其他字符位置不变，字母字符排序；
    public static void sortString() {
        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {
            String src = scan.nextLine();

            List<String> list = new ArrayList<>(src.length());
            char[] result = src.toCharArray();

            Pattern p = Pattern.compile("[a-zA-Z]");

            for (int i = 0 ; i < src.length() ; i++) {
                char tmp = src.charAt(i);
                Matcher m = p.matcher(tmp+"");

                if (m.find()) {
                    list.add(tmp+"");
                }
            }

            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    char c1 = o1.toLowerCase().toCharArray()[0];
                    char c2 = o2.toLowerCase().toCharArray()[0];
                    return c1 - c2;
                }
            });

            int ll = 0;
            for (int j = 0; j < src.length(); j++) {
                Matcher m1 = p.matcher(result[j]+"");

                if (!m1.find()) {
                    System.out.print(result[j]);
                } else {
                    System.out.print(list.get(ll));
                    ll++;
                }
            }
            System.out.println();
        }
    }
}
