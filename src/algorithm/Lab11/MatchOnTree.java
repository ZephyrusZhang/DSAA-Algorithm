package algorithm.Lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class MatchOnTree {
    private static final QReader in = new QReader();
    private static long[][] dp;

    public static void main(String[] args) {
        int n = in.nextInt();
        dp = new long[n + 1][2];
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node(i);
        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            long w = in.nextLong();
            nodes[u].edges.add(new Edge(w, nodes[v]));
            nodes[v].edges.add(new Edge(w, nodes[u]));
        }
        dfs(nodes[1], null);
        System.out.println(dp[1][1]);
    }

    public static void dfs(Node cur, Node father) {
        for (Edge edge : cur.edges) {
            Node next = edge.to;
            if (next == father) continue;
            dfs(next, cur);
        }
        if (cur.edges.size() == 0) {
            dp[cur.id][1] = 0L;
        } else {
            for (Edge edge : cur.edges) {
                Node next = edge.to;
                dp[cur.id][0] += dp[next.id][1];
            }
            dp[cur.id][1] = dp[cur.id][0];
            for (Edge edge : cur.edges) {
                Node next = edge.to;
                if (next == father) continue;
                dp[cur.id][1] = max(edge.weight + dp[cur.id][0] - dp[next.id][1] + dp[next.id][0], dp[cur.id][1]);
            }
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

    static class Edge {
        long weight;
        Node to;

        public Edge(long weight, Node to) {
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
}
