package dsaa.Lab9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Transaction {
    private static final QReader in = new QReader();

    public static void main(String[] args) {
        int n = in.nextInt();
        Node[] nodes = new Node[n + 1];
        int[] inDeg = new int[n + 1];
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < n; i++) {
            int cur = in.nextInt();
            nodes[cur].weight = in.nextInt();
            int pre;
            while ((pre = in.nextInt()) != 0) {
                nodes[pre].next.add(nodes[cur]);
                inDeg[cur]++;
            }
        }
        Queue<Node> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDeg[i] == 0) {
                queue.offer(nodes[i]);
                dp[i] = nodes[i].weight;
            }
        }
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.next) {
                inDeg[next.id]--;
                dp[next.id] = Math.max(dp[next.id], dp[cur.id] + next.weight);
                if (inDeg[next.id] == 0) {
                    queue.offer(nodes[next.id]);
                }
            }
        }
        int res = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++)
            res = Math.max(res, dp[i]);
        System.out.println(res);
    }

    static class Node {
        int id;
        int weight;
        ArrayList<Node> next;

        public Node(int id) {
            this.id = id;
            this.weight = 0;
            this.next = new ArrayList<>();
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
