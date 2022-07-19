package dsaa.Lab10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class GraphProblem1 {
    private static final QReader in = new QReader();
    private static final char[][] plane = new char[105][105];
    private static final boolean[][] isVzt = new boolean[105][105];
    private static final Queue<Pair> queue = new LinkedList<>();
    private static int cnt = 0;

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            String line = in.nextLine();
            for (int j = 1; j <= m; j++) {
                plane[i][j] = line.charAt(j - 1);
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                bfs(i, j);
            }
        }
        System.out.println(cnt);
    }

    public static void bfs(int x, int y) {
        if (isVzt[x][y] || plane[x][y] == '.') return;
        cnt++;
        queue.clear();
        queue.offer(new Pair(x, y));
        isVzt[x][y] = true;
        while (!queue.isEmpty()) {
            Pair cur = queue.poll();
            x = cur.x;
            y = cur.y;
            if (plane[x - 1][y - 1] == 'W' && !isVzt[x - 1][y - 1]) {    // 左上角
                queue.offer(new Pair(x - 1, y - 1));
                isVzt[x - 1][y - 1] = true;
            }
            if (plane[x - 1][y] == 'W' && !isVzt[x - 1][y]) {            // 上面
                queue.offer(new Pair(x - 1, y));
                isVzt[x - 1][y] = true;
            }
            if (plane[x - 1][y + 1] == 'W' && !isVzt[x - 1][y + 1]) {    // 右上角
                queue.offer(new Pair(x - 1, y + 1));
                isVzt[x - 1][y + 1] = true;
            }
            if (plane[x][y + 1] == 'W' && !isVzt[x][y + 1]) {            // 右边
                queue.offer(new Pair(x, y + 1));
                isVzt[x][y + 1] = true;
            }
            if (plane[x + 1][y + 1] == 'W' && !isVzt[x + 1][y + 1]) {    // 右下角
                queue.offer(new Pair(x + 1, y + 1));
                isVzt[x + 1][y + 1] = true;
            }
            if (plane[x + 1][y] == 'W' && !isVzt[x + 1][y]) {            // 下面
                queue.offer(new Pair(x + 1, y));
                isVzt[x + 1][y] = true;
            }
            if (plane[x + 1][y - 1] == 'W' && !isVzt[x + 1][y - 1]) {    // 左下角
                queue.offer(new Pair(x + 1, y - 1));
                isVzt[x + 1][y - 1] = true;
            }
            if (plane[x][y - 1] == 'W' && !isVzt[x][y - 1]) {            // 左边
                queue.offer(new Pair(x, y - 1));
                isVzt[x][y - 1] = true;
            }
        }
    }

    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class QReader {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
            return true;
        }

        public String nextLine() {
            tokenizer = new StringTokenizer("");
            return innerNextLine();
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

}
