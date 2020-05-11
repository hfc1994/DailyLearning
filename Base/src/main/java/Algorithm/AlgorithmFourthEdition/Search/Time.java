package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/5/11.
 *
 * 3.1.4 开发抽象数据类型Time和Event来处理表3.1.5中的例子中的数据
 *
 * 键            值
 * 09:00:00     Chicago
 * 09:00:03     Phoenix
 * 09:00:13     Houston
 * 09:00:59     Chicago
 * 09:01:10     Houston
 * 09:03:13     Chicago
 * 09:10:11     Seattle
 * 09:10:25     Seattle
 * 09:14:25     Phoenix
 * 09:19:32     Chicago
 * 09:19:46     Chicago
 * 09:21:05     Chicago
 * 09:22:43     Seattle
 * 09:22:54     Seattle
 * 09:25:52     Chicago
 * 09:35:21     Chicago
 * 09:36:14     Seattle
 * 09:37:44     Phoenix
 */
public class Time implements Comparable<Time> {

    protected int hour;
    protected int minute;
    protected int second;

    // 不考虑异常情况
    public Time(String strTime) {
        String[] strs = strTime.split(":");
        hour = Integer.parseInt(strs[0]);
        minute = Integer.parseInt(strs[1]);
        second = Integer.parseInt(strs[2]);
    }

    @Override
    public int compareTo(Time o) {
        if (this == o)
            return 0;

        if (hour < o.hour)
            return -1;
        else if (hour > o.hour)
            return 1;
        else if (minute < o.minute)
            return -1;
        else if (minute > o.minute)
            return 1;
        else if (second < o.second)
            return -1;
        else if (second > o.second)
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (hour < 10)
            sb.append(0);
        sb.append(hour).append(":");

        if (minute < 10)
            sb.append(0);
        sb.append(minute).append(":");

        if (second < 10)
            sb.append(0);
        sb.append(second);
        return sb.toString();
    }

    public static void main(String[] args) {
        Time t1 = new Time("09:22:54");
        Time t2 = new Time("09:12:54");
        System.out.println("t1 hour = " + t1.hour);
        System.out.println("t1 minute = " + t1.minute);
        System.out.println("t1 second = " + t1.second);
        System.out.println("t1 is " + t1);
        System.out.println("t1 < t2 is " + String.valueOf(t1.compareTo(t2) < 0));
    }
}
