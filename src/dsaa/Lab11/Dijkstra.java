package dsaa.Lab11;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Dijkstra {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node(i);
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int z = in.nextInt();
            nodes[x].next.add(new Edge(z, nodes[y]));
        }
        boolean[] isVzt = new boolean[n + 1];
        int[] dis = new int[n + 1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[1] = 0;
        PriorityQueue<DisTo> queue = new PriorityQueue<>();
        queue.offer(new DisTo(1, 0));
        int tempDis;
        while (!queue.isEmpty()) {
            Node cur = nodes[queue.poll().nodeID];
            isVzt[cur.id] = true;
            for (Edge edge : cur.next) {
                Node next = edge.to;
                if (isVzt[next.id]) continue;
                tempDis = dis[cur.id] + edge.weight;
                if (tempDis < dis[next.id]) {
                    dis[next.id] = tempDis;
                    queue.offer(new DisTo(next.id, tempDis));
                }
            }
        }
        for (int i = 1; i <= n; i++)
            out.print((dis[i] == Integer.MAX_VALUE ? -1 : dis[i]) + " ");
        out.close();
    }

    static class Node {
        int id;
        ArrayList<Edge> next;

        public Node(int id) {
            this.id = id;
            next = new ArrayList<>();
        }
    }

    static class DisTo implements Comparable<DisTo> {
        int nodeID;
        int dis;

        public DisTo(int nodeID, int dis) {
            this.nodeID = nodeID;
            this.dis = dis;
        }

        @Override
        public int compareTo(DisTo o) {
            return Long.compare(this.dis, o.dis);
        }
    }

    static class Edge {
        int weight;
        Node to;

        public Edge(int weight, Node to) {
            this.weight = weight;
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
