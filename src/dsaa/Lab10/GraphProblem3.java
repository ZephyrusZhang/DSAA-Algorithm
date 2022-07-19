package dsaa.Lab10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GraphProblem3 {
    private static final QReader in = new QReader();
    private static int[] match;
    private static boolean[] isMatched;
    private static boolean[] isVzt;
    private static int[] next;
    private static int matchCnt = 0;
    private static int res = 0;

    public static void main(String[] args) {
        int n = in.nextInt();
        Node[] nodes = new Node[n + 1];
        match = new int[n + 1];
        isMatched = new boolean[n + 1];
        isVzt = new boolean[n + 1];
        next = new int[n + 1];
        nodes[0] = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(in.nextInt(), in.nextInt());
        }
        Arrays.sort(nodes);
        for (int i = 1; i <= n - 1; i++) {
            if (nodes[i].y == nodes[i + 1].y) next[i] = i + 1;
        }
        dfs(1, n);
        System.out.println(res);
    }

    public static void dfs(int root, int n) {
        if (matchCnt >= n) {
            boolean hasCycle = false;
            for (int i = 1; i <= n; i++) {
                if (hasCycle) break;
                Arrays.fill(isVzt, false);
                int cur = i;
                while (next[cur] != 0) {
                    if (isVzt[cur]) {
                        hasCycle = true;
                        break;
                    }
                    isVzt[cur] = true;
                    cur = match[next[cur]];
                }
            }
            if (hasCycle) res++;
            return;
        }
        if (isMatched[root]) {
            dfs(root + 1, n);
        } else {
            for (int i = root + 1; i <= n; i++) {
                if (isMatched[i]) continue;
                match[root] = i;
                match[i] = root;
                isMatched[root] = isMatched[i] = true;
                matchCnt += 2;
                dfs(root + 1, n);
                match[root] = match[i] = 0;
                isMatched[root] = isMatched[i] = false;
                matchCnt -= 2;
            }
        }
    }

    static class Node implements Comparable<Node> {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node o) {
            return (y == o.y) ? Integer.compare(x, o.x) : Integer.compare(y, o.y);
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
