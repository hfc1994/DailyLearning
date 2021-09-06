package Algorithm.Leetcode.pkg0;

import Algorithm.Leetcode.TreeNode;

/**
 * Created by user-hfc on 2020/9/25.
 *
 * 106. 从中序与后序遍历序列构造二叉树
 *
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 *
 * 返回如下的二叉树：
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 */
public class Problem106 {

    private int[] inorder;
    private int[] postorder;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.inorder = inorder;
        this.postorder = postorder;
        return buildNode(0, inorder.length-1, 0, postorder.length-1);
    }

    private TreeNode buildNode(int iBeginIdx, int iEndIdx, int pBeginIdx, int pEndIdx) {
        if (pBeginIdx > pEndIdx) return null;

        int val = postorder[pEndIdx];
        TreeNode root = new TreeNode(val);
        int midIdx = 0;
        for (int i=iBeginIdx; i<=iEndIdx; i++) {
            if (inorder[i] == val) midIdx = i;
        }

        root.left = buildNode(iBeginIdx, midIdx-1, pBeginIdx, pBeginIdx + midIdx - iBeginIdx - 1);
        root.right = buildNode(midIdx+1, iEndIdx, pBeginIdx + midIdx - iBeginIdx, pEndIdx-1);
        return root;
    }

    public static void main(String[] args) {
        Problem106 p = new Problem106();

        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        TreeNode root = p.buildTree(inorder, postorder);
        System.out.print("[");
        for (Integer i : TreeNode.toArray(root)) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
    }

}
