package Algorithm.Leetcode.pkg200;

import Algorithm.Leetcode.TreeNode;

/**
 * Created by user-hfc on 2020/9/16.
 *
 * 226. 翻转二叉树
 *
 * 翻转一棵二叉树。
 *
 * 示例：
 * 输入：
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 *
 * 输出：
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 * 备注:
 * 这个问题是受到 Max Howell 的 原问题 启发的 ：
 * 谷歌：我们90％的工程师使用您编写的软件(Homebrew)，
 * 但是您却无法在面试时在白板上写出翻转二叉树这道题，这太糟糕了。
 */
public class Problem226 {

    public TreeNode invertTree(TreeNode root) {

        if (root == null) return root;

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    public static void main(String[] args) {
        Problem226 p = new Problem226();

        Integer[] nums = {4, 2, 7, 1, 3, 6, 9};
        TreeNode root = TreeNode.buildTree(nums);
        root = p.invertTree(root);
        System.out.print("[");
        for (Integer i : TreeNode.toArray(root)) {
            System.out.print(String.valueOf(i) + ", ");
        }
        System.out.println("]");

        System.out.println("-------------------");

        nums = new Integer[]{4, 2, 7, 1,};
        root = TreeNode.buildTree(nums);
        root = p.invertTree(root);
        System.out.print("[");
        for (Integer i : TreeNode.toArray(root)) {
            System.out.print(String.valueOf(i) + ", ");
        }
        System.out.println("]");

        System.out.println("-------------------");

        nums = new Integer[]{ 4 };
        root = TreeNode.buildTree(nums);
        root = p.invertTree(root);
        System.out.print("[");
        for (Integer i : TreeNode.toArray(root)) {
            System.out.print(String.valueOf(i) + ", ");
        }
        System.out.println("]");

        System.out.println("-------------------");

        nums = new Integer[]{};
        root = TreeNode.buildTree(nums);
        root = p.invertTree(root);
        System.out.print("[");
        for (Integer i : TreeNode.toArray(root)) {
            System.out.print(String.valueOf(i) + ", ");
        }
        System.out.println("]");
    }
}
