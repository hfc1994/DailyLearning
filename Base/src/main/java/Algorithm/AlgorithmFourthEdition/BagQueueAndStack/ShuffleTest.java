package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by user-hfc on 2019/12/19.
 *
 * 我称shuffle之后，在数组下标index位置的值为index的情况为碰撞。
 * 设数组长度为n
 * 方案一会依次把数组index位置的内容与0~n-1互调换内容，因此就会存在调换前后的位置不变
 * 方案二会依次把数组index位置的内容与index~n-1互调换内容，因此也存在调换前后的位置不变的情况
 *
 * 而方案一相对于方案二还存在一种特殊情况
 * 比如把index=0的内容调换到index=9位置，当轮到index=9时，又把该内容调换或index=0了
 * 依次方案一的碰撞几率比方案二略高
 */
public class ShuffleTest {

    public static void shuffleType1(int[] array) {
        int len = array.length;
        int target;
        for (int i=0; i<len; i++) {
            // 唯一的区别
            target = (int) (Math.random() * len);
            int tmp = array[i];
            array[i] = array[target];
            array[target] = tmp;
        }
    }

    public static void shuffleType2(int[] array) {
        int len = array.length;
        int target;
        for (int i=0; i<len; i++) {
            // 唯一的区别
            target = (int) (Math.random() * ( len - i ));
            int tmp = array[i];
            array[i] = array[target];
            array[target] = tmp;
        }
    }

    private static void initArray(int[] arr) {
        for (int i=0; i<arr.length; i++) {
            arr[i] = i;
        }
    }

    private static int calcHit(int[] arr) {
        int hitCount = 0;
        for (int i=0; i<arr.length; i++) {
            if (arr[i] == i)
                hitCount++;
        }
        return hitCount;
    }

    private static double hitPercent(int[] array, int type) {
        int cycle = 5000;
        int totalCount = 0, hitCount = 0;
        for (int i=0; i<cycle; i++) {
            initArray(array);

            if (type == 1)
                shuffleType1(array);
            else
                shuffleType2(array);

            hitCount += calcHit(array);
            totalCount += array.length;
        }
        return hitCount * 1.0d / totalCount * 100;
    }

    public static void main(String[] args) {

        int tinyLen = 10, shortLen = 20, middleLen = 50, longLen = 100;
        int[] array;
        double percent;

        array = new int[tinyLen];
        percent = hitPercent(array, 1);
        System.out.println("shuffleType1 in 10 hit is " + percent + "%");

        array = new int[tinyLen];
        percent = hitPercent(array, 2);
        System.out.println("shuffleType2 in 10 hit is " + percent + "%");

        array = new int[shortLen];
        percent = hitPercent(array, 1);
        System.out.println("shuffleType1 in 20 hit is " + percent + "%");

        array = new int[shortLen];
        percent = hitPercent(array, 2);
        System.out.println("shuffleType2 in 20 hit is " + percent + "%");

        array = new int[middleLen];
        percent = hitPercent(array, 1);
        System.out.println("shuffleType1 in 50 hit is " + percent + "%");

        array = new int[middleLen];
        percent = hitPercent(array, 2);
        System.out.println("shuffleType2 in 50 hit is " + percent + "%");

        array = new int[longLen];
        percent = hitPercent(array, 1);
        System.out.println("shuffleType1 in 100 hit is " + percent + "%");

        array = new int[longLen];
        percent = hitPercent(array, 2);
        System.out.println("shuffleType2 in 100 hit is " + percent + "%");
    }
}
