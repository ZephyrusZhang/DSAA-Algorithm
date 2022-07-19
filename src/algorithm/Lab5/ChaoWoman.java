package algorithm.Lab5;

import java.io.*;
import java.util.*;

public class ChaoWoman {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final long INF = Long.MAX_VALUE;

    public static void main(String[] args) {
        final int N = in.nextInt();
        final int M = in.nextInt();
        Node[] nodes = new Node[N + 1];
        for (int i = 1; i < nodes.length; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 1; i <= M; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            long w = in.nextLong();
            nodes[x].edges.add(new Edge(i, w, nodes[y]));
            nodes[y].edges.add(new Edge(i, w, nodes[x]));
        }

        int[] cnt = new int[M + 1];
        long[] dis = new long[N + 1];
        boolean[] djiVzt = new boolean[N + 1];
        boolean[] bfsVzt = new boolean[N + 1];
        PriorityQueue<DisTo> priorityQueue = new PriorityQueue<>();
        Queue<Node> queue = new LinkedList<>();
        final int P = in.nextInt();
        for (int x = 0; x < P; x++) {
            int s = in.nextInt();
            int t = in.nextInt();

            //region Init
            Arrays.fill(dis, INF);
            dis[s] = 0;
            Arrays.fill(djiVzt, false);
            Arrays.fill(bfsVzt, false);
            priorityQueue.clear();
            queue.clear();
            //endregion

            //region Dijkstra
            priorityQueue.offer(new DisTo(s, 0));
            while (!priorityQueue.isEmpty()) {
                Node cur = nodes[priorityQueue.poll().nodeID];
                djiVzt[cur.id] = true;
                for (int i = 0; i < cur.edges.size(); i++) {
                    Edge edge = cur.edges.get(i);
                    if (djiVzt[edge.to.id]) continue;
                    long tmpDis = dis[cur.id] + edge.weight;
                    if (tmpDis < dis[edge.to.id]) {
                        dis[edge.to.id] = tmpDis;
                        priorityQueue.offer(new DisTo(edge.to.id, dis[edge.to.id]));
                    }
                }
            }
            //endregion

            //region Reverse BFS
            bfsVzt[t] = true;
            queue.offer(nodes[t]);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                for (int i = 0; i < cur.edges.size(); i++) {
                    Edge edge = cur.edges.get(i);
                    Node next = edge.to;
                    if (dis[next.id] + edge.weight == dis[cur.id]) {
                        cnt[edge.id]++;
                        if (!bfsVzt[next.id]) {
                            queue.offer(next);
                            bfsVzt[next.id] = true;
                        }
                    }
                }
            }
            //endregion

        }

        for (int i = 1; i <= M; i++) {
            out.println(cnt[i]);
        }
        out.close();
    }

    static class DisTo implements Comparable<DisTo> {
        int nodeID;
        long dis;

        public DisTo(int nodeID, long dis) {
            this.nodeID = nodeID;
            this.dis = dis;
        }

        @Override
        public int compareTo(DisTo o) {
            return Long.compare(this.dis, o.dis);
        }
    }

    static class Node {
        int id;
        ArrayList<Edge> edges;

        public Node(int id) {
            this.id = id;
            edges = new ArrayList<>();
        }
    }

    static class Edge implements Comparable<Edge> {
        int id;
        long weight;
        Node to;

        public Edge(int id, long weight, Node to) {
            this.id = id;
            this.weight = weight;
            this.to = to;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.weight, o.weight);
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

        public long nextLong() {
            return Long.parseLong(next());
        }

    }

    static class QWriter implements Closeable {
        private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

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
