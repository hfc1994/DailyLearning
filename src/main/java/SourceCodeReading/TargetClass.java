package SourceCodeReading;

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
    private ThreadPoolExecutor threadPoolExecutor;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private Collections collections;    // 简单看了部分方法
    //-------
    private Queue queue;
    private BlockingQueue blockingQueue;
    private ArrayBlockingQueue arrayBlockingQueue;
    private LinkedBlockingQueue linkedBlockingQueue;
    private ConcurrentLinkedQueue concurrentLinkedQueue;
    //-------
    private Map map;    // 简单看了下
    private HashMap hashMap;    // 红黑树相关的还没细看
    private Hashtable hashtable;    // 简单看了下，没有理由会被用到的类
    private LinkedHashMap linkedHashMap;
    private ConcurrentHashMap concurrentHashMap;
    private WeakHashMap weakHashMap;
    private TreeMap treeMap;
    private SortedMap sortedMap;
    //-------
    private List list;
    private ArrayList arrayList;
    private LinkedList linkedList;
    //-------
    private Set set;
    private HashSet hashSet;
    private LinkedHashSet linkedHashSet;
    private SortedSet sortedSet;
    private TreeSet treeSet;

    public static void main(String[] args) {

    }
}
