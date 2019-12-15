package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/15.
 *
 * 1.3.14 编写一个类ResizingArrayQueueOfStrings，使用定长数组实现队列的抽象，
 * 然后扩展实现，使用调整数组的方法突破大小的限制。
 *
 *
 */
public class ResizingArrayQueueOfStrings<K> {

    private K[] objects;

    private int size = 0;

    private int DEFAULT_CAPACITY = 16;

    private int MAX_CAPACITY = 1 << 30;

    @SuppressWarnings("unchecked")
    public ResizingArrayQueueOfStrings() {
        objects = (K[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ResizingArrayQueueOfStrings(int initCapacity) {
        if (initCapacity <= 0)
            throw new IllegalArgumentException("illegal init capacity: " + initCapacity);
        if (initCapacity > MAX_CAPACITY)
            initCapacity = MAX_CAPACITY;

        objects = (K[]) new Object[initCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for (K ele : objects) {
            if (ele.equals(o))
                return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean add(K o) {
        if (size != objects.length)
            objects[size++] = o;
        else {
            int newCapacity;
            if (size == MAX_CAPACITY)
                throw new OutOfMemoryError();
            else if (size >= (MAX_CAPACITY >> 1))
                newCapacity = MAX_CAPACITY;
            else
                newCapacity = 2 * objects.length;

            K[] newObjcets = (K[]) new Object[newCapacity];
            System.arraycopy(objects, 0, newObjcets, 0, size);
            newObjcets[size++] = o;
            objects = newObjcets;
        }
        return true;
    }

    public boolean remove(K o) {
        for (int i=0; i<size; i++) {
            if (objects[i].equals(o)){
                objects[i] = null;
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public K remove() {
        K o = objects[--size];
        objects[size] = null;

        if (objects.length > 32 && size < (objects.length >> 2)) {
            int newCapacity = objects.length >> 1;
            K[] newObjects = (K[]) new Object[newCapacity];
            System.arraycopy(objects, 0, newObjects, 0, size);
            objects = newObjects;
        }

        return o;
    }

    public void clear() {
        do {
            objects[--size] = null;
        } while (size != 0);
    }

    public K peek() {
        if (size == 0)
            return null;
        else
            return objects[size - 1];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i=0; i<size; i++) {
            sb.append(objects[i].toString())
                .append(",");
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        ResizingArrayQueueOfStrings<String> queue = new ResizingArrayQueueOfStrings<>(5);
        System.out.println("---after init---");
        System.out.println("size = " + queue.size());
        System.out.println("isEmpty = " + queue.isEmpty());
        System.out.println("toString = " + queue.toString());
        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");
        queue.add("ddd");
        System.out.println("---after add---");
        System.out.println("size = " + queue.size());
        System.out.println("isEmpty = " + queue.isEmpty());
        System.out.println("toString = " + queue.toString());
        queue.remove();
        System.out.println("---after remove---");
        System.out.println("size = " + queue.size());
        System.out.println("isEmpty = " + queue.isEmpty());
        System.out.println("toString = " + queue.toString());
        queue.add("eee");
        queue.add("fff");
        queue.add("ggg");
        System.out.println("---after resize---");
        System.out.println("size = " + queue.size());
        System.out.println("isEmpty = " + queue.isEmpty());
        System.out.println("toString = " + queue.toString());
    }
}
