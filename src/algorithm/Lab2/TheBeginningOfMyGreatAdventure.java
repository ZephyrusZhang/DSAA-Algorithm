package algorithm.Lab2;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class TheBeginningOfMyGreatAdventure {
    public static QReader in = new QReader();
    public static QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int[] a = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            a[i] = in.nextInt();
        }
        boolean[] isVisited = new boolean[n + 1];
        int[] dist = new int[n + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        isVisited[1] = true;
        deque.offerFirst(1);
        int[] neighbors = new int[3];
        int levelCnt = 1;
        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            for (int i = 0; i < levelSize; i++) {
                int cur = deque.removeFirst();
                neighbors[0] = cur - 1;
                neighbors[1] = a[cur];
                neighbors[2] = cur + 1;
                int len = cur == n ? 2 : 3;
                for (int j = cur == 1 ? 1 : 0; j < len; j++) {
                    if (!isVisited[neighbors[j]]) {
                        deque.offerLast(neighbors[j]);
                        isVisited[neighbors[j]] = true;
                        dist[neighbors[j]] = levelCnt;
                    }
                }
            }
            levelCnt++;
        }
        for (int i = 1; i < n + 1; i++) {
            out.print(dist[i] + " ");
        }
        out.close();
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

        public void hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
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

        @Override
        public void close() {
            try {
                writer.close();
            } catch (IOException ignored) {
            }
        }
    }
}
