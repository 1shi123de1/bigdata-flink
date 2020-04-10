package test;

import java.util.Arrays;

/**
 * @ClassName PackageIssue
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/4/3 9:08
 */
public class PackageIssue {
    public static void main(String[] args) {
        int[] w = {0,3,4,5};
        int[] v = {0,2,3,4};
        int[] M = {0,4,3,2};
        int capacity = 15;
        int[][] result = new int[w.length][capacity+1];

        for (int i = 1; i < w.length; i++) {
            for (int j = 1; j <= capacity; j++) {

                // 01背包
//                if (j < w[i]) {
//                    result[i][j] = result[i-1][j];
//                } else {
//                    result[i][j] = Math.max(result[i-1][j],result[i-1][j-w[i]]+v[i]);
//                }

                // 完全背包
//                for (int k = 0; k * w[i] <= j; k++) {
//                    result[i][j] = Math.max(result[i][j],result[i-1][j-w[i]*k]+v[i]*k);
//                }

                // 多重背包,就比完全背包问题多了一个数量的条件
                for (int k = 0; k * w[i] <= j && k <= M[i]; k++) {
                    result[i][j] = Math.max(result[i][j],result[i-1][j-w[i]*k]+v[i]*k);
                }
            }
        }

        System.out.println(result[w.length-1][capacity]);
        System.out.println(Arrays.deepToString(result));
    }
}
