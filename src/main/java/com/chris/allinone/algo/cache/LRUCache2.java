package com.chris.allinone.algo.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache2 {

    static class Node {
        String key;
        String value;
        Node next;
        Node pre;

        Node() {

        }

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<String, Node> cache;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache2(int capacity) {
        this.cache = new HashMap<>(capacity);
        this.capacity = capacity;
        this.head = new Node();
        this.tail = new Node();
        this.head.next = this.tail;
        this.tail.pre = this.head;
    }

    public String get(String key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        } else {
            //move to head
            return node.value;
        }
    }

    public void put(String key, String value) {
        Node vNode = cache.get(key);
        if (vNode == null) {
            //新添加(k, vNode)
            if (cache.size() >= capacity) {
                //删除双向链表末尾元素
                Node drop = tail.pre;
                Node dropPre = drop.pre;
                dropPre.next = tail;
                tail.pre = dropPre;
                //从map中移除末尾元素对应的key
                cache.remove(drop.key);
            }
            //添加新node
            addNewNode(key, value);
        } else {
            //更新node
            vNode.key = key;
            vNode.value = value;
            //切断现有连接
            Node oldPre = vNode.pre;
            Node oldNext = vNode.next;
            oldPre.next = oldNext;
            oldNext.pre = oldPre;
            //更新头节点
            Node headOldNext = head.next;
            headOldNext.pre = vNode;
            vNode.next = headOldNext;
            vNode.pre = head;
            head.next = vNode;
            cache.put(key, vNode);
        }
    }

    //头插
    private void addNewNode(String key, String value) {
        Node add = new Node(key, value);
        Node headOldNext = head.next;
        headOldNext.pre = add;
        add.next = headOldNext;
        add.pre = head;
        head.next = add;
        cache.put(key, add);
    }

    private void printAllKeys() {
        cache.keySet().forEach(System.out::println);
    }

    public static void main(String[] args) {
        LRUCache2 lruCache = new LRUCache2(3);
        lruCache.put("1","1");
        lruCache.put("2","2");
        lruCache.put("3","3");
        lruCache.put("4","4");
        lruCache.put("5","5");
        lruCache.put("6","6");
        lruCache.printAllKeys();
    }

}
