package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by hfc on 2020/3/7.
 */
public interface Sort<T extends Comparable<T>> {

    void sort(T[] a);

    // 比较大小，v<w时返回true
    default boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    // 交换数据
    default void exchange(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    default void show(T[] a) {
        // 在当行中打印数组
        for (T t : a)
            System.out.print(t + " ");
        System.out.println();
    }

    default boolean isSorted(T[] a) {
        // 测试数组元素是否有序
        for (int i=1; i<a.length; i++) {
            if (less(a[i], a[i-1]))
                return false;
        }
        return true;
    }
}
