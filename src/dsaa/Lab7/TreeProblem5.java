package dsaa.Lab7;

import java.io.*;
import java.util.*;

public class TreeProblem5 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        Node[] nodes = new Node[n + 1];
        Edge[] edges = new Edge[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 1; i <= n - 1; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].nxt.add(nodes[y]);
            nodes[y].nxt.add(nodes[x]);
            edges[i] = new Edge(i, nodes[x], nodes[y]);
        }

        //region Calculate level(bfs)
        boolean[] isVzt = new boolean[n + 1];
        int levelCnt = 0;
        Queue<Node> queue = new LinkedList<>();
        isVzt[1] = true;
        nodes[1].level = levelCnt;
        queue.offer(nodes[1]);
        while (!queue.isEmpty()) {
            int size = queue.size();
            levelCnt++;
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                assert cur != null;
                for (int j = 0; j < cur.nxt.size(); j++) {
                    Node next = cur.nxt.get(j);
                    if (!isVzt[next.id]) {
                        next.level = levelCnt;
                        isVzt[next.id] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        //endregion

        Arrays.fill(isVzt, false);

        long sum = 0L;
        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            int opt = in.nextInt();
            int ei = in.nextInt();
            long x = in.nextLong();

            //region Update
            Node a = edges[ei].a;
            Node b = edges[ei].b;
            if (opt == 2) {
                Node temp = a;
                a = b;
                b = temp;
            }
            if (a.level > b.level) {
                a.mark += x;
            } else {
                sum += x;
                b.mark -= x;
            }
            //endregion
        }

        //region Calculate mark(bfs)
        isVzt[1] = true;
        queue.offer(nodes[1]);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                assert cur != null;
                cur.val += cur.mark;
                for (int j = 0; j < cur.nxt.size(); j++) {
                    Node next = cur.nxt.get(j);
                    if (!isVzt[next.id]) {
                        next.mark += cur.mark;
                        isVzt[next.id] = true;
                        queue.offer(nodes[next.id]);
                    }
                }
            }
        }
        //endregion

        for (int i = 1; i <= n; i++) {
            out.println(nodes[i].val + sum);
        }
        out.close();
    }

    static class Edge {
        int id;
        Node a;
        Node b;

        public Edge(int id, Node a, Node b) {
            this.id = id;
            this.a = a;
            this.b = b;
        }
    }

    static class Node {
        int id;
        long val;
        long mark;
        int level;
        ArrayList<Node> nxt;

        public Node(int id) {
            this.level = 0;
            this.mark = 0L;
            this.id = id;
            this.val = 0L;
            nxt = new ArrayList<>();
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
