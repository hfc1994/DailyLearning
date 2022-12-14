package Util.Guava;

import com.google.common.collect.HashBasedTable;

/**
 * Created by hfc on 2022/12/13.
 *
 * HashBasedTable 本质上用的是 LinkedHashMap<R, LinkedHashMap<C, V>> 来实现的
 * TreeBasedTable 本质上用的是 TreeMap<R, TreeMap<C, V>> 来实现的
 * StandardRowSortedTable 本质上用的是 SortedMap<R, SortedMap<C, V>>
 * ArrayTable 要求在构造时就指定行和列的大小，本质上由一个二维数组实现，可以提升
 *      访问速度和密集 Table 的内存利用率
 *
 */
public class TableDemo {

    public static void main(String[] args) {
        HashBasedTable<String, Integer, String> table = HashBasedTable.create();
        table.put("A", 1, "A_value1");
        table.put("A", 2, "A_value2");
        table.put("B", 1, "B_value1");
        table.put("C", 3, "C_value3");

        System.out.println(table.get("A", 1));  // A_value1
        System.out.println(table.get("C", 3));  // C_value3

        table.cellSet().forEach(cell -> {
            System.out.print("rowKey: " + cell.getRowKey() + " colKey:" + cell.getColumnKey()
                    + " value:" + cell.getValue());
            System.out.println();
        });

        // rowKey: A B C
        System.out.print("rowKey: ");
        table.rowKeySet().forEach(rowKey -> {
            System.out.print(rowKey + " ");
        });
        System.out.println();

        // colKey: 1 2 3
        System.out.print("colKey: ");
        table.columnKeySet().forEach(columnKey -> {
            System.out.print(columnKey + " ");
        });
        System.out.println();

        // colKey: 1,val: A_value1
        // colKey: 2,val: A_value2
        table.row("A").forEach((colKey, val) -> {
            System.out.println("colKey: " + colKey + ",val: " + val);
        });

        // TODO: 2022/12/14 怎么获取的
        // rowKey: A,val: A_value1
        // rowKey: B,val: B_value1
        table.column(1).forEach((rowKey, val) -> {
            System.out.println("rowKey: " + rowKey + ",val: " + val);
        });


        System.out.println(table.contains("C", 4));   // false
        System.out.println(table.containsRow("A")); // true
        System.out.println(table.containsColumn(2));    // true
        System.out.println(table.containsValue("B_value1"));    // true

        table.remove("A", 1);
        // colKey: 2,val: A_value2
        table.row("A").forEach((colKey, val) -> {
            System.out.println("colKey: " + colKey + ",val: " + val);
        });
    }

}
