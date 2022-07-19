package dsaa.Lab9;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class GraphTraversal {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static boolean[] isVzt;
    private static int[] res;
    private static final Queue<Node> queue = new LinkedList<>();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node(i);
        isVzt = new boolean[n + 1];
        res = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[y].next.add(nodes[x]);
        }
        for (int i = n; i >= 1; i--) {
            bfs(nodes[i]);
        }
        for (int i = 1; i <= n; i++) {
            out.print(res[i] + " ");
        }
        out.close();
    }

    public static void bfs(Node root) {
        if (isVzt[root.id]) return;
        queue.clear();
        queue.offer(root);
        res[root.id] = root.id;
        isVzt[root.id] = true;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.next) {
                if (isVzt[next.id]) continue;
                res[next.id] = root.id;
                queue.offer(next);
                isVzt[next.id] = true;
            }
        }
    }

    static class Node {
        int id;
        ArrayList<Node> next;

        public Node(int id) {
            this.id = id;
            this.next = new ArrayList<>();
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
