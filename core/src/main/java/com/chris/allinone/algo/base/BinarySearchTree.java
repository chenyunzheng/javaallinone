package com.chris.allinone.algo.base;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树
 */
public class BinarySearchTree {

    private TreeNode root;

    public static class TreeNode {
        int data;
        TreeNode leftChild;
        TreeNode rightChild;

        public TreeNode(int data) {
            this.data = data;
        }
    }

    public void insert(int data) {
        if (root == null){
            root = new TreeNode(data);
        } else {
            insertNode(root, data);
        }
    }

    private TreeNode insertNode(TreeNode node, int data) {
        if (node == null) {
            return new TreeNode(data);
        }

        if (node.data > data) {
            //左节点
            node.leftChild = insertNode(node.leftChild, data);
        } else if (node.data < data) {
            //右节点
            node.rightChild = insertNode(node.rightChild, data);
        } else {
            //节点自身
        }
        return node;
    }

    /**
     * 前序DFS遍历
     */
    private void preOrderOutput() {
        preOrder(root);
    }

    private void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data);
        preOrder(node.leftChild);
        preOrder(node.rightChild);
    }

    /**
     * 中序DFS遍历
     */
    private void midOrderOutput() {
        midOrder(root);
    }

    private void midOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        midOrder(node.leftChild);
        System.out.println(node.data);
        midOrder(node.rightChild);
    }

    /**
     * 后序DFS遍历
     */
    private void postOrderOutput() {
        postOrder(root);
    }

    private void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.leftChild);
        postOrder(node.rightChild);
        System.out.println(node.data);
    }

    /**
     * 广度优先遍历
     * 借助队列实现
     */
    public void bfs() {
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            System.out.println(node.data);
            if (node.leftChild != null) {
                nodeQueue.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                nodeQueue.offer(node.rightChild);
            }
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(10);
        bst.insert(7);
        bst.insert(8);
        bst.insert(9);
        bst.insert(15);
        bst.insert(13);
        bst.insert(11);
        bst.insert(20);
        System.out.println();
        //DFS：深度优先遍历
        //DFS - 前序：根左右
        System.out.println("DFS - 前序：根左右");
        bst.preOrderOutput();
        //DFS - 中序：左根右
        System.out.println("DFS - 中序：左根右(输出天然排序)");
        bst.midOrderOutput();
        //DFS - 后序：左右根
        System.out.println("DFS - 后序：左右根");
        bst.postOrderOutput();
        //广度优先遍历，借助队列实现
        System.out.println("广度优先遍历，借助队列实现");
        bst.bfs();
    }

}
