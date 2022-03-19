package Algorithm.Leetcode.pkg400;

import java.util.*;

/**
 * Created by hfc on 2022/3/16.
 *
 * 432. 全 O(1) 的数据结构
 *
 * 请你设计一个用于存储字符串计数的数据结构，并能够返回计数最小和最大的字符串。
 * 实现 AllOne 类：
 * - AllOne() 初始化数据结构的对象。
 * - inc(String key) 字符串 key 的计数增加 1 。如果数据结构中尚不存在 key ，那么插入计数为 1 的 key 。
 * - dec(String key) 字符串 key 的计数减少 1 。如果 key 的计数在减少后为 0 ，那么需要将这个 key 从数据结构中删除。测试用例保证：在减少计数前，key 存在于数据结构中。
 * - getMaxKey() 返回任意一个计数最大的字符串。如果没有元素存在，返回一个空字符串 "" 。
 * - getMinKey() 返回任意一个计数最小的字符串。如果没有元素存在，返回一个空字符串 "" 。 
 *
 * 示例：
 * 输入
 * ["AllOne", "inc", "inc", "getMaxKey", "getMinKey", "inc", "getMaxKey", "getMinKey"]
 * [[], ["hello"], ["hello"], [], [], ["leet"], [], []]
 * 输出
 * [null, null, null, "hello", "hello", null, "hello", "leet"]
 * 解释
 * AllOne allOne = new AllOne();
 * allOne.inc("hello");
 * allOne.inc("hello");
 * allOne.getMaxKey(); // 返回 "hello"
 * allOne.getMinKey(); // 返回 "hello"
 * allOne.inc("leet");
 * allOne.getMaxKey(); // 返回 "hello"
 * allOne.getMinKey(); // 返回 "leet"
 *  
 * 提示：
 * 1 <= key.length <= 10
 * key 由小写英文字母组成
 * 测试用例保证：在每次调用 dec 时，数据结构中总存在 key
 * 最多调用 inc、dec、getMaxKey 和 getMinKey 方法 5 * 10^4 次
 *
 */
public class Problem432 {

    /**
     * 速度 23%
     * 内存 90%
     */
    class AllOne1 {

        Map<String, Integer> map;
        Node tail;
        Node head;

        public AllOne1() {
            this.map = new HashMap<>(256);
        }

        // head -> tail：cnt 从小到大
        public void inc(String key) {
            Integer cnt = this.map.get(key);
            if (cnt == null) {
                this.map.put(key, 1);
                Node node = new Node(1, key);
                if (head == null) {
                    head = node;
                    tail = node;
                } else if (head.cnt == 1) {
                    head.keys.add(key);
                } else {
                    node.next = head;
                    head = node;
                }
            } else {
                // cnt != null，说明对应 node 必然存在
                Node cur = head;
                Node prev = null;
                while (cur.cnt != cnt) {
                    prev = cur;
                    cur =cur.next;
                }

                // 删除原有的
                if (cur.keys.size() == 1) {
                    if (prev == null) {
                        head = cur.next;
                    } else {
                        prev.next = cur.next;
                    }

                    if (tail == cur) {
                        tail = cur.next;
                    }
                } else {
                    cur.keys.remove(key);
                    prev = cur;
                }
                cur = cur.next;

                // 添加新的
                cnt++;
                if (cur == null) {
                    Node n = new Node(cnt, key);
                    if (prev != null) {
                        prev.next = n;
                    } else {
                        head = n;
                    }
                    tail = n;
                } else if (cur.cnt > cnt) {
                    Node n = new Node(cnt, key);
                    n.next = cur;
                    if (prev != null) {
                        prev.next = n;
                    } else {
                        head = n;
                    }
                } else {
                    cur.keys.add(key);
                }

                this.map.put(key, cnt);
            }
        }

        public void dec(String key) {
            Integer cnt = this.map.get(key);
            if (cnt != null) {
                // cnt != null，说明对应 node 必然存在
                Node cur = head;
                Node prev = null;
                while (cur.cnt != cnt) {
                    prev = cur;
                    cur =cur.next;
                }

                // 删除原有的
                if (cur.keys.size() == 1) {
                    if (prev == null) {
                        head = cur.next;
                    } else {
                        prev.next = cur.next;
                    }

                    if (tail == cur) {
                        tail = prev;
                    }
                } else {
                    cur.keys.remove(key);
                }
                cur = prev;

                cnt--;
                if (cnt == 0) {
                    this.map.remove(key);
                    return;
                } else {
                    this.map.put(key, cnt);
                }

                if (cur == null) {
                    Node n = new Node(cnt, key);
                    n.next = head;
                    head = n;
                    if (tail == null)
                        tail = n;
                } else if (cur.cnt < cnt) {
                    Node n = new Node(cnt, key);
                    n.next = cur.next;
                    cur.next = n;
                    if (n.next == null)
                        tail = n;
                } else {
                    cur.keys.add(key);
                }
            }
        }

        public String getMaxKey() {
            return tail == null ? "" : tail.keys.get(0);
        }

        public String getMinKey() {
            return head == null ? "" : head.keys.get(0);
        }
    }

    class Node {
        Node next = null;
        int cnt;
        List<String> keys;

        public Node(int cnt, String key) {
            this.cnt = cnt;
            this.keys = new ArrayList<>();
            this.keys.add(key);
        }
    }

    /**
     * 题解给的灵感
     * 速度 54%
     * 内存 31%
     */
    class AllOne {

        Map<String, Integer> map;
        TreeMap<Integer, List<String>> treeMap;

        public AllOne() {
            map = new HashMap<>();
            treeMap = new TreeMap<>();
        }

        public void inc(String key) {
            int cnt = map.getOrDefault(key, 0);
            List<String> list;
            if (cnt != 0) {
                list = treeMap.get(cnt);
                if (list.size() == 1) {
                    treeMap.remove(cnt);
                } else {
                    list.remove(key);
                }
            }

            cnt++;
            list = treeMap.computeIfAbsent(cnt, k -> new ArrayList<>());
            list.add(key);
            if (list.size() == 1) {
                treeMap.put(cnt, list);
            }
            map.put(key, cnt);
        }

        public void dec(String key) {
            int cnt = map.get(key);
            List<String> list = treeMap.get(cnt);
            if (list.size() == 1) {
                treeMap.remove(cnt);
            } else {
                list.remove(key);
            }

            cnt--;
            if (cnt == 0) {
                map.remove(key);
            } else {
                list = treeMap.computeIfAbsent(cnt, k -> new ArrayList<>());
                list.add(key);
                map.put(key, cnt);
            }
        }

        public String getMaxKey() {
            return treeMap.size() == 0 ? "" : treeMap.lastEntry().getValue().get(0);
        }

        public String getMinKey() {
            return treeMap.size() == 0 ? "" : treeMap.firstEntry().getValue().get(0);
        }
    }

    public static void main(String[] args) {
        Problem432 p = new Problem432();

        AllOne allOne = p.new AllOne();
        allOne.inc("hello");
        allOne.inc("hello");    // hello 2
        System.out.println("hello".equals(allOne.getMaxKey()));
        System.out.println("hello".equals(allOne.getMinKey()));
        allOne.inc("leetcode"); // hello 2, leetcode 1
        System.out.println("hello".equals(allOne.getMaxKey()));
        System.out.println("leetcode".equals(allOne.getMinKey()));
        allOne.dec("hello");
        allOne.dec("hello");    // leetcode 1
        System.out.println("leetcode".equals(allOne.getMaxKey()));
        System.out.println("leetcode".equals(allOne.getMinKey()));
        allOne.inc("hello");
        allOne.inc("abcd");
        allOne.inc("hello");    // hello 2, leetcode 1, abcd 1
        System.out.println("hello".equals(allOne.getMaxKey()));
        allOne.inc("abcd");
        allOne.inc("leetcode");
        allOne.inc("leetcode"); // hello 2, leetcode 3, abcd 2
        System.out.println("leetcode".equals(allOne.getMaxKey()));
        allOne.inc("hello");
        allOne.dec("leetcode");
        allOne.dec("abcd");     // hello 3, leetcode 2, abcd 1
        System.out.println("hello".equals(allOne.getMaxKey()));
        System.out.println("abcd".equals(allOne.getMinKey()));
        allOne.dec("hello");
        allOne.inc("abcd");
        allOne.inc("abcd");
        allOne.dec("leetcode"); // hello 2, leetcode 1, abcd 3
        System.out.println("abcd".equals(allOne.getMaxKey()));
        System.out.println("leetcode".equals(allOne.getMinKey()));
        allOne.dec("leetcode");
        allOne.dec("hello");
        allOne.dec("hello");
        allOne.dec("abcd");
        allOne.dec("abcd");
        allOne.dec("abcd");
        System.out.println("".equals(allOne.getMaxKey()));
        System.out.println("".equals(allOne.getMinKey()));

        System.out.println("-------------");

        allOne = p.new AllOne();
        allOne.inc("hello");
        allOne.inc("goodbye");
        allOne.inc("hello");
        allOne.inc("hello"); // hello 3, goodbye 1
        System.out.println("hello".equals(allOne.getMaxKey()));
        allOne.inc("leet");
        allOne.inc("code");
        allOne.inc("leet");
        allOne.dec("hello"); // hello 2, leet 2, goodbye 1, code 1
        allOne.inc("leet");
        allOne.inc("code");
        allOne.inc("code"); // leet 3, code 3, hello 2, goodbye 1
        System.out.println("leet".equals(allOne.getMaxKey()));

        System.out.println("-------------");

        allOne = p.new AllOne();
        allOne.inc("a");
        allOne.inc("b");
        allOne.inc("b");
        allOne.inc("b");
        allOne.inc("b");
        allOne.dec("b");
        allOne.dec("b");
        System.out.println("b".equals(allOne.getMaxKey()));
        System.out.println("a".equals(allOne.getMinKey()));
    }
}
