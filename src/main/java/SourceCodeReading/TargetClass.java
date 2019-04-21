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

    private ReentrantLock reentrantLock;    // 大致看完了一遍
    private ReentrantReadWriteLock reentrantReadWriteLock;  // 大致看完了一遍
    private LockSupport lockSupport;
    private Semaphore semaphore;
    private CountDownLatch countDownLatch;
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
    private TreeMap treeMap;
    private ConcurrentHashMap concurrentHashMap;
    private LinkedHashMap linkedHashMap;
    private WeakHashMap weakHashMap;
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
