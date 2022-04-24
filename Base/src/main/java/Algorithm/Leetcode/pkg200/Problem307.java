package Algorithm.Leetcode.pkg200;

/**
 * Created by hfc on 2022/4/19.
 *
 * 307. 区域和检索 - 数组可修改
 *
 * 给你一个数组 nums ，请你完成两类查询。
 * - 其中一类查询要求 更新 数组 nums 下标对应的值
 * - 另一类查询要求返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）
 *   的nums元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 * - NumArray(int[] nums) 用整数数组 nums 初始化对象
 * - void update(int index, int val) 将 nums[index] 的值 更新 为 val
 * - int sumRange(int left, int right) 返回数组 nums 中索引 left 和索引
 *   right 之间（ 包含 ）的nums元素的 和 （即，nums[left] + nums[left +
 *   1],..., nums[right]）
 *  
 * 示例 1：
 * 输入：
 * ["NumArray", "sumRange", "update", "sumRange"]
 * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
 * 输出：
 * [null, 9, null, 8]
 * 解释：
 * NumArray numArray = new NumArray([1, 3, 5]);
 * numArray.sumRange(0, 2); // 返回 1 + 3 + 5 = 9
 * numArray.update(1, 2);   // nums = [1,2,5]
 * numArray.sumRange(0, 2); // 返回 1 + 2 + 5 = 8
 *  
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -100 <= nums[i] <= 100
 * 0 <= index < nums.length
 * -100 <= val <= 100
 * 0 <= left <= right < nums.length
 * 调用 update 和 sumRange 方法次数不大于 3 * 10^4 
 *
 */
public class Problem307 {

    class NumArray1 {

        int[] datas;
        int[] accVals;

        public NumArray1(int[] nums) {
            this.datas = nums;
            this.accVals = new int[nums.length];
            this.accVals[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                this.accVals[i] = nums[i] + this.accVals[i - 1];
            }
        }

        public void update(int index, int val) {
            int diff = val - this.datas[index];
            this.datas[index] = val;
            for (int i = index; i < accVals.length; i++) {
                this.accVals[i] += diff;
            }
        }

        public int sumRange(int left, int right) {
            if (left == 0) return this.accVals[right];
            else return this.accVals[right] - this.accVals[left - 1];
        }
    }

    /**
     * 题解给的灵感，分块处理
     * 速度 7%
     * 内存 68%
     */
    class NumArray2 {

        int[] datas;
        int[] accVals;
        int accSize;

        public NumArray2(int[] nums) {
            this.datas = nums;
            accSize = (int) Math.sqrt(nums.length);
            this.accVals = new int[nums.length / accSize + 1];

            for (int i = 0; i < nums.length; i++) {
                int idx = i / accSize;
                this.accVals[idx] += nums[i];
            }
        }

        public void update(int index, int val) {
            int diff = val - this.datas[index];
            this.datas[index] = val;
            this.accVals[index / this.accSize] += diff;
        }

        public int sumRange(int left, int right) {
            int fromIdx = left / this.accSize;
            int toIdx = right / this.accSize;

            int sum = 0;
            if (fromIdx == toIdx) {
                for (int i = left; i <= right; i++) {
                    sum += this.datas[i];
                }
            } else {
                for (int i = fromIdx + 1; i < toIdx; i++) {
                    sum += this.accVals[i];
                }

                for (int i = left; i < this.datas.length; i++) {
                    if (i / this.accSize == fromIdx) {
                        sum += this.datas[i];
                        continue;
                    }
                    break;
                }

                for (int i = right; i >= 0; i--) {
                    if (i / this.accSize == toIdx) {
                        sum += this.datas[i];
                        continue;
                    }
                    break;
                }
            }

            return sum;
        }
    }

    /**
     * 题解给的灵感，线段树：https://zhuanlan.zhihu.com/p/106118909
     * 速度 9%
     * 内存 30%
     */
    class NumArray {

        int[] datas;
        int size;
        int[] segmentTree;

        public NumArray(int[] nums) {
            this.datas = nums;
            this.size = nums.length;
            this.segmentTree = new int[nums.length * 4];

            for (int i = 0; i < nums.length; i++) {
                this.toSet(0, this.size - 1, 0, i);
            }
        }

        public void update(int idx, int val) {
            this.datas[idx] = val;
            this.toSet(0, this.size - 1, 0, idx);
        }

        public int sumRange(int left, int right) {
            return this.toSum(left, right, 0, this.size - 1, 0);
        }

        private int toSet(int from, int to, int stIdx, int idx) {
            if (from == to) {
                int diff = this.datas[idx] - this.segmentTree[stIdx];
                this.segmentTree[stIdx] = this.datas[idx];
                return diff;
            }

            int mid = (from + to) / 2;
            int diff;
            if (idx <= mid) {
                diff = this.toSet(from, mid, 2 * stIdx + 1, idx);
            } else {
                diff = this.toSet(mid + 1, to, 2 * stIdx + 2, idx);
            }
            this.segmentTree[stIdx] += diff;
            return diff;
        }

        private int toSum(int left, int right, int from, int to, int stIdx) {
            if (left == from && right == to) {
                return this.segmentTree[stIdx];
            }

            int sum = 0;
            int mid = (from + to) / 2;
            if (right <= mid) {
                sum = this.toSum(left, right, from, mid, 2 * stIdx + 1);
            } else if (left > mid) {
                sum = this.toSum(left, right, mid + 1, to, 2 * stIdx + 2);
            } else {
                sum += this.toSum(left, mid, from, mid, 2 * stIdx + 1);
                sum += this.toSum(mid + 1, right, mid + 1, to, 2 * stIdx + 2);
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        Problem307 p = new Problem307();

        NumArray numArray = p.new NumArray(new int[] {1, 3, 5});
        System.out.println(9 == numArray.sumRange(0, 2));
        numArray.update(1, 2);
        System.out.println(8 == numArray.sumRange(0,2));

        System.out.println("---");

        numArray = p.new NumArray(new int[] {0, 9, 5, 7, 3});
        System.out.println(3 == numArray.sumRange(4, 4));
        System.out.println(15 == numArray.sumRange(2, 4));
        System.out.println(7 == numArray.sumRange(3, 3));
        numArray.update(4, 5);
        numArray.update(1, 7);
        numArray.update(0, 8);
        System.out.println(12 == numArray.sumRange(1, 2));
        numArray.update(1, 9);
        System.out.println(5 == numArray.sumRange(4, 4));
        numArray.update(3, 4);

        System.out.println("---");

        numArray = p.new NumArray(new int[] {1, 3, 5, 7, 9, 11});
        System.out.println(9 == numArray.sumRange(0, 2));
        numArray.update(1, 2);
        System.out.println(8 == numArray.sumRange(0, 2));
        numArray.update(4, 5);
        System.out.println(23 == numArray.sumRange(3, 5));
        numArray.update(2, 4);
        System.out.println(18 == numArray.sumRange(1, 4));
        numArray.update(3, 7);
        numArray.update(5, 7);
        System.out.println(26 == numArray.sumRange(0, 5));
        numArray.update(2, 6);
        System.out.println(16 == numArray.sumRange(0, 3));
    }

}
