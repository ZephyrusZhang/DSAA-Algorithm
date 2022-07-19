package dsaa.Lab7;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class TreeProblem2 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long c = in.nextLong();
            nodes[a].edges.add(new Edge(c, nodes[b]));
            nodes[b].edges.add(new Edge(c, nodes[a]));
        }
        int q = in.nextInt();
        int r = in.nextInt();
        Queue<Node> queue = new LinkedList<>();
        long[] dis = new long[n + 1];
        boolean[] isVisited = new boolean[n + 1];
        isVisited[r] = true;
        queue.offer(nodes[r]);

        //region BFS
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Edge edge : cur.edges) {
                Node next = edge.to;
                if (isVisited[next.id]) continue;
                isVisited[next.id] = true;
                dis[next.id] += dis[cur.id] + edge.length;
                queue.offer(next);
            }
        }
        //endregion

        for (int i = 0; i < q; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            out.println(dis[x] + dis[y]);
        }
        out.close();
    }

    static class Node {
        int id;
        ArrayList<Edge> edges;

        public Node(int id) {
            this.id = id;
            edges = new ArrayList<>();
        }
    }

    static class Edge {
        long length;
        Node to;

        public Edge(long length, Node to) {
            this.length = length;
            this.to = to;
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
