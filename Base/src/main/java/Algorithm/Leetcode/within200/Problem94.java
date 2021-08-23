package Algorithm.Leetcode.within200;

import Algorithm.Leetcode.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2020/9/14.
 *
 * 94. 二叉树的中序遍历
 *
 * 给定一个二叉树，返回它的中序 遍历。
 *
 * 示例:
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,3,2]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 */
public class Problem94 {

    public List<Integer> inorderTraversal(TreeNode root) {
//        return solution1(root);
        return solution2(root);
    }

    // 递归法
    public List<Integer> solution1(TreeNode root) {
        List<Integer> ret = new LinkedList<>();
        inorder(root, ret);
        return ret;
    }

    // 迭代法
    public List<Integer> solution2(TreeNode root) {
        List<Integer> ret = new LinkedList<>();
        Deque<TreeNode> queue = new LinkedList<>();

        TreeNode cur = root;
        while (cur != null || !queue.isEmpty()) {
            if (cur == null) {
                cur = queue.pop();
                ret.add(cur.val);
                cur = cur.right;
            } else {
                queue.push(cur);
                cur = cur.left;
            }
        }

        return ret;
    }

    private void inorder(TreeNode cur, List<Integer> ret) {
        if (cur == null)
            return;

        inorder(cur.left, ret);
        ret.add(cur.val);
        inorder(cur.right, ret);
    }

    public static void main(String[] args) {
        Problem94 p = new Problem94();

        Integer[] nums = {1, null, 2, 3};
        TreeNode root = TreeNode.buildTree(nums);
        System.out.print("[");
        p.inorderTraversal(root).forEach(val -> System.out.print(val + ","));
        System.out.println("]");

        nums = new Integer[]{1, null, 2, 3, 4, null, 5, 6, null, 7, null, 8, null, 9, 10, null, null, 11};
        root = TreeNode.buildTree(nums);
        System.out.print("[");
        p.inorderTraversal(root).forEach(val -> System.out.print(val + ","));
        System.out.println("]");
    }

}
