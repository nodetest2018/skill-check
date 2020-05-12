package q003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Q003 集計と並べ替え
 *
 * 以下のデータファイルを読み込んで、出現する単語ごとに数をカウントし、アルファベット辞書順に並び変えて出力してください。
 * resources/q003/data.txt
 * 単語の条件は以下となります
 * - "I"以外は全て小文字で扱う（"My"と"my"は同じく"my"として扱う）
 * - 単数形と複数形のように少しでも文字列が異れば別単語として扱う（"dream"と"dreams"は別単語）
 * - アポストロフィーやハイフン付の単語は1単語として扱う（"isn't"や"dead-end"）
 *
 * 出力形式:単語=数
 *
[出力イメージ]
（省略）
highest=1
I=3
if=2
ignorance=1
（省略）

 * 参考
 * http://eikaiwa.dmm.com/blog/4690/
 */
public class Q003 {
    /**
     * データファイルを開く
     * resources/q003/data.txt
     */
    private static InputStream openDataFile() {
        return Q003.class.getResourceAsStream("data.txt");
    }

    public static void main(String[] args) {
        try {
            Map<String, Integer> map = new HashMap<>();
            String delimit = "\\,|\\.|\\;|[\\s]|\\–";
            InputStreamReader is = new InputStreamReader(openDataFile(), "utf-8");
            BufferedReader br = new BufferedReader(is);
            String ln;
            while ((ln = br.readLine()) != null) {
                String[] words = ln.split(delimit, -1);
                for (String word : words) {
                    if (!word.isEmpty()) {
                        if (!word.equals("I")) {
                            word = word.toLowerCase();
                        }
                        if (map.containsKey(word)) {
                            int count = map.get(word) + 1;
                            map.put(word, count);
                        } else {
                            map.put(word, 1);
                        }
                    }
                }
            }
            List<String> keylist = new ArrayList<>(map.keySet());
            Collections.sort(keylist, new java.util.Comparator<String>() {
                @Override
                public int compare(String k1, String k2) {
                    String s1 = k1.toLowerCase();
                    String s2 = k2.toLowerCase();
                    return s1.compareTo(s2);
                }
            });
            for (String k : keylist) {
                System.out.println(k + "=" + map.get(k));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
// 完成までの時間: 3時間 20分