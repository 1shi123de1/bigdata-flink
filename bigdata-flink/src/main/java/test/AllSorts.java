package test;

import java.util.Arrays;

/**
 * @ClassName AllSorts
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/4/20 9:58
 */
public class AllSorts {
    public static void main(String[] args) {
        int[] a = {3,5,6,8,9,10};
//        SelectSort(a);

//        QuickSort(a,0,a.length-1);
//        System.out.println(Arrays.toString(a));

        int result = BinarySearch(a,7);
        System.out.println("result: "+result);
    }

    /**
     *   冒泡排序 , 升序排序
     *             要降序排序的话，把if条件的 > 改为 < 即可
     * @param ints
     */
    public static void BubbleSort (int[] ints) {
        if (ints.length <= 1) {
            System.out.println("不需要排序"+Arrays.toString(ints));
            return;
        }
        int length = ints.length;
        int tmp = 0;
        for (int i = 0; i < length; i++) {
            boolean flag = true;    // 标志位
            for (int j = 0 ; j < length-i-1; j++) {
                if (ints[j] > ints[j+1]) {
                    tmp = ints[j];
                    ints[j] = ints[j+1];
                    ints[j+1] = tmp;

                    flag = false;
                }
            }
            System.out.println("第 "+i+" 个数字.此时的数组是："+ Arrays.toString(ints));
            if (flag) {
                break;
            }
        }
        System.out.println("数组最终排序结果是：" + Arrays.toString(ints));
    }

    /**
     *  插入排序，升序排序
     *           要降序排序的话，把if条件的>改为<
     * @param ints
     */
    public static void InsertSort (int[] ints) {
        if (ints.length <= 1) {
            System.out.println(Arrays.toString(ints));
            return;
        }

        for (int i = 1; i < ints.length; i++) {

            int tmp = ints[i];  // 存为临时变量
            int j = i - 1;      // j 保存下来，如果在下面的for循环在定义，则下面的for循环完之后用不了
            for (; j >= 0; j--) {
                if (ints[j] > tmp) {     // 查找并移动数组
                    ints[j+1] = ints[j];
                } else {
                    break;
                }
            }

            // 原值赋给空出来的位置
            ints[j+1] = tmp;
        }
        System.out.println(Arrays.toString(ints));
    }

    /**
     *   选择排序，升序排序
     *            降序排序,把if条件的 < 改为 > 即可
     * @param ints
     */
    public static void SelectSort (int[] ints) {
        for (int i = 0 ; i < ints.length ; i++) {
            int tmp = ints[i];
            int x = 0;
            boolean flag = true;
            for (int j = i+1 ; j < ints.length ; j++) {
                if (ints[j] < tmp) {
                    tmp = ints[j];
                    x = j;
                    flag = false;
                }
            }
            if (flag) {
                continue;
            }
            ints[x] = ints[i];
            ints[i] = tmp;
        }
        System.out.println(Arrays.toString(ints));
    }

    public static void QuickSort (int[] ints,int left,int right) {
        if (ints == null || ints.length == 1 || left >= right) {
            return;
        }

        int pivot = ints[left];   // 数组最左边的数作为基准值
        int i = left , j = right;

        while (i != j) {
            // j从数组右边往左，找到一个小于pivot的数
            while (ints[j] >= pivot && i < j) {
                j--;
            }

            // 接着i从数组左边往右，找到一个大于pivot的数
            while (ints[i] <= pivot && i < j) {
                i++;
            }

            // 找到i和j的位置后，交换所对应的值
            if (i < j) {
                int tmp = ints[i];
                ints[i] = ints[j];
                ints[j] = tmp;
            }
        }

        ints[left] = ints[i];
        ints[i] = pivot;

        QuickSort(ints,left,i-1);
        QuickSort(ints,i+1,right);
    }

    /**
     * 二分查找以及几种变体
     * @param ints 待查找的数组
     * @param a   要查找的元素
     * 返回元素在数组中的位置,不存在则返回-1
     */
    public static int BinarySearch (int[] ints,int a) {
        int low = 0,high = ints.length - 1;  // low和high还有后面的mid都是指数组的下标

//        while (low <= high) {
//            int mid = (low + high)/2;
//            if (ints[mid] > a) {
//                high = mid - 1;
//            } else if (ints[mid] < a)  {
//                low = mid + 1;
//            }else {
//                // 普通二分查找，数组中没有重复的值,找到就直接返回
////                return mid;
//
//                // 二分查找变体1：数组有重复的值，要查找第一个等于值的元素
////                if (mid == 0 || ints[mid-1] != a) {    // 当mid是数组第一个元素，或者位置mid的前一个值不等于a，则mid就是第一个要查找的元素
////                    return mid;
////                } else {
////                    high = mid - 1;
////                }
//
//                // 二分查找变体2：数组有重复的值，要查找最后个等于值的元素
//                if (mid == ints.length-1 || ints[mid+1] != a) {   // 当mid是数组最后一个元素，或者位置mid的后一个值不等于a，则mid就是最后一个要查找的元素
//                    return mid;
//                } else {
//                    low = mid + 1;
//                }
//            }
//        }

        // 二分查找变体3：数组有重复的值，要查找第一个大于等于给定值a的元素
//        while (low < high) {
//            int mid = (low+high)/2;
//
//            if (ints[mid] >= a) {
//                if (mid == 0 || ints[mid-1]<a) {   // 如果mid是数组第一个元素，或者mid位置的前一个元素是小于给定值a，则mid就是要找的元素
//                    return mid;
//                } else {
//                    high = mid - 1;
//                }
//            } else {
//                low = mid + 1;
//            }
//        }

        // 二分查找变体4：数组有重复的值，要查找最后一个小于等于给定值a的元素
        while (low < high) {
            int mid = (low + high)/2;
            if (ints[mid] <= a) {
                if (mid == ints.length-1 || ints[mid+1] > a) { // 如果mid位于数组最后一个元素，或者mid位置的下一个元素已经大于给定值a了，则mid就是要查找的元素
                    return mid;
                } else {
                    low = mid + 1;
                }
            } else {
                high = mid - 1;
            }
        }

        return -1;

    }


}