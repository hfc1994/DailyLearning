package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/5/16.
 *
 * 3.2.6 为二叉查找树添加一个方法height()来计算树的高度。实现两种方案：
 * 一种使用递归（用时为线性级别，所需空间和树高度成正比），一种模仿size()
 * 在每个结点中添加一个变量（所需空间为线性级别，查询耗时为常数）
 *
 * 方案一：使用递归
 */
public class BSTWithHeight<k extends Comparable<k>, v>
        extends BST<k, v> {

    @Override
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null)
            return 0;

        int leftHeight = height(x.left) + 1;
        int rightHeight = height(x.right) + 1;

        return leftHeight >= rightHeight ? leftHeight : rightHeight;
    }

    public static void main(String[] args) {
        String[] content = new String[]{"e","a","s","y","q","u","e","s","t","i","o","n"};

        BSTWithHeight<String, String> bstw = new BSTWithHeight<>();
        for (int i=0; i<content.length; i++) {
            bstw.put(content[i], String.valueOf(i));
        }

        System.out.println("height = " + bstw.height());
    }

}
