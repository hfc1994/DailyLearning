package Algorithm.Leetcode.within600;

import Algorithm.Leetcode.TreeNode;

/**
 * Created by user-hfc on 2020/9/21.
 *
 * 538. 把二叉搜索树转换为累加树
 *
 * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，
 * 使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 *
 * 例如：
 *
 * 输入: 原始二叉搜索树:
 *               5
 *             /   \
 *            2     13
 *
 * 输出: 转换为累加树:
 *              18
 *             /   \
 *           20     13
 *  
 *
 * 注意：本题和 1038: https://leetcode-cn.com/problems/binary-search-tree-to-greater-sum-tree/ 相同
 *
 */
public class Problem538 {

    public TreeNode convertBST(TreeNode root) {
        doDfs(root, 0);
        return root;
    }

    private int doDfs(TreeNode node, int acc) {
        if (node == null) return acc;

        node.val += doDfs(node.right, acc);
        return doDfs(node.left, node.val);
    }

    public static void main(String[] args) {
        Problem538 p = new Problem538();

        Integer[] nums = {5, 2, 13};
        TreeNode root = TreeNode.buildTree(nums);
        System.out.print("[");
        for (Integer val : TreeNode.toArray(p.convertBST(root))) {
            System.out.print(val + ", ");
        }
        System.out.println("]");

        nums = new Integer[]{6, 4, 8, 1, 5, 7, 9};
        root = TreeNode.buildTree(nums);
        System.out.print("[");
        for (Integer val : TreeNode.toArray(p.convertBST(root))) {
            System.out.print(val + ", ");
        }
        System.out.println("]");
    }
}
