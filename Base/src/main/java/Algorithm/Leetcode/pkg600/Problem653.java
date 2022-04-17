package Algorithm.Leetcode.pkg600;

import Algorithm.Leetcode.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hfc on 2022/3/21.
 *
 * 653. 两数之和 IV - 输入 BST
 *
 * 给定一个二叉搜索树 root 和一个目标结果 k，如果 BST 中存在两个元素且它们的和
 * 等于给定的目标结果，则返回 true。
 *
 * 示例 1：
 *              5
 *            /   \
 *          3      6
 *        /  \      \
 *       2   4       7
 * 输入: root = [5,3,6,2,4,null,7], k = 9
 * 输出: true
 *
 * 示例 2：
 *              5
 *            /   \
 *          3      6
 *        /  \      \
 *       2   4       7
 * 输入: root = [5,3,6,2,4,null,7], k = 28
 * 输出: false
 *
 * 提示:
 * 二叉树的节点个数的范围是  [1, 10^4].
 * -10^4 <= Node.val <= 10^4
 * root 为二叉搜索树
 * -10^5 <= k <= 10^5
 *
 */
public class Problem653 {

    /**
     * 速度 11%
     * 内存 37%
     */
    public boolean findTarget1(TreeNode root, int k) {
        if (root == null)
            return false;

        List<Integer> list = new ArrayList<>();
        this.doDfs(root, list);

        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(i) + list.get(j) == k) {
                    return true;
                }

                if (list.get(j) < (k / 2)) {
                    break;
                }
            }
        }

        return false;
    }

    private void doDfs(TreeNode node, List<Integer> list) {
        if (node.left != null) {
            this.doDfs(node.left, list);
        }
        list.add(node.val);
        if (node.right != null) {
            this.doDfs(node.right, list);
        }
    }

    /**
     * 参考自题解
     */
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return this.doDfs(set, root, k);
    }

    private boolean doDfs(Set<Integer> set, TreeNode node, int k) {
        if (node == null)
            return false;

        if (set.contains(k - node.val))
            return true;

        set.add(node.val);
        return this.doDfs(set, node.left, k) || this.doDfs(set, node.right, k);
    }

    public static void main(String[] args) {
        Problem653 p = new Problem653();

        System.out.println(p.findTarget(TreeNode.buildTree(new Integer[] {5, 3, 6, 2, 4, null, 7}), 9));
        System.out.println(!p.findTarget(TreeNode.buildTree(new Integer[] {5, 3, 6, 2, 4, null, 7}), 28));
        System.out.println(p.findTarget(TreeNode.buildTree(new Integer[] {5, 3, 6, 2, 4, null}), 7));
        System.out.println(!p.findTarget(TreeNode.buildTree(new Integer[] {}), 7));
        System.out.println(!p.findTarget(TreeNode.buildTree(new Integer[] {0}), 0));
        System.out.println(!p.findTarget(TreeNode.buildTree(new Integer[] {2, null, 3}), 6));
        System.out.println(!p.findTarget(TreeNode.buildTree(new Integer[] {334, 277, 507, null, 285, null, 678}), 1014));
    }
}
