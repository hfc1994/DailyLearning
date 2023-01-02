package Util.Guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Created by hfc on 2023/1/2.
 *
 * Cache 通过设置 concurrencyLevel 来使缓存支持并发的写入
 * Cache 采用类似 ConcurrentHashMap 的方式，通过把 Hash 表分割成多个
 *   segment，然后分区加锁（分离锁），以此来减小锁的细粒度，提高并发性能。
 * Cache 有 4 种缓存回收的场景
 * 1、基于容量的回收：缓存项的数目达到限定值之后，采用 LRU + FIFO 的回收方式
 * 2、定时回收：
 *  - expireAfterAccess：给定时间内没有被读写操作访问则回收
 *  - expireAfterWrite：给定时间内没有被写访问（创建或覆盖）
 * 3、基于引用回收：通过使用弱引用的键、或弱引用的值、或软引用的值来回收
 * 4、主动调用 invalidate 和 invalidateAll
 */
public class CacheDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(10L)   // 采用 LRU + FIFO 的策略
                .expireAfterAccess(Duration.ofSeconds(5L))
                .recordStats()  // 收集统计数据
                .concurrencyLevel(3)    // 并发写数据的线程数
                .removalListener(ntf -> {
                    // 在新数据写入时，或者调用 invalidate 和 invalidateAll 时才会被触发
                    String msg = String.format(Locale.ROOT, "key %s removed by: %s",
                            ntf.getKey(), ntf.getCause());
                    System.out.println(msg);
                })
                .build();

        baseTest(cache);
        // parallelTest1(cache);
        // parallelTest2(cache);
        // valueLoadTest();
    }

    private static void baseTest(Cache<String, String> cache) throws InterruptedException, ExecutionException {
        for (int i =0; i<12; i++) {
            cache.put(String.valueOf(i), String.valueOf(i));
        }
        System.out.println(cache.size());   // 10

        System.out.println(cache.getIfPresent("A"));    // null
        System.out.println(cache.get("A", () -> "AAA"));    // AAA
        System.out.println(cache.getIfPresent("A"));    // AAA

        Map<String, String> oMap = cache.getAllPresent(Arrays.asList("1", "2", "3", "4", "5"));
        System.out.println(oMap);   // {3=3, 4=4, 5=5}

        System.out.println(cache.getIfPresent("4"));    // 4
        cache.invalidate("4");  // 过期的信息
        System.out.println(cache.getIfPresent("4"));    // null

        TimeUnit.SECONDS.sleep(6);
        System.out.println(cache.getIfPresent("4"));    // null
        System.out.println(cache.getIfPresent("5"));    // null

        System.out.println("---");
        System.out.println(cache.size());
        cache.put("11", "11");  // 打印大量过期的信息
        System.out.println("---");
        System.out.println(cache.getIfPresent("11"));   // 11

        cache.put("22", "22");
        cache.put("33", "33");
        cache.put("44", "44");

        cache.invalidateAll();  // 打印大量过期的信息
        System.out.println(cache.getIfPresent("33"));
        cache.put("55", "55");

        // totalLoadTime的单位是纳秒，1毫秒=1000微秒，1微秒=1000纳秒
        System.out.println(cache.stats());
    }

    /**
     * Cache.get(key, callable) 里面 callable 的调用是线程安全的，
     * 对同一个 key 的并发获取最终只会产生一次 callable 的调用，
     * 因为在最终取值的地方会使用一把可重入锁进行加锁。
     *
     */
    private static void parallelTest1(Cache<String, String> cache) throws InterruptedException {
        Thread[] ts = new Thread[6];
        for (int i=0; i<ts.length; i++) {
            if (i == ts.length - 1) {
                ts[i] = new Thread(() -> {
                    try {
                        String ret = cache.get("22", () -> {
                            TimeUnit.SECONDS.sleep(2);
                            System.out.println("produce 222");
                            return "222";
                        });
                        System.out.println(ret);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                ts[i] = new Thread(() -> {
                    try {
                        String ret = cache.get("11", () -> {
                            TimeUnit.SECONDS.sleep(2);
                            System.out.println("produce 111");
                            return "111";
                        });
                        System.out.println(ret);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        Arrays.stream(ts).forEach(Thread::start);
    }

    /**
     * concurrencyLevel 的设置不影响读取操作
     */
    private static void parallelTest2(Cache<String, String> cache) {
        int count = 5;

        Thread[] ts = new Thread[count];
        for (int i=0; i<count; i++) {
            int tmp = i;
            ts[i] = new Thread(() -> {
                try {
                    String ret = cache.get(String.valueOf(tmp), () -> {
                        System.out.println("begin: " + tmp);
                        TimeUnit.SECONDS.sleep(2);
                        return "result: " + tmp;
                    });
                    System.out.println(ret);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }

        Stream.of(ts).forEach(Thread::start);
    }

    private static void valueLoadTest() throws ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("trigger load");
                        return "result: " + key;
                    }
                });

        System.out.println(cache.getIfPresent("11"));   // null
        System.out.println(cache.getIfPresent("11"));   // null
        System.out.println(cache.getIfPresent("22"));   // null
        System.out.println(cache.get("11"));    // trigger load result: 11
        System.out.println(cache.get("22"));    // trigger load result: 22
        System.out.println(cache.get("22", () -> "new22")); // result: 22
        System.out.println(cache.get("33", () -> "new33")); // new33
    }

}
