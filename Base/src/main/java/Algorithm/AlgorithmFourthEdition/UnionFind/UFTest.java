package Algorithm.AlgorithmFourthEdition.UnionFind;

import java.io.*;

/**
 * Created by user-hfc on 2020/1/13.
 */
public class UFTest {

    private static String[] paths = {"/resources/algs4-data/tinyUF.txt"
            ,"/resources/algs4-data/mediumUF.txt"
            ,"/resources/algs4-data/largeUF.txt"};

    public static void main(String[] args) throws IOException {

        long lBegin = System.currentTimeMillis();

//        route(0, "qf");
//        route(1, "qf");

//        route(0, "qu");
//        route(1, "qu");

//        route(0, "wqu");
//        route(1, "wqu");
//        route(2, "wqu");

//        route(0, "cwqu");
//        route(1, "cwqu");
//        route(2, "cwqu");

//        route(0, "hwqu");
//        route(1, "hwqu");
//        route(2, "hwqu");

        route(0, "dwqu");
//        route(1, "dwqu");
//        route(2, "dwqu");

        long lEnd = System.currentTimeMillis();
        System.out.printf("--- cost %.2f seconds ---\n", (lEnd - lBegin) / 1000.0d);
    }

    private static void route(int fileIndex, String ufType) throws IOException {
        String filePath = System.getProperty("user.dir")
                + paths[fileIndex];

        File f = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(f));
        toReadAndUF(br, ufType);
    }

    private static void toReadAndUF(BufferedReader br, String ufType) throws IOException {
        String line;
        UF uf = null;
        String[] array;
        while ((line = br.readLine()) != null) {
            if (uf == null) {
                int size = Integer.parseInt(line);
                System.out.printf("--- size = %d ---\n", size);
                uf = ufGen(ufType, size);
                continue;
            }

            array = line.split(" ");
            int p = Integer.parseInt(array[0]);
            int q = Integer.parseInt(array[1]);
            if (uf.connected(p, q))
                continue;

            uf.union(p, q);
            System.out.println(line);
        }

        if (uf != null)
            System.out.println(uf.count() + " components");
    }

    private static UF ufGen(String type, int size) {
        switch (type) {
            case "qf":
                return new QuickFind(size);
            case "qu":
                return new QuickUnion(size);
            case "wqu":
                return new WeightQuickUnion(size);
            case "cwqu":
                return new CompressWeightQuickUnion(size);
            case "hwqu":
                return new HeightWeightQuickUnion(size);
            case "dwqu":
                return new DynamicWeightQuickUnion();
            default:
                throw  new IllegalArgumentException();
        }
    }
}
