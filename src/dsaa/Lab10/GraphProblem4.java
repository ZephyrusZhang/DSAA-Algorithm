package dsaa.Lab10;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class GraphProblem4 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        int[] inDeg = new int[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node(i);
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].next.add(nodes[y]);
            inDeg[y]++;
        }
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            if (inDeg[i] == 0) {
                queue.offer(nodes[i]);
            }
        }
        StringBuilder str = new StringBuilder();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                n--;
                assert cur != null;
                str.append(cur.id).append(" ");
                for (Node next : cur.next) {
                    inDeg[next.id]--;
                    if (inDeg[next.id] == 0) {
                        queue.offer(next);
                    }
                }
            }
        }
        if (n == 0) {
            out.print(str.toString());
        } else {
            out.print(-1);
        }
        out.close();
    }

    static class Node implements Comparable<Node> {
        int id;
        ArrayList<Node> next;

        public Node(int id) {
            this.id = id;
            next = new ArrayList<>();
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.id, o.id);
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
