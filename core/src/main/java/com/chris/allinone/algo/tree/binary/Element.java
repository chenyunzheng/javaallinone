package com.chris.allinone.algo.tree.binary;

import lombok.Data;

/**
 * @author chrischen
 */
@Data
public class Element implements Comparable<Element> {

    private Integer e;

    public Element(int e) {
        this.e = e;
    }

    @Override
    public int compareTo(Element o) {
        return this.e - o.e;
        //return o.e - this.e;
    }
}
