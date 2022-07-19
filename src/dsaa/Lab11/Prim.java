package dsaa.Lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Prim {
    private static final QReader in = new QReader();

    public static void main(String[] args) {
        int n = in.nextInt(), m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        boolean[] isVzt = new boolean[n + 1];
        int[] dis = new int[n + 1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[1] = 0;
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node(i);
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int z = in.nextInt();
            nodes[x].next.add(new Edge(z, nodes[y]));
            nodes[y].next.add(new Edge(z, nodes[x]));
        }
        int res = 0;
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.offer(new Edge(0, nodes[1]));
        while (!queue.isEmpty()) {
            Edge minEdge = queue.poll();
            if (isVzt[minEdge.to.id]) continue;
            isVzt[minEdge.to.id] = true;
            res += minEdge.weight;
            for (Edge edge : minEdge.to.next) {
                if (edge.weight < dis[edge.to.id]) {
                    dis[edge.to.id] = edge.weight;
                    queue.offer(edge);
                }
            }
        }
        System.out.println(res);
    }

    static class Node {
        int id;
        ArrayList<Edge> next;

        public Node(int id) {
            this.id = id;
            next = new ArrayList<>();
        }
    }

    static class Edge implements Comparable<Edge> {
        int weight;
        Node to;

        public Edge(int weight, Node to) {
            this.weight = weight;
            this.to = to;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(weight, o.weight);
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
