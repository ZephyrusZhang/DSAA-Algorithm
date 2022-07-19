package dsaa.Lab10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GraphProblem2 {
    private static final QReader in = new QReader();
    private static final Queue<Node> queue = new LinkedList<>();
    private static boolean[] isVzt;

    public static void main(String[] args) {
        int k = in.nextInt();
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        isVzt = new boolean[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node(i);
        for (int i = 0; i < k; i++)
            nodes[in.nextInt()].pNum++;
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            nodes[a].next.add(nodes[b]);
        }
        for (int i = 1; i <= n; i++)
            bfs(nodes[i], nodes[i].pNum);
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (nodes[i].cnt == k) res++;
        }
        System.out.println(res);
    }

    public static void bfs(Node root, int pNum) {
        if (root.pNum == 0) return;
        queue.clear();
        Arrays.fill(isVzt, false);
        queue.offer(root);
        isVzt[root.id] = true;
        root.cnt += pNum;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.next) {
                if (isVzt[next.id]) continue;
                queue.offer(next);
                isVzt[next.id] = true;
                next.cnt += pNum;
            }
        }
    }

    static class Node {
        int id;
        int cnt;
        ArrayList<Node> next;
        int pNum;

        public Node(int id) {
            this.id = id;
            this.cnt = 0;
            this.pNum = 0;
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
