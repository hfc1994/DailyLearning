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
    private TimeUnit timeUnit;
    private Thread thread;
    private ThreadLocal threadLocal;

    //-------
    private Executor executor;
    private Executors executors;
    private ThreadPoolExecutor threadPoolExecutor;

    private Collections collections;
    //-------
    private Queue queue;
    private BlockingQueue blockingQueue;
    private ArrayBlockingQueue arrayBlockingQueue;
    private LinkedBlockingQueue linkedBlockingQueue;
    private ConcurrentLinkedQueue concurrentLinkedQueue;
    //-------
    private Map map;
    private HashMap hashMap;
    private Hashtable hashtable;
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
