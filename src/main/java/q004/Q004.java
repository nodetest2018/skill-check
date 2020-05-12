package q004;

/**
 * Q004 ソートアルゴリズム
 *
 * ListManagerクラスをnewして、小さい順に並び変えた上でcheckResult()を呼び出してください。
 *
 * 実装イメージ:
 * ListManager data = new ListManager();
 * // TODO 並び換え
 * data.checkResult();
 *
 * - ListManagerクラスを修正してはいけません
 * - ListManagerクラスの dataList を直接変更してはいけません
 * - ListManagerクラスの比較 compare と入れ替え exchange を使って実現してください
 */
public class Q004 {
    public static void main(String[] args) {
        ListManager data = new ListManager();
        for (int i1 = 0; i1 < data.size(); i1++) {
            for (int i2 = i1 + 1; i2 < data.size(); i2++) {
                if (data.compare(i1, i2) == 1) {
                    data.exchange(i1, i2);
                }
            }
        }
        data.checkResult();
    }
}
// 完成までの時間: 2時間 30分