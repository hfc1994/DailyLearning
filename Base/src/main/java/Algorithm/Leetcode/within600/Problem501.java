package Algorithm.Leetcode.within600;

import Algorithm.Leetcode.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by user-hfc on 2020/9/24.
 *
 * 501. 二叉搜索树中的众数
 *
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 * 假定 BST 有如下定义：
 * 结点左子树中所含结点的值小于等于当前结点的值
 * 结点右子树中所含结点的值大于等于当前结点的值
 * 左子树和右子树都是二叉搜索树
 *
 * 例如：
 * 给定 BST [1,null,2,2],
 *    1
 *     \
 *      2
 *     /
 *    2
 * 返回[2].
 *
 * 提示：如果众数超过1个，不需考虑输出顺序
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 *
 */
public class Problem501 {

    public int[] findMode(TreeNode root) {
//        return solution1(root);
        return solution2(root);
    }


    private Map<Integer, Integer> stats;
    private int maxCount;

    public int[] solution1(TreeNode root) {
        stats = new HashMap<>();
        doDfs(root);

        List<Integer> list = new LinkedList<>();
        stats.forEach((key, value) -> {
            if (value == maxCount)
                list.add(key);
        });

        int[] ret = new int[list.size()];
        for (int i=0; i<list.size(); i++)
            ret[i] = list.get(i);

        return ret;
    }

    private List<Integer> list;
    private int base;
    private int cur;
    private int count;
    private int total;
    public int[] solution2(TreeNode root) {
        if (root == null) return new int[0];

        list = new LinkedList<>();
        base = cur = count = total = 0;
        doDfs2(root);
        if (count == 0)
            list.add(cur);
        else if (count < 0) {
            list.clear();
            list.add(cur);
        }

        int[] ret = new int[list.size()];
        for (int i=0; i<list.size(); i++)
            ret[i] = list.get(i);
        return ret;
    }

    private void doDfs(TreeNode node) {
        if (node == null)
            return;

        int count = stats.getOrDefault(node.val, 0) + 1;
        maxCount = count > maxCount ? count : maxCount;
        stats.put(node.val, count);

        doDfs(node.left);
        doDfs(node.right);
    }

    private void doDfs2(TreeNode node) {
        if (node == null) return;

        doDfs2(node.left);
        if (total == 0) {   // 最初始的阶段
            base = cur = node.val;
            total = count = 1;
            list.add(cur);
        } else {
            if (base == node.val) { // 计算第一组众数
                total = ++count;
            } else if (cur == node.val) {
                // 在cur不变的情况下，每遇到一个不同于cur的数就减一
                // 当最后count==0则说明出现了新增了众数;count<0则说明出现了全新的众数
                count--;
            } else {
                if (count < 0) {
                    list.clear();
                    total = total - count;
                    base = cur;
                    list.add(cur);
                } else if (count == 0) {
                    list.add(cur);
                }

                cur = node.val;
                count = total - 1;
            }
        }
        doDfs2(node.right);
    }

    public static void main(String[] args) {
        Problem501 p = new Problem501();

        Integer[] nums = {};
        System.out.print("[");
        for (int i : p.findMode(TreeNode.buildTree(nums))) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        nums = new Integer[]{1, null, 2, 2};
        System.out.print("[");
        for (int i : p.findMode(TreeNode.buildTree(nums))) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        nums = new Integer[]{1,null,2,2,3,2,null,null,4,null,null,4,5,null,null,5,5,null,null,null,6,6,6};
        System.out.print("[");
        for (int i : p.findMode(TreeNode.buildTree(nums))) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        nums = new Integer[]{1,null,2,2,3,2,null,null,4,null,null,4,5,null,null,5,5,null,null,null,6,6,6,6};
        System.out.print("[");
        for (int i : p.findMode(TreeNode.buildTree(nums))) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        nums = new Integer[]{2,1,3,0,2,null,null,null,null,2,null,1,null,null,1};
        System.out.print("[");
        for (int i : p.findMode(TreeNode.buildTree(nums))) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
    }
}
