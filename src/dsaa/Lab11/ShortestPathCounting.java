package dsaa.Lab11;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class ShortestPathCounting {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final int MOD = 100003;

    public static void main(String[] args) {
        int n = in.nextInt(), m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        boolean[] isVzt = new boolean[n + 1];
        int[] dis = new int[n + 1];
        int[] cnt = new int[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node(i);
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].next.add(nodes[y]);
            nodes[y].next.add(nodes[x]);
        }
        cnt[1] = 1;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(nodes[1]);
        isVzt[1] = true;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.next) {
                if (isVzt[next.id]) {
                    if (dis[cur.id] + 1 == dis[next.id]) {
                        cnt[next.id] += cnt[cur.id];
                        cnt[next.id] %= MOD;
                    }
                } else {
                    cnt[next.id] += cnt[cur.id];
                    cnt[next.id] %= MOD;
                    queue.offer(next);
                    isVzt[next.id] = true;
                    dis[next.id] = dis[cur.id] + 1;
                }
            }
        }
        for (int i = 1; i <= n; i++)
            out.println(cnt[i]);
        out.close();
    }

    static class Node {
        int id;
        ArrayList<Node> next;

        public Node(int id) {
            this.id = id;
            next = new ArrayList<>();
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
