package Algorithm.Leetcode.pkg2000;

import java.util.*;

/**
 * Created by hfc on 2022/1/23.
 *
 * 2034. 股票价格波动
 *
 * 给你一支股票价格的数据流。数据流中每一条记录包含一个 时间戳 和该时间点股票对应的 价格 。
 * 不巧的是，由于股票市场内在的波动性，股票价格记录可能不是按时间顺序到来的。某些情况下，
 * 有的记录可能是错的。如果两个有相同时间戳的记录出现在数据流中，前一条记录视为错误记录，
 * 后出现的记录 更正 前一条错误的记录。
 *
 * 请你设计一个算法，实现：
 * - 更新 股票在某一时间戳的股票价格，如果有之前同一时间戳的价格，这一操作将 更正 之前的错误价格。
 * - 找到当前记录里 最新股票价格 。最新股票价格 定义为时间戳最晚的股票价格。
 * - 找到当前记录里股票的 最高价格 。
 * - 找到当前记录里股票的 最低价格 。
 *
 * 请你实现 StockPrice 类：
 * - StockPrice() 初始化对象，当前无股票价格记录。
 * - void update(int timestamp, int price) 在时间点 timestamp 更新股票价格为 price 。
 * - int current() 返回股票 最新价格 。
 * - int maximum() 返回股票 最高价格 。
 * - int minimum() 返回股票 最低价格 。
 *
 * 示例 1：
 * 输入：
 * ["StockPrice", "update", "update", "current", "maximum", "update", "maximum", "update", "minimum"]
 * [[], [1, 10], [2, 5], [], [], [1, 3], [], [4, 2], []]
 * 输出：
 * [null, null, null, 5, 10, null, 5, null, 2]
 * 解释：
 * StockPrice stockPrice = new StockPrice();
 * stockPrice.update(1, 10); // 时间戳为 [1] ，对应的股票价格为 [10] 。
 * stockPrice.update(2, 5);  // 时间戳为 [1,2] ，对应的股票价格为 [10,5] 。
 * stockPrice.current();     // 返回 5 ，最新时间戳为 2 ，对应价格为 5 。
 * stockPrice.maximum();     // 返回 10 ，最高价格的时间戳为 1 ，价格为 10 。
 * stockPrice.update(1, 3);  // 之前时间戳为 1 的价格错误，价格更新为 3 。
 *                           // 时间戳为 [1,2] ，对应股票价格为 [3,5] 。
 * stockPrice.maximum();     // 返回 5 ，更正后最高价格为 5 。
 * stockPrice.update(4, 2);  // 时间戳为 [1,2,4] ，对应价格为 [3,5,2] 。
 * stockPrice.minimum();     // 返回 2 ，最低价格时间戳为 4 ，价格为 2 。
 *  
 * 提示：
 * 1 <= timestamp, price <= 10^9
 * update，current，maximum 和 minimum 总 调用次数不超过 10^5 。
 * current，maximum 和 minimum 被调用时，update 操作 至少 已经被调用过 一次 。
 *
 */
public class Problem2034 {

    /**
     * 数据量大的时候时间超限
     */
    class StockPrice1 {

        private int boardLen = 200;
        private Stock[] board;

        private int current;
        private int curPrice;
        private int maxPrice;
        private int miniPrice;

        public StockPrice1() {
            this.board = new Stock[this.boardLen];

            this.current = 0;
            this.maxPrice = Integer.MIN_VALUE;
            this.miniPrice = Integer.MAX_VALUE;
        }

        public void update(int timestamp, int price) {
            int offset = timestamp % boardLen;
            Stock stock = this.board[offset];

            boolean modified = false;
            Stock newOne = new Stock(timestamp, price);
            if (stock == null) {
                this.board[offset] = newOne;
            } else {
                Stock prev = null;
                do {
                    if (stock.timestamp == timestamp) {
                        if ((stock.price == maxPrice && price < maxPrice)
                                || (stock.price == miniPrice && price > miniPrice)) {
                            modified = true;
                        }
                        stock.price = price;
                        break;
                    }
                    prev = stock;
                } while ((stock = stock.next) != null);

                if (stock == null) {
                    prev.next = newOne;
                }
            }

            if (timestamp > current) current = timestamp;
            if (timestamp >= current) curPrice = price;

            if (price >= maxPrice) {
                maxPrice = price;
            }

            if (price <= miniPrice) {
                miniPrice = price;
            }

            if (modified) {
                this.findMaxAndMini();
            }
        }

        public int current() {
            return curPrice;
        }

        public int maximum() {
            return maxPrice;
        }

        public int minimum() {
            return miniPrice;
        }

        private void findMaxAndMini() {
            int max = Integer.MIN_VALUE;
            int mini = Integer.MAX_VALUE;
            for (int i = 0; i < boardLen; i++) {
                Stock cur = board[i];
                while (cur != null) {
                    if (cur.price > max) max = cur.price;
                    if (cur.price < mini) mini = cur.price;
                    cur = cur.next;
                }
            }

            this.maxPrice = max == Integer.MIN_VALUE ? 0 : max;
            this.miniPrice = mini == Integer.MAX_VALUE ? 0 : mini;
        }
    }

    class  Stock {
        int timestamp;
        int price;
        Stock next;

        public Stock(int t, int p) {
            this.timestamp = t;
            this.price = p;
        }
    }

    class StockPrice {

        private int current;
        private Map<Integer, Integer> time2Price;
        private TreeMap<Integer, Integer> priceCount;

        public StockPrice() {
            time2Price = new HashMap<>();
            priceCount = new TreeMap<>();
        }

        public void update(int timestamp, int price) {
            if (timestamp > current) {
                this.current = timestamp;
            }

            Integer oldVal = this.time2Price.put(timestamp, price);

            if (oldVal != null) {
                int oldCnt = this.priceCount.get(oldVal);
                if (oldCnt == 1) {
                    this.priceCount.remove(oldVal);
                } else {
                    this.priceCount.put(oldVal, oldCnt - 1);
                }
            }

            int cnt = this.priceCount.getOrDefault(price, 0);
            this.priceCount.put(price, cnt + 1);
        }

        public int current() {
            return this.time2Price.get(this.current);
        }

        public int maximum() {
            return this.priceCount.lastKey();
        }

        public int minimum() {
            return this.priceCount.firstKey();
        }
    }

    public static void main(String[] args) {
        Problem2034 p = new Problem2034();

        StockPrice sp = p.new StockPrice();
        sp.update(1, 10);
        sp.update(2, 5);
        System.out.println(sp.current() == 5);
        System.out.println(sp.maximum() == 10);
        sp.update(1, 3);
        System.out.println(sp.maximum() == 5);
        sp.update(4, 2);
        System.out.println(sp.minimum() == 2);

        System.out.println("----------");

        sp = p.new StockPrice();
        sp.update(38, 2308);
        System.out.println(sp.maximum() == 2308);
        System.out.println(sp.current() == 2308);
        System.out.println(sp.minimum() == 2308);

    }

}
