package q005;

import java.util.HashMap;
import java.util.Map;

/**
 * 作業時間管理クラス
 * 自由に修正してかまいません
 */
public class WorkData {
    /** 社員番号 */
    private String number;

    /** 役職 */
    private String position;

    /** Pコード */
    private String pCode;

    /** 作業時間(分) */
    private int workTime;

    private Map<String, Integer> positionMap;
    private Map<String, Integer> pCodeMap;
    private Map<String, Integer> numberMap;

    public WorkData() {
        this.positionMap = new HashMap<>();
        this.pCodeMap = new HashMap<>();
        this.numberMap = new HashMap<>();
    }

    public void setData(String nb, String pn, String pc, int wt) {
        this.number = nb;
        this.position = pn;
        this.pCode = pc;
        this.workTime = wt;
    }

    public void aggregateData() {
        if (positionMap.containsKey(this.position)) {
            int workTime = positionMap.get(this.position) + this.workTime;
            positionMap.put(this.position, workTime);
        } else {
            positionMap.put(this.position, this.workTime);
        }

        if (pCodeMap.containsKey(this.pCode)) {
            int workTime = pCodeMap.get(this.pCode) + this.workTime;
            pCodeMap.put(this.pCode, workTime);
        } else {
            pCodeMap.put(this.pCode, this.workTime);
        }

        if (numberMap.containsKey(this.position)) {
            int workTime = numberMap.get(this.number) + this.workTime;
            numberMap.put(this.number, workTime);
        } else {
            numberMap.put(this.number, this.workTime);
        }
    }

    public void outData() {
        for (String key : positionMap.keySet()) {
            int hr = positionMap.get(key) / 60;
            int mn = positionMap.get(key) % 60;
            System.out.println(key + ": " + hr + "時間" + mn  + "分");
        }

        for (String key : pCodeMap.keySet()) {
            int hr = pCodeMap.get(key) / 60;
            int mn = pCodeMap.get(key) % 60;
            System.out.println(key + ": " + hr + "時間" + mn  + "分");
        }

        for (String key : numberMap.keySet()) {
            int hr = numberMap.get(key) / 60;
            int mn = numberMap.get(key) % 60;
            System.out.println(key + ": " + hr + "時間" + mn  + "分");
        }
    }
}
