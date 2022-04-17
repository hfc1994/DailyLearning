package Algorithm.Leetcode.pkg800;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hfc on 2022/4/1.
 *
 * 954. 二倍数对数组
 *
 * 给定一个长度为偶数的整数数组 arr，只有对 arr 进行重组后可以满足 “对于每个 0 <= i < len(arr) / 2，
 * 都有 arr[2 * i + 1] = 2 * arr[2 * i]” 时，返回 true；否则，返回 false。
 *
 * 示例 1：
 * 输入：arr = [3,1,3,6]
 * 输出：false
 *
 * 示例 2：
 * 输入：arr = [2,1,2,6]
 * 输出：false
 *
 * 示例 3：
 * 输入：arr = [4,-2,2,-4]
 * 输出：true
 * 解释：可以用 [-2,-4] 和 [2,4] 这两组组成 [-2,-4,2,4] 或是 [2,4,-2,-4]
 *  
 * 提示：
 * 0 <= arr.length <= 3 * 10^4
 * arr.length 是偶数
 * -10^5 <= arr[i] <= 10^5
 *
 */
public class Problem954 {

    /**
     * 速度 67%
     * 内存 98%
     */
    public boolean canReorderDoubled(int[] arr) {
        Arrays.sort(arr);
        Map<Integer, Integer> map = new HashMap<>(arr.length / 2);
        for (int i : arr) {
            int count = map.getOrDefault(i, 0);
            count++;
            map.put(i, count);
        }

        int executeCnt = 0;
        for (int i : arr) {
            if (executeCnt == arr.length / 2) {
                break;
            } else if (map.get(i) == 0) {
                continue;
            }

            // 负数 * 2会变得更小，更小的反而在更左边
            // 所以负数从左往右是除以2，因此需要求余判断是否能整除
            if (i < 0 && i % 2 != 0)
                return false;

            map.put(i, map.get(i) - 1);
            int dValue = i < 0 ? (i / 2) : (i * 2);
            int count = map.getOrDefault(dValue, 0);
            if (count == 0) {
                return false;
            } else {
                map.put(dValue, count - 1);
                executeCnt++;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Problem954 p = new Problem954();

        System.out.println(!p.canReorderDoubled(new int[] {3, 1, 3, 6}));
        System.out.println(!p.canReorderDoubled(new int[] {2, 1, 2, 6}));
        System.out.println(p.canReorderDoubled(new int[] {4, -2, 2, -4}));
        System.out.println(p.canReorderDoubled(new int[] {4, -2, 2, -4, 0, 0}));
        System.out.println(!p.canReorderDoubled(new int[] {4, -2, 2, -4, 0, 1}));
        System.out.println(p.canReorderDoubled(new int[] {4, 2}));
        System.out.println(!p.canReorderDoubled(new int[] {4, -2}));
        System.out.println(!p.canReorderDoubled(new int[] {-5, -2}));
        System.out.println(p.canReorderDoubled(new int[] {}));
        System.out.println(p.canReorderDoubled(new int[] {-5, -10}));
        System.out.println(!p.canReorderDoubled(new int[] {-5, -2}));
        System.out.println(p.canReorderDoubled(new int[] {5, 10}));
        System.out.println(!p.canReorderDoubled(new int[] {5, 2}));
    }
}
