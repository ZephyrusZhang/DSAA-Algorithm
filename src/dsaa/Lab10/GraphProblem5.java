package dsaa.Lab10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GraphProblem5 {
    private static final QReader in = new QReader();
    private static int[] color;
    private static boolean[] is;

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        color = new int[n + 1];
        is = new boolean[n + 1];
        Arrays.fill(color, -1);
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            nodes[u].next.add(nodes[v]);
        }
        for (int i = 1; i <= n; i++) {
            if (color[i] == -1) {
                dfs(nodes[i]);
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (is[i]) res++;
        }
        System.out.println(res);
    }

    public static boolean dfs(Node cur) {
        color[cur.id] = 0;
        for (Node next : cur.next) {
            if (color[next.id] == 0) {
                is[cur.id] = true;
            } else if (color[next.id] == -1) {
                is[cur.id] = is[cur.id] | dfs(next);
            }
        }
        color[cur.id] = 1;
        return is[cur.id];
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

}
