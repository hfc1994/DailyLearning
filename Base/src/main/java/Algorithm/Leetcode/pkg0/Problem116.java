package Algorithm.Leetcode.pkg0;

import Algorithm.Leetcode.Node;

import java.util.LinkedList;

/**
 * Created by user-hfc on 2021/9/7.
 *
 * 116. 填充每个节点的下一个右侧节点指针
 *
 * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
 *
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 *
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，
 * 则将 next 指针设置为 NULL。
 * 初始状态下，所有 next 指针都被设置为 NULL。
 *
 * 进阶：
 * 你只能使用常量级额外空间。
 * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
 *
 * 示例：
 *
 *            1                   1 →
 *          /   \             /      \
 *         2     3           2   →   3 →
 *        / \   / \         / \     /  \
 *       4  5  6   7       4 → 5 → 6 → 7
 *
 * 输入：root = [1,2,3,4,5,6,7]
 * 输出：[1,#,2,3,#,4,5,6,7,#]
 * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个
 * 右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 next 指针连接，
 * '#' 标志着每一层的结束。
 *
 * 提示：
 * 树中节点的数量少于 4096
 * -1000 <= node.val <= 1000
 *
 */
public class Problem116 {

    public Node connect_1(Node root) {
        if (root != null) {
            LinkedList<Node> list = new LinkedList<>();
            list.add(root);
            doBfs(list);
        }
        return root;
    }

    // 广度优先遍历
    private void doBfs(LinkedList<Node> list) {
        if (list.size() == 0)
            return;

        int count = list.size();
        Node prev, cur = null;
        while (count-- != 0) {
            prev = cur;
            cur = list.removeFirst();

            if (prev != null)
                prev.next = cur;

            if (cur.left != null) {
                list.add(cur.left);
                list.add(cur.right);
            }
        }

        doBfs(list);
    }

    // 参考自leetcode评论区，递归版，内存占用较低
    public Node connect(Node root) {
        if (root == null || root.left == null)
            return root;

        root.left.next = root.right;
        if (root.next != null)
            root.right.next = root.next.left;

        connect(root.left);
        connect(root.right);

        return root;
    }

    public static void main(String[] args) {
        Problem116 p = new Problem116();

        // 1,2,3,4,5,6,7
    }

}
