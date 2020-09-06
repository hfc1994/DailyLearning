package Algorithm.Leetcode;

import java.util.*;

/**
 * Created by hfc on 2020/9/6.
 *
 * 107. 二叉树的层次遍历 II
 *
 * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 *
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回其自底向上的层次遍历为：
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]
 *
 */
public class Problem107 {

    public List<List<Integer>> levelOrderBottom(TreeNode root) {

        List<List<Integer>> ret = new LinkedList<>();

        if (root == null)
            return ret;

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int count = 1, chidCount = 0;
        TreeNode current;
        List<Integer> vals = new LinkedList<>();
        while (!queue.isEmpty()) {
            current = queue.remove();

            vals.add(current.val);
            if (current.left != null) {
                queue.add(current.left);
                chidCount++;
            }

            if (current.right != null) {
                queue.add(current.right);
                chidCount++;
            }

            count--;
            if (count == 0) {
                ret.add(vals);
                vals = new LinkedList<>();
                count = chidCount;
                chidCount = 0;
            }
        }

        Collections.reverse(ret);
        return ret;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        Problem107 p = new Problem107();

        TreeNode root = p.new TreeNode(3);
        root.left = p.new TreeNode(9);
        TreeNode right = p.new TreeNode(20);
        root.right = right;
        right.left = p.new TreeNode(15);
        right.right = p.new TreeNode(7);

        p.levelOrderBottom(root).forEach(childList -> {
            System.out.print("[");
            childList.forEach(child -> System.out.print(child + ","));
            System.out.print("]");
            System.out.println();
        });

        System.out.println("--------");

        root = p.new TreeNode(3);

        p.levelOrderBottom(root).forEach(childList -> {
            System.out.print("[");
            childList.forEach(child -> System.out.print(child + ","));
            System.out.print("]");
            System.out.println();
        });

        System.out.println("--------");

        root = p.new TreeNode(3);
        TreeNode left = p.new TreeNode(9);
        root.left = left;
        left.left = p.new TreeNode(4);
        left.right = p.new TreeNode(12);
        right = p.new TreeNode(20);
        root.right = right;
        right.left = p.new TreeNode(15);
        right.right = p.new TreeNode(7);

        p.levelOrderBottom(root).forEach(childList -> {
            System.out.print("[");
            childList.forEach(child -> System.out.print(child + ","));
            System.out.print("]");
            System.out.println();
        });

        System.out.println("--------");

        root = p.new TreeNode();

        p.levelOrderBottom(root).forEach(childList -> {
            System.out.print("[");
            childList.forEach(child -> System.out.print(child + ","));
            System.out.print("]");
            System.out.println();
        });

    }
}
