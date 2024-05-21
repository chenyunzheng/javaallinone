package com.chris.allinone.algo.tree.binary;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author chrischen
 * 最大堆/最小堆（大顶堆/小顶堆）
 */
public class MinOrMaxHeap<T extends Comparable<T>> {

    public T[] transfer(T[] source, Class<T> clazz) {
        Objects.requireNonNull(source);
        T[] array = (T[]) Array.newInstance(clazz, source.length);
        for (int k = 0; k < source.length; k++) {
            T e = source[k];
            int j = k;
            while (j > 0) {
                int parent = (j - 1) >>> 1;
                T p = array[parent];
                if (p.compareTo(e) >= 0) {
                    break;
                }
                //下沉
                array[j] = p;
                j = parent;
            }
            array[j] = e;
        }
        return array;
    }

    public static void main(String[] args) {
        MinOrMaxHeap<Element> heap = new MinOrMaxHeap<>();
        Element[] elements = new Element[] {
                new Element(1),
                new Element(56),
                new Element(99),
                new Element(32),
                new Element(100),
                new Element(23),
        };
        Arrays.stream(heap.transfer(elements, Element.class)).forEach(element -> System.out.println(element.getE()));
    }
}
