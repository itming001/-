package com.li.tree;

import com.li.tree.model.TreeNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 遍历二叉树
 *
 * @author dell
 */
public class Test {

    public static void main(String[] args) {
        //实现二叉树的广度优先遍历
        List<List<Integer>> lists = levelOrder(getTreeNode());
        System.out.println(lists);
    }

    /**
     * 获取二叉树模拟数据
     * @return
     */
    public static TreeNode getTreeNode(){
        TreeNode treeNode1 = new TreeNode(10);
        TreeNode treeNodeL = new TreeNode(5);
        TreeNode treeNodeR = new TreeNode(66);
        TreeNode treeNode2LL = new TreeNode(23);
        TreeNode treeNode2LR = new TreeNode(64);
        TreeNode treeNode2RL = new TreeNode(79);
        TreeNode treeNode2RR = new TreeNode(55);
        TreeNode treeNode2LLL = new TreeNode(23);
        TreeNode treeNode2LLR = new TreeNode(47);
        TreeNode treeNode2LRL = new TreeNode(56);
        TreeNode treeNode2LRR = new TreeNode(42);
        treeNode1.setLeft(treeNodeL);
        treeNode1.setRight(treeNodeR);
        treeNodeL.setLeft(treeNode2LL);
        treeNodeL.setRight(treeNode2LR);
        treeNodeR.setLeft(treeNode2RL);
        treeNodeR.setRight(treeNode2RR);
        treeNode2LL.setLeft(treeNode2LLL);
        treeNode2LL.setRight(treeNode2LLR);
        treeNode2LR.setLeft(treeNode2LRL);
        treeNode2LR.setRight(treeNode2LRR);
        return treeNode1;
    }

    /**
     * 实现二叉树的广度优先遍历
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> allResults = new ArrayList<>();
        if (root == null) {
            return allResults;
        }
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            int size = nodes.size();
            List<Integer> results = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = nodes.poll();
                results.add(node.getVal());
                if (node.getLeft() != null) {
                    nodes.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    nodes.add(node.getRight());
                }
            }
            allResults.add(results);
        }
        return allResults;
    }
}
