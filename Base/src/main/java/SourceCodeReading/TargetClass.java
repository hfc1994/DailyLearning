package SourceCodeReading;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.ref.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by hfc on 2019/4/1.
 */
public class TargetClass {

    private ReentrantLock reentrantLock;    // 大致看了一遍
    private ReentrantReadWriteLock reentrantReadWriteLock;  // 大致看了一遍
    private LockSupport lockSupport;    // 大致看了一遍
    private Semaphore semaphore;    // 简单看了下注释，其它信息见SemaphoreDemo
    private CountDownLatch countDownLatch;  // 简单看了一下，和Semaphore的思想类似
    private CyclicBarrier cyclicBarrier;    // 大致看了一遍
    private Exchanger exchanger;    // 简单的使用了下，实现未细看
    private TimeUnit timeUnit;  // 看过一遍
    private Thread thread;  // 简单过了遍公用方法
    private ThreadLocal threadLocal;    // 简单看了使用方法和源码实现

    //-------
    private Executor executor;  // 接口
    private Executors executors;    // 类似工具类
    private ExecutorService executorService;    // 接口
    private ScheduledExecutorService scheduledExecutorService;  // 接口
    private ThreadPoolExecutor threadPoolExecutor;  // 大致看了一遍实现逻辑
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;    // 大致看了一遍实现逻辑

    //-------
    private ForkJoinPool forkJoinPool;
    private Future future;
    private FutureTask futureTask;
    private CompletableFuture completableFuture;
    private ScheduledFuture scheduledFuture;

    //-------
    private Arrays arrays;  // 工具类，简单浏览了
    private Collections collections;    // 简单看了部分方法
    //-------
    private Queue queue;    // 接口
    private Deque deque;    // 接口，双端队列
    private BlockingQueue blockingQueue;    // 接口
    private ArrayBlockingQueue arrayBlockingQueue;  // 看了实现逻辑，Itr、Itrs和Spliterator没有看
    private LinkedBlockingQueue linkedBlockingQueue;    // 看了实现逻辑，Itr和Spliterator没有看
    private PriorityQueue priorityQueue;    // 非线程安全，逻辑类似ScheduledThreadPoolExecutor -> DelayedWorkQueue
    private PriorityBlockingQueue priorityBlockingQueue;    // 线程安全，逻辑类似PriorityQueue
    //-------
    private Map map;    // 简单看了下
    private HashMap hashMap;    // 红黑树相关的还没细看
    private Hashtable hashtable;    // 简单看了下，没有理由会被用到的类
    private LinkedHashMap linkedHashMap;    // 简单看了下，继承自HashMap
    private ConcurrentHashMap concurrentHashMap;
    private WeakHashMap weakHashMap;    // 简单看了下实现
    private TreeMap treeMap;    // 基于红黑树算法的map
    private SortedMap sortedMap;    // 加锁版的TreeMap
    //-------
    private List list;  // 接口
    private ArrayList arrayList;    // 简单看了下实现
    private LinkedList linkedList;  // 简单看了下实现
    //-------
    private Set set;    // 接口
    private HashSet hashSet;    // 基于HashMap的key实现，value用一个Object
    private LinkedHashSet linkedHashSet;    // 继承自HashSet，但是基于LinkedHashMap，详见其构造函数
    // ------
    private String string;  // 简单看了下方法，.trim()是把字符串前后的不可见字符（ASCII<=32）全部给去除
    private Integer integer;    // 简单看了下方法

    // ------
    private Reference reference;    // 引用，简单看了下
    private SoftReference softReference;    // 软引用，简单看了下
    private WeakReference weakReference;    // 弱引用，简单看了下
    private PhantomReference phantomReference;  // 虚引用，简单看了下
    private ReferenceQueue referenceQueue;  // 引用队列，简单看了下

    public static void main(String[] args) {
        People p = new People();
        p.setName("aaa");
        p.setAge(19);
        p.setContent("jjljljljljljljl");

        JSONObject obj = (JSONObject) JSON.toJSON(p);
        System.out.println(obj.toJSONString());
    }

    static class People {
        private String name;
        private int age;
        private transient String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
