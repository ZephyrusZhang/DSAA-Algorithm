package dsaa.Lab9;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class AsukasMaze {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final char[][] maze = new char[1505][1505];
    private static final Queue<Pair> queue = new LinkedList<>();
    private static final int[][] vzt = new int[1505][1505];

    public static void main(String[] args) {
        int n, m, x = 0, y = 0;
        String line;
        while ((n = in.nextInt()) != -1 && (m = in.nextInt()) != -1) {
            for (int i = 1; i <= n; i++) {
                line = in.next();
                for (int j = 1; j <= m; j++) {
                    maze[i][j] = line.charAt(j - 1);
                    if (maze[i][j] == 'S') {
                        x = i;
                        y = j;
                    }
                }
            }
            if (bfs(x, y, n, m)) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
        out.close();
    }

    public static boolean bfs(int x, int y, int n, int m) {
        queue.clear();
        queue.offer(new Pair(x, y));
        vzt[x][y] = hash(x, y);
        Pair cur;
        int nextX = 0, nextY = 0, modX, modY;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0 -> {
                        nextX = cur.x;
                        nextY = cur.y - 1;
                    }
                    case 1 -> {
                        nextX = cur.x - 1;
                        nextY = cur.y;
                    }
                    case 2 -> {
                        nextX = cur.x;
                        nextY = cur.y + 1;
                    }
                    case 3 -> {
                        nextX = cur.x + 1;
                        nextY = cur.y;
                    }
                }
                modX = (nextX + n) % n;
                modY = (nextY + m) % m;
                if (maze[modX][modY] == '#') continue;
                if (vzt[modX][modY] != hash(nextX, nextY)) {
                    return true;
                } else if (vzt[modX][modY] == 0) {
                    vzt[modX][modY] = hash(nextX, nextY);
                    queue.offer(new Pair(nextX, nextY));
                }
            }
        }
        return false;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int hash(int x, int y) {
        return (53 + x) * 53 + y;
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

    static class QWriter implements Closeable {
        private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
            } catch (IOException ignored) {
            }
        }

        public void println(Object object) {
            try {
                writer.write(object.toString());
                writer.write("\n");
            } catch (IOException ignored) {
            }
        }

        @Override
        public void close() {
            try {
                writer.close();
            } catch (IOException ignored) {
            }
        }
    }
}
