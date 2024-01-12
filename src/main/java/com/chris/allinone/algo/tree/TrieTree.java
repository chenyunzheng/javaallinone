package com.chris.allinone.algo.tree;

public class TrieTree {

    private static final int COUNT = 26;

    //根据字符数构造N叉树(N叉固定情形，比如26个英文小写字符)
    public static class TrieNode {
        char data;
        TrieNode[] children;
        boolean isLeaf;

        public TrieNode(char data) {
            this.data = data;
            children = new TrieNode[COUNT];
        }
    }

    private TrieNode root = new TrieNode('/');

    private void insert(char[] toCharArray) {
        TrieNode node = root;
        for (int i = 0; i < toCharArray.length; i++) {
            int index = toCharArray[i] - 97;
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(toCharArray[i]);
            }
            node = node.children[index];
        }
        node.isLeaf = true;
    }

    private boolean find(char[] toCharArray) {
        TrieNode node = root;
        for (int i = 0; i < toCharArray.length; i++) {
            int index = toCharArray[i] - 97;
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isLeaf;
    }

    public static void main(String[] args) {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("hello".toCharArray());
        trieTree.insert("he".toCharArray());
        trieTree.insert("see".toCharArray());
        trieTree.insert("so".toCharArray());
        System.out.println("===== add data to trie tree");
        boolean findOrNot = trieTree.find("see".toCharArray());
        System.out.println(findOrNot);
    }




}
