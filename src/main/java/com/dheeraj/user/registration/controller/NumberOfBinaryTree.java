package com.dheeraj.user.registration.controller;

/**
 * Created by dheeraj on 06/05/18
 */
public class NumberOfBinaryTree {

    public static void main(String[] args) {
        NumberOfBinaryTree tree = new NumberOfBinaryTree();

//        int a[] = {10, 11};//, 12, 13, 14};
//        int a[] = {10, 11, 12};//, 13, 14};
        int a[] = {10, 11, 12, 13, 14};

        System.out.println(tree.process(a, 0, a.length - 1));
    }

    private int process(int[] a, int l, int h) {


        if (h < l) {
            return 1;
        }
        if (l == h) {
            return 1;
        }
        if (h == l + 1) {
            return 2;

        }

        int total = 0;

        for (int i = l; i <= h; i++) {

            int left = process(a, l, i - 1);
            int right = process(a, i + 1, h);


            total += left * right;

        }

        return total;
    }
}
