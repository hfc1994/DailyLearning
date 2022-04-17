package Algorithm.Leetcode.pkg600;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hfc on 2022/3/31.
 *
 * 728. 自除数
 *
 * 自除数 是指可以被它包含的每一位数整除的数。
 * 例如，128 是一个 自除数 ，因为 128 % 1 == 0，128 % 2 == 0，128 % 8 == 0。
 * 自除数 不允许包含 0 。
 * 给定两个整数 left 和 right ，返回一个列表，列表的元素是范围 [left, right] 
 * 内所有的 自除数 。
 *
 * 示例 1：
 * 输入：left = 1, right = 22
 * 输出：[1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
 *
 * 示例 2:
 * 输入：left = 47, right = 85
 * 输出：[48,55,66,77]
 *  
 * 提示：
 * 1 <= left <= right <= 10^4
 *
 */
public class Problem728 {

    /**
     * 速度 68%
     * 内存 34%
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (i <= 9) {
                result.add(i);
                continue;
            }

            boolean isOk = true;
            int num = i;
            do {
                int div = num % 10;
                if (div == 0 || i % div != 0) {
                    isOk = false;
                    break;
                }

                num /= 10;
            } while (num != 0);

            if (isOk) result.add(i);
        }
        return result;
    }

    public static void main(String[] args) {
        Problem728 p = new Problem728();

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22),
                p.selfDividingNumbers(1, 22)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(48,55,66,77),
                p.selfDividingNumbers(47, 85)));

        System.out.println(p.selfDividingNumbers(1, 10000));
        System.out.println(p.selfDividingNumbers(1, 10000).size());
    }
}
