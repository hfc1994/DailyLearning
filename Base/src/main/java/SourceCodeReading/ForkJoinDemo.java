package SourceCodeReading;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hfc on 2019/9/15.
 *
 * ForkJoinPool可以认为是对ThreadPoolExecutor的一种补充
 * ForkJoinPool在有巨量小任务的情况下会有一些优势，可以以较少线程数处理巨量小人物
 * 在使用了偷窃算法的情况下，不会出现某些线程很忙，某些线程空闲的情况
 *
 * 每个ForkJoinWorkerThread中维护有一个WorkQueue
 * WorkQueue中有一个top和base引用指向队列的头部和尾部
 * 当ForkJoinWorkerThread到自己的队列中取任务时，采用LIFO方式获取任务（从top从获取）
 * 当ForkJoinWorkerThread的任务队列空闲时会采用偷窃算法去其它任务队列获取任务，此时使用FIFO方式获取任务（从base处获取任务）
 */
public class ForkJoinDemo{

    public static void main(String[] args) {
        int length = 100_000;
        List<Long> srcList = new ArrayList<>(length);
        for(int i=0; i<length; i++) {
            long num = (long) (Math.random() * length);
            srcList.add(num);
        }

        doTransfer(srcList);
        System.out.println("---------");
        doCalculate(srcList);
    }

    private static void doTransfer(List<Long> srcList) {
        Queue<Long> dstList = new ArrayBlockingQueue<>(srcList.size());
        System.out.println("任务开始前，来源链表长度： " + srcList.size());
        long beginTime = System.currentTimeMillis();
        RecursiveAction action = new RecursiveActionDemo(0, srcList.size() - 1, srcList, dstList);

        // 使用自定义的ForkJoin线程池
//        ForkJoinPool pool = new ForkJoinPool();
//        pool.submit(action).join();

        // 使用默认的ForkJoinPool.common线程池
        // .fork()把actiontianjia到common的线程池任务队列中
        action.fork();
        // 等待任务完成
        action.join();
        long endTime = System.currentTimeMillis();
        System.out.println("任务结束后，目标队列长度： " + dstList.size());
        System.out.println("耗时：" + (endTime - beginTime) + "ms");
        System.out.println("总计启动任务数：" + RecursiveActionDemo.atomic.intValue());
    }

    private static void doCalculate(List<Long> srcList) {
        long total = srcList.stream().reduce(0L, (a, b) -> a + b);
        System.out.println("任务开始前，来源链表的总和为： " + total);
        long beginTime = System.currentTimeMillis();
        RecursiveTask<Long> task = new RecursizeTaskDemo(0, srcList.size() - 1, srcList);
        task.fork();
        long total_Task = task.join();
        long endTime = System.currentTimeMillis();
        System.out.println("任务结束后得出的总和为： " + total_Task);
        System.out.println("耗时：" + (endTime - beginTime) + "ms");
        System.out.println("总计启动任务数：" + RecursizeTaskDemo.atomic.intValue());
    }

    /**
     * 把长度为n的list的数据转移到另外一个队列
     */
    static class RecursiveActionDemo extends RecursiveAction {

        static AtomicInteger atomic = new AtomicInteger(0);

        private int begin;
        private int end;
        private List<Long> src;  // 数据来源链表
        private Queue<Long> dst;  // 数据目标队列

        public RecursiveActionDemo(int begin, int end, List<Long> src, Queue<Long> dst) {
            this.begin = begin;
            this.end = end;
            this.src = src;
            this.dst = dst;
            atomic.incrementAndGet();
        }

        @Override
        protected void compute() {
            if (this.end - this.begin <= 5) {
                for (int i=this.begin; i<=this.end; i++) {
                    this.dst.add(this.src.get(i));
                }
            } else {
                int mid = (int) Math.floor(((this.begin + this.end) / 2.0));
                RecursiveAction action1 = new RecursiveActionDemo(this.begin, mid, this.src, this.dst);
                RecursiveAction action2 = new RecursiveActionDemo(mid + 1, this.end, this.src, this.dst);
                action1.fork();
                action2.fork();
                // 非偷窃算法时，任务是LIFO
                action2.join();
                action1.join();
            }
        }
    }

    /**
     * 对长度为n的队列的数据进行求和
     */
    static class RecursizeTaskDemo extends RecursiveTask<Long> {

        static AtomicInteger atomic = new AtomicInteger(0);

        private int begin;
        private int end;
        private List<Long> src;  // 数据来源链表

        public RecursizeTaskDemo(int begin, int end, List<Long> src) {
            this.begin = begin;
            this.end = end;
            this.src = src;
            atomic.incrementAndGet();
        }

        @Override
        protected Long compute() {
            if (this.end - this.begin <= 5) {
                long ret = 0;
                for (int i=this.begin; i<=this.end; i++) {
                    ret += this.src.get(i);
                }
                return ret;
            } else {
                int mid = (int) Math.floor(((this.begin + this.end) / 2.0));
                RecursiveTask<Long> action1 = new RecursizeTaskDemo(this.begin, mid, this.src);
                RecursiveTask<Long> action2 = new RecursizeTaskDemo(mid + 1, this.end, this.src);
                action1.fork();
                action2.fork();
                // 非偷窃算法时，任务是LIFO
                return action2.join() + action1.join();
            }
        }
    }
}
