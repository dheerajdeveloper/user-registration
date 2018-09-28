package com.dheeraj.user.registration.controller;

import java.util.Stack;

/**
 * Created by dheeraj on 13/05/18
 */
public class NIntoM {

    public static void main(String[] args) {
        NIntoM nIntoM = new NIntoM();

        int n = nIntoM.binaryToInt("10000000000");
        int m = nIntoM.binaryToInt("10011");
        System.out.println(nIntoM.IntToBinary(nIntoM.process(n, m, 2, 6)));
    }

    private String IntToBinary(int n) {

        Stack<Integer> stack = new Stack<>();
        while (n > 0) {
            stack.push(n % 2);
            n /= 2;
        }

        String s = "";
        while (!stack.isEmpty()) {
            s += stack.pop().toString();
        }

        return s;


    }

    private int process(int n, int m, int i, int j) {

        if (m > n) {
            return -1;
        }

        if (m == n) {
            return m;
        }

        Stack<Integer> ns = new Stack<>();
        Stack<Integer> ms = new Stack<>();

        while (m > 0) {
            ms.push(m % 2);
            m /= 2;
        }

        int diff = j - i+1;

        while (n > 0 && i > 0) {
            ns.push(n % 2);
            n /= 2;
            i--;
        }

        System.out.println(IntToBinary(n));

        while (n > 0 && diff > 0) {
            n /= 2;
            diff--;
        }

        System.out.println(IntToBinary(n));
        while (!ms.isEmpty()) {
            n <<= 1;
            if (ms.pop() == 1) {
                n |= 1;
            }
        }
        System.out.println(IntToBinary(n));
        while (!ns.isEmpty()) {
            n <<= 1;
            if (ns.pop() == 1) {
                n |= 1;
            }
        }
        System.out.println(IntToBinary(n));

        return n;

    }

    private int binaryToInt(String s) {


        int val = 0;

        int c = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            val += c * (s.charAt(i) - '0');
            c *= 2;
        }

        System.out.println(s + " " + val);
        return val;
    }
}
