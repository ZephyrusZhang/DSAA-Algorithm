package dsaa.Lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Kosaraju {
    private static final QReader in = new QReader();
    private static boolean[] isVzt;
    private static final Queue<Node> queue = new LinkedList<>();

    public static void main(String[] args) {
        int n = in.nextInt(), m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        Node[] reverse = new Node[n + 1];
        isVzt = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
            reverse[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].next.add(nodes[y]);
            reverse[y].next.add(reverse[x]);
        }
        boolean isSCC = bfs(nodes[1]) == n && bfs(reverse[1]) == n;
        System.out.println(isSCC ? "Yes" : "No");
    }

    public static int bfs(Node root) {
        int cnt = 0;
        Arrays.fill(isVzt, false);
        queue.clear();
        queue.offer(root);
        cnt++;
        isVzt[root.id] = true;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.next) {
                if (isVzt[next.id]) continue;
                queue.offer(next);
                cnt++;
                isVzt[next.id] = true;
            }
        }
        return cnt;
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
