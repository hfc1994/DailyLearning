package Algorithm.Leetcode.pkg200;

/**
 * Created by user-hfc on 2021/8/30.
 *
 * 278. 第一个错误的版本
 *
 * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。
 * 由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
 * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
 * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。
 * 实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
 *
 *
 * 示例 1：
 * 输入：n = 5, bad = 4
 * 输出：4
 * 解释：
 * 调用 isBadVersion(3) -> false
 * 调用 isBadVersion(5) -> true
 * 调用 isBadVersion(4) -> true
 * 所以，4 是第一个错误的版本。
 *
 * 示例 2：
 * 输入：n = 1, bad = 1
 * 输出：1
 *
 * 提示：
 * 1 <= bad <= n <= 2^31 - 1
 *
 */
public class Problem278 {

    /* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */
    boolean isBadVersion(int version) {
//        if (version <= 3) return false;
        if (version <= 1702766719) return false;
        else return true;
    }

    public int firstBadVersion_1(int n) {
        int begin = 1, end = n, mid;
        while (begin <= end) {
            mid = begin + (end - begin) / 2; // 相比 (begin + end) / 2 可以避免类型溢出
            if (isBadVersion((mid))) {
                if (!isBadVersion(mid - 1)) {
                    return mid;
                } else {
                    end = mid - 1;
                }
            } else {
                if (isBadVersion(mid + 1)) {
                    return mid + 1;
                } else {
                    begin = mid + 1;
                }
            }
        }
        return begin;
    }

    /**
     * 实际上就是找临界点，没必要如方法 1 那样做无意义的左右比较
     */
    public int firstBadVersion(int n) {
        int begin = 1, end = n, mid;
        while (begin <= end) {
            mid = begin + (end - begin) / 2;
            if (isBadVersion((mid))) {
                end = mid - 1;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

    public static void main(String[] args) {
        Problem278 p = new Problem278();

//        System.out.println(p.firstBadVersion(5));
        System.out.println(p.firstBadVersion(2126753390));
    }

}
