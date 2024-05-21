package com.chris.allinone.algo.tree;

/**
 * 镜像二叉树
 */
public class MirrorBinaryTree {

    static class TreeNode {
        String data;
        TreeNode left;
        TreeNode right;
        public TreeNode(String data) {
            this.data = data;
        }
    }

    public static boolean isMirror(TreeNode leftNode, TreeNode rightNode) {
        if (leftNode == null && rightNode == null) {
            return true;
        }
        if (leftNode == null) {
            return false;
        }
        if (rightNode == null) {
            return false;
        }
        return leftNode.data.equals(rightNode.data) && isMirror(leftNode.left,rightNode.right)
                && isMirror(leftNode.right, rightNode.left);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode("root");
        TreeNode node1 = new TreeNode("1");
        TreeNode node2 = new TreeNode("1");
        TreeNode node3 = new TreeNode("2");
        TreeNode node4 = new TreeNode("3");
        TreeNode node5 = new TreeNode("2");
        TreeNode node6 = new TreeNode("3");
        //构建二叉树用于测试
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node6;
        node2.right = node5;
        System.out.println(MirrorBinaryTree.isMirror(root, root));

    }
}
