package Algorithm.AlgorithmFourthEdition.Search;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/5/8.
 *
 * 3.1.2 开发一个符号表的实现ArrayST，使用（无序）
 * 数组来实现我们的基本API
 *
 * 暂不处理数组扩容
 */
public class ArrayST<k, v> implements ST<k, v> {

    private Node[] vals;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayST(int capacity) {
        vals = new ArrayST.Node[capacity];
    }

    @Override
    public void put(k key, v value) {
        if (key == null)
            return;

        int index = seek(key);
        if (index != -1) {
            if ( value == null) {
                // 删除
                for (int i=index; i<size-1; i++) {
                    vals[i] = vals[i+1];
                }
                vals[--size] = null;
            } else {
                // 更新
                vals[index].val = value;
            }
        } else if (value != null){
            // 新增
            vals[size++] = new Node(key, value);
        }
    }

    @Override
    public v get(k key) {
        int index = seek(key);
        if (index != -1)
            return vals[index].val;
        else
            return null;
    }

    private int seek(k key) {
        for (int i=0; i<size; i++) {
            if (vals[i].key.equals(key))
                return i;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<k> keys() {
        return () -> new Iterator<k>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index != size;
            }

            @Override
            public k next() {
                return vals[index++].key;
            }
        };
    }

    public static void main(String[] args) {
        String[] content = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk"};
        ArrayST<String, String> sss = new ArrayST<>(10);
        for (int i=0; i<content.length; i++) {
            sss.put(String.valueOf(i % 10), content[i]);
        }

        System.out.println("sss size = " + sss.size());
        System.out.println("==========");
        Iterator<String> it = sss.keys().iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("key = " + key);
            System.out.println("value = " + sss.get(key));
            System.out.println("-------");
        }

        for (int i=0; i<content.length; i++) {
            sss.delete(String.valueOf(i % 10));
            System.out.println("size = " + sss.size());
        }
        System.out.println("isEmpty = " + sss.isEmpty());
    }

    // 节点定义
    private class Node {
        k key;
        v val;

        public Node(k key, v val) {
            this.key = key;
            this.val = val;
        }
    }
}
