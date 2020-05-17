package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/5/17.
 *
 * 3.2.13 为二叉查找树实现非递归的put()和get()方法。
 *
 * put()的实现更复杂一些，因为它需要保存一个指向底层结点的链接，以便使之
 * 成为新结点的父结点。你还需要额外遍历一遍查找路径来更新所有结点计算器以
 * 保证结点插入的正确性。因为在性能优先的实现中查找的次数比插入多得多，有
 * 必要使用这段get()代码，而相应的put()实现则无关紧要。
 */
public class BSTNoRecursive<k extends Comparable<k>, v>
        extends BSTWithHeight<k, v>  {

    @Override
    public v get(k key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x.val;
        }
        return null;
    }

    @Override
    public void put(k key, v val) {
        Node x = root;

        if (x == null) {
            root = new Node(key, val, 1, 1);
            return;
        }

        Node prev;
        boolean left = true;
        do {
            prev = x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
                left = true;
            } else if (cmp > 0) {
                x = x.right;
                left = false;
            } else {
                // 更新结点
                x.val = val;
                return;
            }
        } while (x != null);

        x = new Node(key, val, 1, 1);
        if (left)
            prev.left = x;
        else
            prev.right = x;

        x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.N++;
                x = x.left;
            } else if (cmp > 0) {
                x.N++;
                x = x.right;
            } else {
                x = null;
            }
        }
    }

    /**
     * 3.2.14 实现非递归的min()，max()，floor()，ceiling()，rank()和select()方法。
     * @return
     */
    @Override
    public k min() {
        Node x = root;

        while (x != null) {
            if (x.left == null)
                return x.key;
            x = x.left;
        }

        return null;
    }

    @Override
    public k max() {
        Node x = root;

        while (x != null) {
            if (x.right == null)
                return x.key;
            x = x.right;
        }
        return null;
    }

    @Override
    public k floor(k key) {
        Node x = root;

        Node prev = null;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x.key;
            } else if (cmp < 0) {
                if (x.left != null) {
                    x = x.left;
                    continue;
                } else if (prev != null){
                    return prev.key;
                } else
                    return null;
            }

            if (x.right == null)
                return x.key;

            prev = x;
            x = x.right;
        }
        return null;
    }

    @Override
    public k ceiling(k key) {
        Node x = root;

        Node prev = null;
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp == 0)
                return x.key;
            else if (cmp > 0) {
                if (x.right != null) {
                    x = x.right;
                    continue;
                } else if (prev != null)
                    return prev.key;
                else
                    return null;
            }

            if (x.left == null)
                return x.key;

            prev = x;
            x = x.left;
        }

        return null;
    }

    @Override
    public int rank(k key) {
        Node x = root;

        int prevCount = 0;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x.left == null ? prevCount : prevCount + x.left.N;
            } else if (cmp < 0) {
                x = x.left;
            } else {
                if (x.left != null)
                    prevCount = prevCount + x.left.N + 1;   // 加1是x本身
                else
                    prevCount += 1;
                x = x.right;
            }
        }

        return prevCount;
    }

    @Override
    public k select(int index) {
        Node x = root;

        int prevCount = 0;
        while (x != null) {
            int leftN = x.left == null ? 1 : x.left.N + 1;  // 包含x本身
            if (leftN + prevCount == index)
                return x.key;
            else if (leftN + prevCount < index) {
                prevCount += leftN;
                x = x.right;
            } else {
                x = x.left;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String[] content = new String[]{"e","a","s","y","q","u","e","s","t","i","o","n"};

        BSTNoRecursive<String, String> bstnr = new BSTNoRecursive<>();
        for (int i=0; i<content.length; i++) {
            bstnr.put(content[i], String.valueOf(i));
        }

        System.out.println("height = " + bstnr.height());

//        bstnr.put("z", String.valueOf(content.length + 1));
//        System.out.println("except 6, actual " + bstnr.height());

        bstnr.put("p", String.valueOf(content.length + 2));
        System.out.println("except 6, actual " + bstnr.height());

        bstnr.put("m", String.valueOf(content.length + 3));
        System.out.println("except 7, actual " + bstnr.height());

        System.out.println("min is " + bstnr.min());
        System.out.println("max is " + bstnr.max());

        // floor()
        System.out.println("floor x = " + bstnr.floor("x"));
        System.out.println("floor b = " + bstnr.floor("b"));
        System.out.println("floor j = " + bstnr.floor("j"));
        System.out.println("floor o = " + bstnr.floor("o"));

        // ceiling
        System.out.println("ceiling x = " + bstnr.ceiling("x"));
        System.out.println("ceiling b = " + bstnr.ceiling("b"));
        System.out.println("ceiling j = " + bstnr.ceiling("j"));
        System.out.println("ceiling o = " + bstnr.ceiling("o"));
        System.out.println("ceiling z = " + bstnr.ceiling("z"));

        // rank
        System.out.println("rank a = " + bstnr.rank("a"));
        System.out.println("rank b = " + bstnr.rank("b"));
        System.out.println("rank j = " + bstnr.rank("j"));
        System.out.println("rank q = " + bstnr.rank("q"));
        System.out.println("rank o = " + bstnr.rank("o"));
        System.out.println("rank z = " + bstnr.rank("z"));

        // select
        System.out.println("select 3 = " + bstnr.select(3));
        System.out.println("select 1 = " + bstnr.select(1));
        System.out.println("select 7 = " + bstnr.select(7));
        System.out.println("select 12 = " + bstnr.select(12));
        System.out.println("select 122 = " + bstnr.select(122));
    }
}
