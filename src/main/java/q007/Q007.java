package q007;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * q007 最短経路探索
 *
 * 壁を 'X' 通路を ' ' 開始を 'S' ゴールを 'E' で表現された迷路で、最短経路を通った場合に
 * 何歩でゴールまでたどり着くかを出力するプログラムを実装してください。
 * もし、ゴールまで辿り着くルートが無かった場合は -1 を出力してください。
 * なお、1歩は上下左右のいずれかにしか動くことはできません（斜めはNG）。
 *
 * 迷路データは MazeInputStream から取得してください。
 * 迷路の横幅と高さは毎回異なりますが、必ず長方形（あるいは正方形）となっており、外壁は全て'X'で埋まっています。
 * 空行が迷路データの終了です。
 *

[迷路の例]
XXXXXXXXX
XSX    EX
X XXX X X
X   X X X
X X XXX X
X X     X
XXXXXXXXX

[答え]
14
 */
public class Q007 {
    /** 迷路のサイズ */
    private static int width = 0;
    private static int height = 0;
    /** スタート地点 */
    private static int start_x = 0;
    private static int start_y = 0;
    /** ゴール地点 */
    private static int goal_x = 0;
    private static int goal_y = 0;

    /** 迷路の１コマを表すインナークラス */
    private static class Block {
        /** 座標 */
        int x;
        int y;
        /** スタート地点か否か */
        boolean start = false;
        /** ゴール地点か否か */
        boolean goal = false;
        /** 壁か否か */
        boolean wall = false;
        /** 正解の軌道か否か */
        boolean correct = false;
        /** 探索済みか否か */
        boolean stamped = false;
        /** スタート地点からの移動距離 */
        int cost;
        /** ゴールまでの最短移動距離 */
        int expected;
        /** スタートからゴールまでの最小期待値 */
        int getPoint() {
            return cost + expected;
        }
        /** 親ブロック */
        Block parent = null;
    }

    /** 迷路の生成と表示 */
    private static List<String> createMaze(MazeInputStream maze) throws IOException {
        System.out.println("[迷路]");
        BufferedReader br = new BufferedReader(new InputStreamReader(maze));
        List<String> lineList = new ArrayList<String>();
        String line;
        while ((line = br.readLine()) != null) {
            if (!(line.isEmpty() || line.startsWith(" "))) {
                lineList.add(line);
                System.out.println(line);
            }
        }
        return lineList;
    }

    /** 迷路の解析 */
    private static Block[][] analysisMazeBlocks(List<String> lineList) {
        Block[][] mazeBlocks = new Block[width][height];

        /** 各ブロックの通路、壁、スタート地点、ゴール地点を解析 */
        for (int y = 0; y < lineList.size(); y++) {
            for (int x = 0; x < lineList.get(y).length(); x++) {
                mazeBlocks[x][y] = new Block();
                String block = lineList.get(y).substring(x, x + 1);
                if (" ".equals(block)) {

                } else if ("X".equals(block)) {
                    mazeBlocks[x][y].wall = true;
                } else if ("S".equals(block)) {
                    mazeBlocks[x][y].start = true;
                    start_x = x;
                    start_y = y;
                } else if ("E".equals(block)) {
                    mazeBlocks[x][y].goal = true;
                    goal_x = x;
                    goal_y = y;
                }
                mazeBlocks[x][y].x = x;
                mazeBlocks[x][y].y = y;
            }
        }

        /** 各ブロックのゴールまでの最短移動距離を算出・格納 */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mazeBlocks[x][y].expected = Math.abs(goal_x - x) + Math.abs(goal_y - y);
            }
        }
        return mazeBlocks;
    }

    /** 迷路の探索 */
    private static void mazeSearch(Block[][] mazeBlocks) throws Exception {
        List<Block> openList = new ArrayList<Block>();

        /** スタート地点をオープン */
        mazeBlocks[start_x][start_y].cost = 0;
        openList.add(mazeBlocks[start_x][start_y]);

        while (true) {
            /** スタートからゴールまでの期待値が最小のものから順次探索する */
            Block now = getShortestOpenBlock(openList);
            now.stamped = true;

            /** 現在地点がゴールに隣接していたら探索終了 */
            if (now.x + 1 == goal_x && now.y == goal_y) {
                mazeBlocks[goal_x][goal_y].parent = now;
                break;
            } else if (now.x == goal_x && now.y + 1 == goal_y) {
                mazeBlocks[goal_x][goal_y].parent = now;
                break;
            } else if (now.x - 1 == goal_x && now.y == goal_y) {
                mazeBlocks[goal_x][goal_y].parent = now;
                break;
            } else if (now.x == goal_x && now.y - 1 == goal_y) {
                mazeBlocks[goal_x][goal_y].parent = now;
                break;
            }

            /** 現在地点の四方のうち、進めるブロックをオープンリストに追加 */
            if (now.x + 1 <= width &&
                    !mazeBlocks[now.x + 1][now.y].wall &&
                    !mazeBlocks[now.x + 1][now.y].stamped) {
                mazeBlocks[now.x + 1][now.y].parent = now;
                openList.add(mazeBlocks[now.x + 1][now.y]);
            }
            if (now.x - 1 >= 0 &&
                    !mazeBlocks[now.x - 1][now.y].wall &&
                    !mazeBlocks[now.x - 1][now.y].stamped) {
                mazeBlocks[now.x - 1][now.y].parent = now;
                mazeBlocks[now.x - 1][now.y].cost = now.cost + 1;
                openList.add(mazeBlocks[now.x - 1][now.y]);
            }
            if (now.y + 1 <= height &&
                    !mazeBlocks[now.x][now.y + 1].wall &&
                    !mazeBlocks[now.x][now.y + 1].stamped) {
                mazeBlocks[now.x][now.y + 1].parent = now;
                mazeBlocks[now.x][now.y + 1].cost = now.cost + 1;
                openList.add(mazeBlocks[now.x][now.y + 1]);
            }
            if (now.y - 1 >= 0 &&
                    !mazeBlocks[now.x][now.y - 1].wall &&
                    !mazeBlocks[now.x][now.y - 1].stamped) {
                mazeBlocks[now.x][now.y - 1].parent = now;
                mazeBlocks[now.x][now.y - 1].cost = now.cost + 1;
                openList.add(mazeBlocks[now.x][now.y - 1]);
            }
        }
    }

    /** オープンされているブロックのうち、最小コストのブロックを返却する */
    private static Block getShortestOpenBlock(List<Block> openList) throws Exception {
        if (openList.size() == 0) {
            throw new Exception("ゴールへ辿り付く経路がありません.");
        }
        Block shortest = null;
        int index = 0;
        for (int i = 0; i < openList.size(); i++) {
            if (shortest == null ||
                    shortest.getPoint() >= openList.get(i).getPoint()) {
                shortest = openList.get(i);
                index = i;
            }
        }
        return openList.remove(index);
    }

    /** 探索経路のマーキングと表示 */
    public static void markMaze(Block[][] mazeBlocks) {
        System.out.println();
        System.out.println("[経路]");

        /** ゴールからスタートまでの道のりをマーキング */
        Block parent = mazeBlocks[goal_x][goal_y].parent;
        while (true) {
            if (parent.start == false) {
                parent.correct = true;
                parent = parent.parent;
            } else {
                break;
            }
        }
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Block block = mazeBlocks[x][y];
                if (block.wall) {
                    System.out.print("X");
                } else if (block.start) {
                    System.out.print("S");
                } else if (block.goal) {
                    System.out.print("E");
                } else if (block.correct) {
                    System.out.print("o");
                    count++;
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("[答え]");
        System.out.println(count);
    }

    public static void main(String[] args) {
        MazeInputStream maze = new MazeInputStream();
        try {
            List<String> lineList = createMaze(maze);
            width = lineList.get(0).length();
            height = lineList.size();

            /** 迷路データ解析結果を格納 */
            Block[][] mazeBlocks = analysisMazeBlocks(lineList);

            /** 迷路探索（辿り着かなかった場合、例外発生） */
            mazeSearch(mazeBlocks);

            /** 探索経路のマーキングと表示 */
            markMaze(mazeBlocks);
          } catch (Exception e) {
            System.out.println();
            System.out.println("[答え]");
            System.out.println(-1);
        }
    }
}
// 完成までの時間: 5時間 00分