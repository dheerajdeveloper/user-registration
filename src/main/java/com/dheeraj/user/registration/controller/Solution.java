package com.dheeraj.user.registration.controller;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    int max = 0;
    List<String> res = new ArrayList<>();

    public static void main(String[] args) {
        Solution s = new Solution();

        System.out.println(s.isMatch("aa", "aa**"));
//        System.out.println(s.isMatch("aa", "*"));
//        System.out.println(s.isMatch("cb", "?a"));
//        System.out.println(s.isMatch("acdcb", "a*c?b"));
//        System.out.println(s.isMatch("abcdefgh", "a*f*h"));
//        System.out.println(s.isMatch("abcdfeg", "*d*"));
//        System.out.println(s.isMatch("abbbbbcc", "*b*c"));
//        System.out.println(s.isMatch("abbbbbbbc", "*b*"));
//        System.out.println(s.isMatch("abc", "*"));
//        System.out.println(s.isMatch("abc", "abc"));
    }


    public boolean isMatch(String s, String p) {

        if (p == null) {
            if (s == null) {
                return true;
            } else {
                return false;
            }
        } else if (s == null) {
            return false;
        }

        int n = s.length();
        int m = p.length();

        if (m == 0) {
            if (n == 0) {
                return true;
            } else {
                return false;
            }
        }

        if (s.length() > 0 && (p.charAt(0) == '?' || s.charAt(0) == p.charAt(0))) {
            if (p.length() == 0) {
                return false;
            }
            return isMatch(s.substring(1), p.substring(1));
        } else if (p.charAt(0) == '*') {

            if(p.length() == 1){
                return true;
            } else if(p.charAt(1)=='*'){
                return isMatch(s,p.substring(1));
            }

            boolean res;

            for (int i = 0; i < s.length(); i++) {
                res = isMatch(s.substring(i), p.substring(1));
                if (res) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<String> removeInvalidParentheses(String s) {

        if (s == null) {
            return null;
        }

        int n = s.length();

        if (n == 0) {
            return new ArrayList<>();
        }

        check(s, "", 0, 0);

        List<String> finalAns = new ArrayList<String>();

        for (String r : res) {
            if (r.length() == max) {
                finalAns.add(r);
            }
        }

        return finalAns;
    }

    void check(String s, String ans, int l, int r) {
        if (s.length() == 0) {
            if (ans.length() >= max && l == r) {
                res.add(ans);
                max = ans.length();
            }
            return;
        }

        if (l >= r) {
            if (s.charAt(0) == '(') {
                check(s.substring(1), ans + '(', l + 1, r);
            } else {
                check(s.substring(1), ans, l, r);
                for (int j = ans.length() - 1; j >= 0; j--) {
                    if (ans.charAt(j) == ')') {
                        check(s.substring(1), ans.substring(0, j - 1) + ans.substring(j + 1) + ')', l, r);
                    }
                }
            }
        }
    }
}