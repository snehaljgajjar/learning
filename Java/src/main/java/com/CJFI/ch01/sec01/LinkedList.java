package com.CJFI.ch01.sec01;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Function;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : pgajjar
 * @since : 7/1/16
 */
public class LinkedList<E> {
    private List<E> _elements;

    public LinkedList() {
        _elements = Lists.newArrayList();
    }

    public LinkedList(E element) {
        _elements = Lists.newArrayList(element);
    }

    public LinkedList(E... elements) {
        _elements = Lists.newArrayList(elements);
    }

    public int size() { return _elements.size(); }

    public E getElements(int index) {
        return _elements.get(index);
    }

    public E getElementFromEnd(int index) {
        if (size() <= index) { return null; }
        return _elements.get(size() - index);
    }

    public void insertAfter(int index, E element) {
        _elements.add(index, element);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (E element : _elements) {
            stringBuilder.append(element + ", ");
        }
        return stringBuilder.toString();
    }

    public static class LocaleCode {
        private String name;

        public LocaleCode(String _name) {
            name = _name;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return getName();
        }
    }

    public static void main(String[] args) {
        LinkedList<String> names = new LinkedList<String>("Pradip", "Rakesh", "Aarav", "Swara");
        names.insertAfter(2, "Gajjar");
        System.out.println(names);

        LinkedList<Integer> numbers = new LinkedList<Integer>(10, 20, 30 ,40);
        System.out.println(numbers);

        LinkedList<Test> tests = new LinkedList<Test>(new Test(10, 20), new Test(30, 40), new Test(50, 60));
        System.out.println(tests);

        LinkedHashMultimap<Integer, String> data = LinkedHashMultimap.create();
        data.put(35, "Pradip");
        data.put(33, "Rakesh");
        data.put(33, "Poonam");
        data.put(32, "Dhara");
        data.put(35, "Prakash");

        System.out.println(data);

        Set<LocaleCode> surnames = new LinkedHashSet<LocaleCode>();
        surnames.add(new LocaleCode("Gajjar"));
        surnames.add(new LocaleCode("Patel"));
        surnames.add(new LocaleCode("Shah"));
        surnames.add(new LocaleCode("Joshi"));
        surnames.add(new LocaleCode("Ambani"));

        List<LocaleCode> sorted = Ordering.natural().onResultOf(LocaleCode::getName).sortedCopy(surnames);

        System.out.println("Surnames: " + surnames);
        System.out.println("Sorted: " + sorted);
    }
}
