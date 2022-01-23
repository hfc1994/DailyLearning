package Algorithm.Leetcode.pkg0;

import Algorithm.Leetcode.TreeNode;

/**
 * Created by hfc on 2022/1/21.
 *
 * 100. 相同的树
 *
 * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 *
 * 示例 1：
 *      1           1
 *    /  \        /  \
 *   2    3      2    3
 * 输入：p = [1,2,3], q = [1,2,3]
 * 输出：true
 *
 * 示例 2：
 *       1          1
 *     /             \
 *    2               2
 * 输入：p = [1,2], q = [1,null,2]
 * 输出：false
 *
 * 示例 3：
 *      1           1
 *    /  \        /  \
 *   2    1      1    2
 * 输入：p = [1,2,1], q = [1,1,2]
 * 输出：false
 *
 * 提示：
 * 两棵树上的节点数目都在范围 [0, 100] 内
 * -10^4 <= Node.val <= 10^4
 *
 */
public class Problem100 {

    /**
     * 速度 100%
     * 内存 90.8%
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p != null && q != null) {
            if (p.val != q.val) {
                return false;
            }
        } else {
            return false;
        }

        // 这种内存只超 5.5%
        // if (!isSameTree(p.left, q.left)) return false;
        // if (!isSameTree(p.right, q.right)) return false;

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        Problem100 p = new Problem100();

        System.out.println(p.isSameTree(TreeNode.buildTree(new Integer[] {}), TreeNode.buildTree(new Integer[] {})));
        System.out.println(!p.isSameTree(TreeNode.buildTree(new Integer[] {}), TreeNode.buildTree(new Integer[] { 1, 2, 3 })));
        System.out.println(p.isSameTree(TreeNode.buildTree(new Integer[] { 1, 2, 3 }), TreeNode.buildTree(new Integer[] { 1, 2, 3 })));
        System.out.println(!p.isSameTree(TreeNode.buildTree(new Integer[] { 1, 2 }), TreeNode.buildTree(new Integer[] { 1, null, 2 })));
        System.out.println(!p.isSameTree(TreeNode.buildTree(new Integer[] { 1, 2, 1 }), TreeNode.buildTree(new Integer[] { 1, 1, 2 })));
        System.out.println(p.isSameTree(TreeNode.buildTree(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 }),
                TreeNode.buildTree(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 })));
        System.out.println(!p.isSameTree(TreeNode.buildTree(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 }),
                TreeNode.buildTree(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 })));
    }

}
