import java.util.*;

/**
 * Created by hfc on 2019/12/8.
 * 当LinkedHashMap和HashMap的初始状态为空时，
 * .iterator()产生的迭代器会保存住新建时的Map状态，相当于是一份快照
 * 在迭代器生成后，如果新添加了元素，那么.hashNext()返回的依然是false。
 */
public class IteratorDemo {

    public static void main(String[] args) {

        System.out.println("-------LinkedHashMap-----------");
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        Set<Map.Entry<String, String>> entry = linkedHashMap.entrySet();
        Iterator<Map.Entry<String, String>> iter = linkedHashMap.entrySet().iterator();
        System.out.println(iter.hasNext());
        System.out.println(entry.iterator().hasNext());
        linkedHashMap.put("aaa", "bbb");
        System.out.println(iter.hasNext());
        System.out.println(entry.iterator().hasNext());

        System.out.println("-------HashMap-----------");
        HashMap<String, String> hashMap = new HashMap<>();
        Iterator<Map.Entry<String, String>> oIterator = hashMap.entrySet().iterator();
        System.out.println(oIterator.hasNext());
        hashMap.put("ccc", "ddd");
        System.out.println(oIterator.hasNext());


        System.out.println("-------ArrayList-----------");
        List<String> olist = new ArrayList<>();
        Iterator<String> iterator = olist.iterator();
        System.out.println(iterator.hasNext());
        System.out.println(olist.iterator().hasNext());
        olist.add("asas");
        System.out.println(iterator.hasNext());
        System.out.println(olist.iterator().hasNext());

    }
}
