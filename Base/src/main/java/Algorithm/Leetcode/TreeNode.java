package Algorithm.Leetcode;

import java.util.Deque;
import java.util.LinkedList;

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
     * 源数据形如: [1,null,2,3]
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
        Deque<TreeNode> queue = new LinkedList<>();
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

}
