import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by hfc on 2023/7/1.
 * 1-33 取6个
 * 1-16 取1个
 */
public class ColorPaper {

    private static String dataPath = "F:\\java_idea\\daletou\\daletou-all.json";

    // daletou
    // 01-35 取5个
    // 01-12 取2个
    //
    // fuli
    // 1-33 取6个
    // 1-16 取1个
    public static void main(String[] args) throws IOException {
        doDaLeTou();
    }

    private static void doDaLeTou() throws IOException {
        List<Map<String, String>> datas = loadHistoryData();
        Set<String> hisData = new HashSet<>();
        datas.forEach(oMap -> hisData.add(oMap.get("number")));
        System.out.println(hisData.size());

        String result = doGen(35, 12, 5, 2);
        while (hisData.contains(result)) {
            System.out.println("conflict... " + result);
            result = doGen(35, 12, 5, 2);
        }
        System.out.println("result: " + result);
    }

    private static List<Integer> doInit(List<Integer> list, int limit) {
        for (int i=1; i<=limit; i++) {
            list.add(i);
        }
        return list;
    }

    private static List<Integer> makeChaos(List<Integer> list) {
        Random r = new Random();
        for (int c=0; c<365; c++) {
            int lIdx = r.nextInt(list.size());
            int rIdx = r.nextInt(list.size());
            while (lIdx == rIdx) {
                rIdx = r.nextInt(list.size());
            }

            int temp = list.get(lIdx);
            list.set(lIdx, list.get(rIdx));
            list.set(rIdx, temp);
        }

        return list;
    }

    private static String doGen(int redLimit, int blueLimit, int redCnt, int blueCnt) {
        List<Integer> reds = doInit(new ArrayList<>(), redLimit);
        List<Integer> blues = doInit(new ArrayList<>(), blueLimit);

        List<Integer> targetReds = new ArrayList<>();
        List<Integer> targetBlues = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        for (int i=1; i<=redCnt; i++) {
            makeChaos(reds);
            Random r = new Random();
            int idx = r.nextInt(365 * 365);
            idx = idx % reds.size();
            targetReds.add(reds.remove(idx));
        }
        targetReds.sort(Comparator.naturalOrder());
        System.out.println("reds: " + targetReds.size());
        sb.append("red ( ");
        for (int i=0; i<targetReds.size(); i++) {
            int target = targetReds.get(i);
            if (target < 10) {
                sb.append("0");
            }
            sb.append(target).append(" ");
        }
        sb.append(" ) ");

        for (int i=1; i<=blueCnt; i++) {
            makeChaos(blues);
            Random r = new Random();
            int idx = r.nextInt(365 * 365);
            idx = idx % blues.size();
            targetBlues.add(blues.remove(idx));
        }
        targetBlues.sort(Comparator.naturalOrder());
        System.out.println("blues: " + targetBlues.size());
        sb.append("blue ( ");
        for (int i=0; i<targetBlues.size(); i++) {
            int target = targetBlues.get(i);
            if (target < 10) {
                sb.append("0").append(target);
            } else {
                sb.append(target);
            }

            if (i != targetBlues.size() - 1) {
                sb.append(" ");
            }
        }
        sb.append(" ) ");
        return sb.toString();
    }

    private void doStat(List<Map<String, String>> datas) {
        int maxNum = Integer.MIN_VALUE;
        int minNum = Integer.MAX_VALUE;
        for (Map<String, String> oMap : datas) {
            int index = Integer.parseInt(oMap.get("index"));
            if (index > maxNum) maxNum = index;
            if (index < minNum) minNum = index;

            System.out.print(oMap.get("index") + " " + oMap.get("date") + " " + oMap.get("number"));
            System.out.println();
        }

        System.out.println(maxNum + " " + minNum );
        System.out.println("diff: " + (maxNum - minNum + 1));
        System.out.println("size: " + datas.size());
    }

    private static List<Map<String, String>> loadHistoryData() throws IOException {
        File hisFile = new File(dataPath);
        byte[] hisDataBytes = Files.readAllBytes(hisFile.toPath());
        JSONObject json = JSON.parseObject(new String(hisDataBytes));

        List<Map<String, String>> result = new ArrayList<>();
        JSONArray jArray = json.getJSONObject("value").getJSONArray("list");
        for (Object obj : jArray) {
            JSONObject target = (JSONObject) obj;

            Map<String, String> oMap = new HashMap<>();
            oMap.put("index", target.getString("lotteryDrawNum"));
            oMap.put("number", target.getString("lotteryDrawResult"));
            oMap.put("date", target.getString("lotteryDrawTime"));
            result.add(oMap);
        }
        return result;
    }

}
