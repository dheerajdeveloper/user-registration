package com.dheeraj.user.registration.controller;

import java.util.*;

/**
 * Created by dheeraj on 01/05/18
 */
public class LongestSubstring {

    public static void main(String[] args) {
        System.out.println(check("geeksforgeeks", "geeksgeeks"));
    }

    private static boolean check(String s1, String s2) {

        HashMap<Character, Queue<Integer>> m = new HashMap<>();

        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);

            if (!m.containsKey(c)) {
                m.put(c, new LinkedList<>());
            }

            m.get(c).add(i);


        }

        int curIndex = -1;
        int i = 0;
        for (i = 0; i < s2.length(); i++) {

            char c = s2.charAt(i);

            if(!m.containsKey(c)){
                return false;
            }

            int index = -2;

            while(index < curIndex && !m.get(c).isEmpty()){
                index = m.get(c).poll();
            }

            if(curIndex > index){
                return false;
            }


            curIndex = index;

        }

        if(i==s2.length()){
            return true;
        }

        return false;

    }
}
