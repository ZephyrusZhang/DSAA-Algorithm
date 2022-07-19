package dsaa.Lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TreeProblem3 {
    private static final QReader in = new QReader();
    private static int n;
    private static long[] dis;
    private static int[] nodeNum;

    public static void main(String[] args) {
        n = in.nextInt();
        Node[] nodes = new Node[n + 1];
        dis = new long[n + 1];
        nodeNum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            nodes[a].next.add(nodes[b]);
            nodes[b].next.add(nodes[a]);
        }
        dfs(nodes[1]);
        for (int i = 1; i <= n; i++) {
            nodes[i].isVisited = false;
        }
        DFS(nodes[1]);
        int centroid = 0;
        long minDisSum = Long.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (dis[i] < minDisSum) {
                minDisSum = dis[i];
                centroid = i;
            }
        }
        System.out.println(centroid + " " + minDisSum);
    }

    public static void dfs(Node root) {
        if (root.isVisited) return;
        root.isVisited = true;
        nodeNum[root.id] += 1;
        for (Node next : root.next) {
            if (next.isVisited) continue;
            dfs(next);
            nodeNum[root.id] += nodeNum[next.id];
            dis[root.id] += dis[next.id] + nodeNum[next.id];
        }
    }

    public static void DFS(Node root) {
        if (root.isVisited) return;
        root.isVisited = true;
        for (Node next : root.next) {
            if (next.isVisited) continue;
            dis[next.id] = dis[root.id] - 2L * nodeNum[next.id] + n;
            DFS(next);
        }
    }

    static class Node {
        int id;
        boolean isVisited;
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

}
