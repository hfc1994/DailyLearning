import java.util.Random;

/**
 * Created by hfc on 2022/1/18.
 *
 * 6 种常用排序
 */
public class SortExample {

    private void exchange(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private int partition(int[] data, int lo, int hi) {
        int i = lo, j = hi + 1;
        int v = data[i];

        while (true) {
            while (data[++i] < v) if (i >= hi) break;
            while (data[--j] > v) if (j <= lo) break;

            if (i >= j) break;
            exchange(data, i, j);
        }

        exchange(data, lo, j);
        return j;
    }

    /**
     * 快速排序
     */
    public void quickSort(int[] data, int lo, int hi) {
        if (lo >= hi) return;

        int mid = partition(data, lo, hi);
        quickSort(data, lo, mid - 1);
        quickSort(data, mid + 1, hi);
    }

    /**
     * 冒泡排序
     */
    public void bubbleSort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j] > data[j+1]) {
                    exchange(data, j, j+1);
                }
            }
        }
    }

    /**
     * 选择排序
     */
    public void selectSort(int[] data) {
        int minIdx;
        for (int i = 0; i < data.length - 1; i++) {
            minIdx = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                exchange(data, i, minIdx);
            }
        }
    }

    /**
     * 插入排序
     */
    public void insertSort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = i + 1; j >= 1; j--) {
                if (data[j - 1] > data[j]) {
                    exchange(data, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 归并排序
     */
    public void mergeSort(int[] data, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        if (mid != lo) {
            mergeSort(data, lo, mid);
            mergeSort(data, mid + 1, hi);
        }

        for (int i = mid + 1; i <= hi; i++) {
            for (int j = i - 1; j >= lo; j--) {
                if (data[j] > data[j+1]) {
                    exchange(data, j, j+1);
                }
            }
        }
    }

    private void sink(int[] array, int pos, int limit) {
        int idx = 2 * pos;
        if (idx >= limit) return;

        if (idx + 1 < limit) {
            if (array[idx + 1] > array[idx]) {
                idx = idx + 1;
            }
        }

        if (array[idx] > array[pos]) {
            this.exchange(array, pos, idx);
            // 最大的已经被放到最后的，后续排序不动它
            this.sink(array, idx, limit - 1);
        }
    }

    /**
     * 堆排序
     */
    public void heapSort(int[] data) {
        int[] array = new int[data.length + 1];
        System.arraycopy(data, 0, array, 1, data.length);

        int mid = array.length / 2;
        for (int i = mid; i > 0; i--) {
            this.sink(array, i, array.length);
        }

        for (int i = array.length - 1; i >= 1; i--) {
            this.exchange(array, 1, i);
            this.sink(array, 1, i);
        }
        System.arraycopy(array, 1, data, 0, data.length);
    }

    public void doSort(int[] data) {
//        this.quickSort(data, 0, data.length - 1);
//        this.bubbleSort(data);
//        this.selectSort(data);
//        this.insertSort(data);
//        this.mergeSort(data, 0, data.length - 1);
        this.heapSort(data);
    }

    public static void main(String[] args) {
        SortExample sort = new SortExample();
        int[] arr = new int[] {3, 1, 2, 5, 4};
        sort.doSort(arr);
        for (int i : arr) System.out.print(i + " ");

        System.out.println();
        arr = new int[20];
        Random random = new Random();
        for (int i=0; i<20; i++) {
            arr[i] = random.nextInt(100);
        }
        sort.doSort(arr);
        for (int i : arr) System.out.print(i + " ");
    }

}
