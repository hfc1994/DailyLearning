package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/5/11.
 *
 * 3.1.4 开发抽象数据类型Time和Event来处理表3.1.5中的例子中的数据
 */
public class Event {

    protected String msg;

    public Event(String str) {
        this.msg = str;
    }

    @Override
    public String toString() {
        return this.msg;
    }

    public static void main(String[] args) {
        String[] data = new String[]{
                "09:19:32","Chicago","09:19:46","Chicago","09:21:05","Chicago",
                "09:00:59","Chicago","09:01:10","Houston","09:03:13","Chicago",
                "09:35:21","Chicago","09:36:14","Seattle","09:37:44","Phoenix",
                "09:00:00","Chicago","09:00:03","Phoenix","09:00:13","Houston",
                "09:22:43","Seattle","09:22:54","Seattle","09:25:52","Chicago",
                "09:10:11","Seattle","09:10:25","Seattle","09:14:25","Phoenix"
        };

        BinarySearchST<Time, Event> bss = new BinarySearchST<>(18);
        for (int i=0; i<data.length; i=i+2) {
            bss.put(new Time(data[i]), new Event(data[i+1]));
        }

        System.out.println(bss.min());
        System.out.println(bss.get(new Time("09:00:13")));
        System.out.println(bss.floor(new Time("09:05:00")));
        System.out.println(bss.select(7));
        bss.keys(new Time("09:15:00"), new Time("09:25:00"))
                .forEach(key -> System.out.print(key + " "));
        System.out.println();
        System.out.println(bss.ceiling(new Time("09:30:00")));
        System.out.println(bss.max());
        System.out.println(bss.size(new Time("09:15:00"), new Time("09:25:00")));
        System.out.println(bss.rank(new Time("09:10:25")));
    }
}
