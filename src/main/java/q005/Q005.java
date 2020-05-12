package q005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Q005 データクラスと様々な集計
 *
 * 以下のファイルを読み込んで、WorkDataクラスのインスタンスを作成してください。
 * resources/q005/data.txt
 * (先頭行はタイトルなので読み取りをスキップする)
 *
 * 読み込んだデータを以下で集計して出力してください。
 * (1) 役職別の合計作業時間
 * (2) Pコード別の合計作業時間
 * (3) 社員番号別の合計作業時間
 * 上記項目内での出力順は問いません。
 *
 * 作業時間は "xx時間xx分" の形式にしてください。
 * また、WorkDataクラスは自由に修正してください。
 *
[出力イメージ]
部長: xx時間xx分
課長: xx時間xx分
一般: xx時間xx分
Z-7-31100: xx時間xx分
I-7-31100: xx時間xx分
T-7-30002: xx時間xx分
（省略）
194033: xx時間xx分
195052: xx時間xx分
195066: xx時間xx分
（省略）
 */
public class Q005 {
    /**
     * データファイルを開く
     * resources/q005/data.txt
     */
    private static InputStream openDataFile() {
        return Q005.class.getResourceAsStream("data.txt");
    }

    public static void main(String[] args) {
        try {
            WorkData wd = new WorkData();
            String header = "社員番号,部署名,役職名,P-CODE,作業時間";
            String delimit = "\\,";
            InputStreamReader is = new InputStreamReader(openDataFile(), "utf-8");
            BufferedReader br = new BufferedReader(is);
            String ln;
            while ((ln = br.readLine()) != null) {
                if (header.equals(ln)) {
                    continue;
                }
                String[] datas = ln.split(delimit);
                wd.setData(datas[0], datas[2], datas[3], Integer.parseInt(datas[4]));
                wd.aggregateData();
            }
            wd.outData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
// 完成までの時間: 5時間 30分