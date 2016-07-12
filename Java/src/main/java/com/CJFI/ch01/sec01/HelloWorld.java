package com.CJFI.ch01.sec01;

import com.google.appengine.repackaged.com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * @author : pgajjar
 * @since : 6/22/16
 */
public class HelloWorld {
    public static <E extends Number> double myAdd(E val1, E val2) {
        return val1.doubleValue() + val2.doubleValue();
    }

    public static double Add(Number val1, Number val2) {
        return val1.doubleValue()  + val2.doubleValue();
    }

    public static void main(String[] args) {
        Stack v = new Stack();
        v.push(5);
        v.push(2);
        v.push(1);
        while (!v.isEmpty()) {
            System.out.println(v.pop());
        }

        Hashtable<String, Long> table = new Hashtable<String, Long>();
        table.put("a", 100L);
        table.put("b", 200L);
        table.put("aa", 300L);
        table.put("d", 400L);

        HashMap<String, Long> map = new HashMap<String, Long>();
        map.put("a", 500L);
        map.put("b", 500L);
        map.put("aa", 500L);
        map.put("aac", 100L);

        TreeMap<String, Long> tree = new TreeMap<String, Long>();
        tree.put("a", 900L);
        tree.put("b", 1000L);
        tree.put("aa", 2000L);
        tree.put("d", 3000L);

        Set<Long> treeSet = Sets.newLinkedHashSet();
        treeSet.add(45L);
        treeSet.add(25L);
        treeSet.add(4L);
        treeSet.add(50L);

        for (Long l : treeSet) {
            System.out.println(l);
        }


        System.out.println("Table: " + table);
        System.out.println("Map: " + map);
        System.out.println("TreeMap: " + tree);
        System.out.println("TreeSet: " + treeSet);
        System.out.println("myAdd Double "+ myAdd(10.2D, 20.2D));
        System.out.println("Add Double "+ Add(10.2D, 20.2D));

    }

}
