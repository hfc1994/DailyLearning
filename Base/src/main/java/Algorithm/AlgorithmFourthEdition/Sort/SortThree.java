package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/23.
 *
 * 2.2.21 一式三份。给定三个列表，每个列表中包含N个名字，编写一个
 * 线性对数级别的算法来判定三份列表中是否包含有公共的名字，如果有，
 * 返回第一个被找到的这种名字
 */
public class SortThree {

    public int findThree(int[] da, int[] db, int[] dc) {
        int aLen = da.length;
        int bLen = db.length;
        int cLen = dc.length;

        int aIndex = 0;
        int bIndex = 0;
        int cIndex = 0;

        while (aIndex < aLen && bIndex < bLen && cIndex < cLen) {
            if (da[aIndex] == db[bIndex] && da[aIndex] == dc[cIndex])
                return da[aIndex];

            int max = da[aIndex] > db[bIndex] ? da[aIndex] : db[bIndex];
            max = max > dc[cIndex] ? max : dc[cIndex];

            while (aIndex < aLen && da[aIndex] < max) aIndex++;
            while (bIndex < bLen && db[bIndex] < max) bIndex++;
            while (cIndex < cLen && dc[cIndex] < max) cIndex++;
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] da = new int[] {43,21,3,6,87,45,65,90,34,23};
        int[] db = new int[] {12,76,7,23,16,29,45,54,32,19};
        int[] dc = new int[] {73,51,73,61,87,23,16,83,15,42};

        MergeUB mub = new MergeUB();
        mub.sort(da);
        mub.sort(db);
        mub.sort(dc);

        SortThree st = new SortThree();
        int ret = st.findThree(da, db, dc);
        System.out.println("--- ret ---");
        System.out.println(ret);
    }
}
