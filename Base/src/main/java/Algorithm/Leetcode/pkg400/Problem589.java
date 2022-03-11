package Algorithm.Leetcode.pkg400;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.*;

/**
 * Created by hfc on 2022/3/10.
 *
 * 589. N 叉树的前序遍历
 *
 * 给定一个 n 叉树的根节点  root ，返回 其节点值的 前序遍历 。
 * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
 *
 * 示例 1：
 *              1
 *           /  \  \
 *          3   2   4
 *        /  \
 *       5    6
 * 输入：root = [1,null,3,2,4,null,5,6]
 * 输出：[1,3,5,6,2,4]
 *
 * 示例 2：
 *               1
 *         /   /   \   \
 *       2    3    4    5
 *          /  \   \   / \
 *         6   7   8  9  10
 *             \   \  \
 *             11  12 13
 *             \
 *             14
 * 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * 输出：[1,2,3,6,7,11,14,4,8,12,5,9,13,10]
 *
 * 提示：
 * 节点总数在范围 [0, 10^4]内
 * 0 <= Node.val <= 10^4
 * n 叉树的高度小于或等于 1000
 *
 * 进阶：递归法很简单，你可以使用迭代法完成此题吗?
 *
 */
public class Problem589 {

    /**
     * 递归法
     * 速度 45%
     * 内存 24%
     */
    public List<Integer> preorder1(Node root) {
        List<Integer> preList = new LinkedList<>();
        if (root != null) {
            this.doDfs(root, preList);
        }
        return preList;
    }

    private void doDfs(Node node, List<Integer> results) {
        results.add(node.val);
        if (node.children != null) {
            for (int i = 0; i < node.children.size(); i++) {
                this.doDfs(node.children.get(i), results);
            }
        }
    }

    /**
     * 迭代法
     * 速度 32%
     * 内存 38%
     */
    public List<Integer> preorder(Node root) {
        if (root == null) return Collections.emptyList();

        List<Integer> preList = new LinkedList<>();
        Deque<Node> queue = new ArrayDeque<>();
        queue.push(root);
        while (!queue.isEmpty()) {
            Node node = queue.pop();
            preList.add(node.val);
            if (node.children != null) {
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    queue.push(node.children.get(i));
                }
            }
        }

        return preList;
    }

    public static void main(String[] args) {
        Problem589 p = new Problem589();

        Node node = new Node(3, Arrays.asList(new Node(5), new Node(6)));
        Node root = new Node(1, Arrays.asList(node, new Node(2), new Node(4)));
        System.out.println(LeetcodeUtil.equalsOfList(
                Arrays.asList(1, 3, 5, 6, 2, 4),
                p.preorder(root)));

        node = new Node(11, Collections.singletonList(new Node(14)));
        node = new Node(7, Collections.singletonList(node));
        Node node_3 = new Node(3, Arrays.asList(new Node(6), node));
        node = new Node(8, Collections.singletonList(new Node(12)));
        Node node_4 = new Node(4, Collections.singletonList(node));
        node = new Node(9, Collections.singletonList(new Node(13)));
        Node node_5 = new Node(5, Arrays.asList(node, new Node(10)));
        root = new Node(1, Arrays.asList(new Node(2), node_3, node_4, node_5));
        System.out.println(LeetcodeUtil.equalsOfList(
                Arrays.asList(1, 2, 3, 6, 7, 11, 14, 4, 8, 12, 5, 9, 13, 10),
                p.preorder(root)));

        root = new Node(1);
        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1), p.preorder(root)));

        System.out.println(LeetcodeUtil.equalsOfList(Collections.emptyList(), p.preorder(null)));
    }
}
