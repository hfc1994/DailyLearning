package Algorithm.Leetcode.pkg0;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2021/9/12.
 *
 * 46. 全排列
 *
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 示例 2：
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 *
 * 示例 3：
 * 输入：nums = [1]
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 *
 */
public class Problem46 {

    // backtrack 回溯
    public List<List<Integer>> permute(int[] nums) {
        return backTrack(nums, 0);
    }

    private List<List<Integer>> backTrack(int[] nums, int length) {
        length++;
        int val;
        List<List<Integer>> retList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == -128) continue;

            if (length == nums.length) {
                List<Integer> list = new ArrayList<>(length);
                list.add(nums[i]);
                retList.add(list);
            } else {
                val = nums[i];
                nums[i] = -128;
                List<List<Integer>> tmpList = backTrack(nums, length);
                nums[i] = val;
                for (List<Integer> list : tmpList) {
                    list.add(val);
                    retList.add(list);
                }
            }
        }
        return retList;
    }

    public static void main(String[] args) {
        Problem46 p = new Problem46();

        int[] nums = new int[] { 1,2,3 };
        LeetcodeUtil.printList(p.permute(nums));    // [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

        nums = new int[] { 0,1 };
        LeetcodeUtil.printList(p.permute(nums));    // [[0,1],[1,0]]

        nums = new int[] { 1 };
        LeetcodeUtil.printList(p.permute(nums));    // [[1]]
    }

}
