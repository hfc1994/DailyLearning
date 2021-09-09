package Algorithm.Leetcode.pkg600;

import Algorithm.Leetcode.TreeNode;

import java.util.Arrays;

/**
 * Created by user-hfc on 2021/9/7.
 *
 * 617. 合并二叉树
 *
 * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
 * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加
 * 作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。
 *
 * 示例 1:
 * 输入:
 * 	      Tree 1                     Tree 2
 *           1                         2
 *          / \                       / \
 *         3   2                     1   3
 *        /                           \   \
 *       5                             4   7
 *
 * 输出:
 * 合并后的树:
 * 	     3
 * 	    / \
 * 	   4   5
 * 	  / \   \
 * 	 5   4   7
 * 注意: 合并必须从两个树的根节点开始。
 *
 */
public class Problem617 {

    public TreeNode mergeTrees_1(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return null;

        TreeNode cur = new TreeNode();
        if (root1 != null) cur.val += root1.val;
        if (root2 != null) cur.val += root2.val;

        cur.left = mergeTrees_1(root1 == null ? null : root1.left,
                root2 == null ? null : root2.left);

        cur.right = mergeTrees_1(root1 == null ? null : root1.right,
                root2 == null ? null : root2.right);

        return cur;
    }

    // 参考自leetcode，速度更快
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return null;

        if (root1 == null) return root2;
        else if (root2 == null) return root1;
        else root1.val += root2.val;

        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    public static void main(String[] args) {
        Problem617 p = new Problem617();

        TreeNode root1 = TreeNode.buildTree(new Integer[] {1,3,2,5});
        TreeNode root2 = TreeNode.buildTree(new Integer[] {2,1,3,null,4,null,7});
        // [3,4,5,5,4,null,7]
        System.out.println(Arrays.toString(TreeNode.toArray(p.mergeTrees(root1, root2))));

        root1 = TreeNode.buildTree(new Integer[] {1});
        root2 = TreeNode.buildTree(new Integer[] {2});
        // [3]
        System.out.println(Arrays.toString(TreeNode.toArray(p.mergeTrees(root1, root2))));

        root1 = TreeNode.buildTree(new Integer[] {1,3,2,5,null,8,3,null,5,null,6,null,7});
        root2 = TreeNode.buildTree(new Integer[] {2,null,1,3,null,4,null,7,2,null,6,5,4,null,6});
        // [3,3,3,5,null,11,3,null,5,4,6,null,7,7,2,null,6,5,4,null,6]
        System.out.println(Arrays.toString(TreeNode.toArray(p.mergeTrees(root1, root2))));
    }

}
