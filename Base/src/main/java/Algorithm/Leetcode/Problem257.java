package Algorithm.Leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2020/9/4.
 *
 * 257. 二叉树的所有路径
 *
 * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 输入:
 *
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 *
 * 输出: ["1->2->5", "1->3"]
 *
 * 解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3
 *
 */
public class Problem257 {

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new LinkedList<>();
        buildTreePaths(root, "", paths);
        return paths;
    }

    private void buildTreePaths(TreeNode node, String prefix, List<String> paths) {
        if (node == null)
            return;

        if ("".equals(prefix))
            prefix += node.val;
        else
            prefix += "->" + node.val;

        if (node.left == null && node.right == null)
            paths.add(prefix);

        buildTreePaths(node.left, prefix, paths);
        buildTreePaths(node.right, prefix, paths);
    }

    public static void main(String[] args) {
        Problem257 p = new Problem257();

        Integer[] nums = {1, 2, 3, null, 5};
        TreeNode root = TreeNode.buildTree(nums);
        System.out.print("[");
        p.binaryTreePaths(root).forEach(val -> System.out.print(val + ","));
        System.out.println("]");
    }
}
