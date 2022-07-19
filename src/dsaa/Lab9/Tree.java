package dsaa.Lab9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Tree {
    private static final QReader in = new QReader();
    private static final Queue<Node> queue = new LinkedList<>();
    private static int cnt = 0;

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        int[] degree = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].next.add(nodes[y]);
            nodes[y].next.add(nodes[x]);
            degree[x]++;
            degree[y]++;
        }
        for (int i = 1; i <= n; i++) {
            if (degree[i] == 1) {
                queue.offer(nodes[i]);
                cnt++;
            }
        }
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.next) {
                degree[next.id]--;
                if (degree[next.id] == 1) {
                    queue.offer(next);
                    cnt++;
                }
            }
        }
        System.out.println(cnt == n ? "tree" : "graph");
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
