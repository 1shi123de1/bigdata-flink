package test;

import java.util.Arrays;

/**
 * @ClassName QuickSort
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/3/13 10:08
 */
public class QuickSort {
    public static void main(String[] args) {

        int[] arr = {4,6,1,3,9,8,2,10};

        quickSort(arr,0,arr.length-1);

        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] ints,int left,int right) {

        if (ints == null || ints.length == 0 || ints.length ==1) {
            return;
        }

        int i = left,j = right;
        int base = ints[left];

        if (left > right) {
            return;
        }

        while (i != j) {
            while (ints[j]>=base && i<j) {
                j--;
            }
            while (ints[i]<=base && i<j) {
                i++;
            }
            // exchange ints[left] and ints[right]
            if (i < j) {
                int temp = ints[i];
                ints[i] = ints[j];
                ints[j] = temp;
            }

        }
        ints[left] = ints[i];
        ints[i] = base;

        quickSort(ints,left,i-1);
        quickSort(ints,i+1,right);

    }
}