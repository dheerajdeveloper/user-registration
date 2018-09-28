package com.dheeraj.user.registration.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by dheeraj on 02/05/18
 */
public class LongestUnique {

    public static void main(String[] args) {
        System.out.println(check("aabbcc", 3));
    }

    private static int check(String s, int k) {

        int i = 0;

        int count=0;
        int l=0;
        int r=0;
        LinkedList<Character> list = new LinkedList<>();

        HashSet<Character> set = new HashSet<>();


        while (i < s.length()) {


            Character currChar = s.charAt(i);

            if(set.size() < k){
                list.add(currChar);
                count++;

                if(!set.contains(currChar)){
                    set.add(currChar);
                }

            }




        }


        return 0;
    }
}

class Node {
    char c;
    Node next;
    Node prev;
}