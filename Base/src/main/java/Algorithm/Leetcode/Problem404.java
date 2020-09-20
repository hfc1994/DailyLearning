package Algorithm.Leetcode;

/**
 * Created by hfc on 2020/9/19.
 *
 * 404. 左叶子之和
 *
 * 计算给定二叉树的所有左叶子之和。
 *
 * 示例：
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
 *
 */
public class Problem404 {

    public int sumOfLeftLeaves(TreeNode root) {
        return doDfs(root, false);
    }

    private int doDfs(TreeNode node, boolean left) {
        int val = 0;

        if (node == null) return val;
        if (left && node.left == null && node.right==null) val = node.val;

        return val + doDfs(node.left, true) + doDfs(node.right, false);
    }

    public static void main(String[] args) {
        Problem404 p = new Problem404();

        TreeNode root = TreeNode.buildTree(new Integer[]{3, 9, 20, null, null, 15, 7});
        System.out.println(p.sumOfLeftLeaves(root));

        root = TreeNode.buildTree(new Integer[]{3,2,null,null,4,null,9,20,null,12,15,7,null,18,null,10,12,null,null,20});
        System.out.println(p.sumOfLeftLeaves(root));

        root = TreeNode.buildTree(new Integer[]{3, 2, 4, 9, 20, 12, 15, 7, 18, 10, 12, 20});
        System.out.println(p.sumOfLeftLeaves(root));
    }
}
