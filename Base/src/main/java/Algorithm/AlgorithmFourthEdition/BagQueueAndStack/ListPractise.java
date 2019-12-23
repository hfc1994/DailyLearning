package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/15.
 *
 * 当前测试下面的方法，默认链表的长度都是大于1的
 * 也就是不考虑不处理长度为1的链表
 * 因为方法调用在此处都是引用传递，方法里删除了first结点并不会反应到main函数域
 */
public class ListPractise {

    public static void main(String[] args) {
        Node<String> node4 = new Node<>("eee");
        Node<String> node3 = new Node<>("ddd", node4);
        Node<String> node2 = new Node<>("ccc", node3);
        Node<String> node1 = new Node<>("bbb", node2);
        Node<String> node = new Node<>("aaa", node1);

        printTool(node);
        // 1.3.19
//        delTail(node);
//        printTool(node);

        // 1.3.20
//        delete(node, 3);
//        printTool(node);

        // 1.3.21
//        System.out.println(find(node, "ccc"));
//        System.out.println(find(node, "ccd"));

        // 1.3.24
//        removeAfter(node, node3);
//        printTool(node);

        // 1.3.26
//        Node<String> node5 = new Node<>("ccc");
//        node4.next = node5;
//        node5.prev = node4;
//        printTool(node);
//        remove(node, "ccc");
//        printTool(node);

        // 1.3.27
//        Node<String> node5 = new Node<>("ooooo");
//        node4.next = node5;
//        node5.prev = node4;
//        System.out.println(max(node));

        // 1.3.28
        Node<String> node5 = new Node<>("ppppp");
        node4.next = node5;
        node5.prev = node4;
        System.out.println(maxRecursion(node));
    }

    public static void printTool(Node<String> first) {
        Node<String> current = first;
        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
        System.out.println("---over---");
    }

    /**
     * 1.3.19 给出一段代码，删除链表的尾节点，其中链表的首结点是first
     */
    public static void delTail(Node<String> first) {
        if (null == first)
            return;
        Node<String> tail = first;
        while (tail.next != null) {
            tail = tail.next;
        }

        if (tail.prev == null) {
            // 只有一个first结点
            // 且无法做出有效的操作
        } else {
            Node<String> prev = tail.prev;
            tail.prev = null;
            prev.next = null;
        }
    }

    /**
     * 1.3.20 编写一个方法delete()，接受一个int参数k，删除链表的第k个元素（如果它存在的话）。
     */
    public static void delete(Node<String> first, int k) {
        if (first == null)
            return;

        Node<String> current = first;
        int index = 1;
        do {
            current = current.next;
            index++;
            if (index == k) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            }
        } while (current != null && index != k);
    }

    /**
     * 1.3.21 编写一个方法find()，接受一条链表和一个字符串key作为参数。
     * 如果链表中的某个结点的item域的值为key，则方法返回true，否则返回false。
     */
    public static boolean find(Node<String> first, String key) {
        Node<String> current = first;
        while (current != null) {
            if (key.equals(current.value))
                return true;
            current = current.next;
        }
        return false;
    }

    /**
     * 1.3.24 编写一个方法removeAfter()，接受一个链表结点作为参数，
     * 并删除该结点的后续结点（如果参数结点或参数结点的后续结点为空则什么也不做）
     */
    public static void removeAfter(Node<String> first, Node<String> target) {
        if (first == null)
            return;

        Node<String> current = first;
        while (current != null) {
            if (current.value.equals(target.value)) {
                if (current == first)
                    return;
                current.prev.next = null;
                current.prev = null;
                current = null;
            } else
                current = current.next;
        }
    }

    /**
     * 1.3.26 编写一个方法remove()，接受一条链表和一个字符串key作为参数，删除链表中
     * 所有item域为key的结点。
     */
    public static void remove(Node<String> first, String key) {
        if (first == null)
            return;

        Node<String> current = first;
        while (current != null) {
            if (current.value.equals(key)) {
                if (current == first)
                    return;
                else {
                    current.prev.next = current.next;
                    if (null != current.next)
                        current.next.prev = current.prev;
                }
            }
            current = current.next;
        }
    }

    /**
     * 1.3.27 编写一个方法max()，接受一条链表的首结点作为参数，返回链表中键最大的结点的值
     * （就认为是item最长吧）
     */
    public static String max(Node<String> first) {
        if (first == null)
            return null;

        Node<String> maxNode = first;
        Node<String> current = first;
        while (current != null) {
            if (current.value.length() > maxNode.value.length()) {
                maxNode = current;
            }
            current = current.next;
        }
        return maxNode.value;
    }

    /**
     * 1.3.28 用递归的方法解答上一道练习
     */
    public static String maxRecursion(Node<String> first) {
        if (first == null)
            return null;

        String ret = first.value;
        return compareMax(first.next, ret);
    }

    private static String compareMax(Node<String> target, String key) {
        if (target == null)
            return key;
        else if (target.value.length() > key.length())
            key = target.value;

        return compareMax(target.next, key);
    }

}
