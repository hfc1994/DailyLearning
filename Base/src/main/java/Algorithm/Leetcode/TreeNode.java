package Algorithm.Leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by user-hfc on 2020/9/14.
 */
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    /**
     * 根据源数据构建树
     * 输入： [1,null,2,3]
     *
     * 输出：
     *     1
     *      \
     *       2
     *      /
     *     3
     */
    public static TreeNode buildTree(Integer[] src) {
        if (src.length == 0)
            return null;

        TreeNode root = new TreeNode(src[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int count = 1, index = 1;
        TreeNode cur, tmp;
        Integer val;
        for (int i=1; i<=count; i++) {
            cur = queue.remove();
            for (int j=0; j<2 && index<src.length; j++) {
                val = src[index++];
                if (val != null) {
                    tmp = new TreeNode(val);
                    queue.add(tmp);
                    if (j % 2 == 0) cur.left = tmp;
                    else cur.right = tmp;
                }
            }

            if (i == count) {
                i = 0;
                count = queue.size();
            }
        }

        return root;
    }

    /**
     * 根据树输出其源数据
     * 输入：
     *     1
     *      \
     *       2
     *      /
     *     3
     *
     * 输出： [1,null,2,3]
     */
    public static Integer[] toArray(TreeNode root) {
        List<Integer> ret = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode cur;
        while (!queue.isEmpty()) {
            cur = queue.remove();
            ret.add(cur == null ? null : cur.val);
            if (cur != null && (cur.left != null || cur.right != null)) {
                queue.add(cur.left);
                queue.add(cur.right);
            }
        }

        return ret.toArray(new Integer[0]);
    }
}
