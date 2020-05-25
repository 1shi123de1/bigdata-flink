package test;

import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {

    }

    public static boolean isPrime(int n) {
        int q = (int)Math.sqrt(n);
        for (int i = 2; i <= q; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}