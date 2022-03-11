package Algorithm.Leetcode.pkg400;

import java.util.List;

/**
 * Created by hfc on 2022/3/10.
 */
public class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }
}
