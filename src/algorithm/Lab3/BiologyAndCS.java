package algorithm.Lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BiologyAndCS {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final int MOD = 1000000007;

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] species = new Node[n + 1];
        int[] inCnt = new int[n + 1];
        int[] outCnt = new int[n + 1];
        int[] forward = new int[n + 1];
        int[] reverse = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            species[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            int predator = in.nextInt();
            int prey = in.nextInt();
            species[predator].next.add(prey);
            outCnt[predator]++;
            species[prey].pre.add(predator);
            inCnt[prey]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        //region Forward Topological Sort
        for (int i = 1; i <= n; i++) {
            if (inCnt[i] == 0) {
                forward[i] = 1;
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = 0; i < species[cur].next.size(); i++) {
                int next = species[cur].next.get(i);
                forward[next] += forward[cur];
                forward[next] %= MOD;
                inCnt[next]--;
                if (inCnt[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        //endregion

        //region Reverse Topological Sort
        for (int i = 1; i <= n; i++) {
            if (outCnt[i] == 0) {
                reverse[i] = 1;
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = 0; i < species[cur].pre.size(); i++) {
                int pre = species[cur].pre.get(i);
                reverse[pre] += reverse[cur];
                reverse[pre] %= MOD;
                outCnt[pre]--;
                if (outCnt[pre] == 0) {
                    queue.offer(pre);
                }
            }
        }
        //endregion

        for (int i = 1; i <= n; i++) {
            long res = (long) forward[i] * (long) reverse[i];
            res %= MOD;
            out.print(res + " ");
        }
        out.close();
    }

    static class Node {
        int species;
        ArrayList<Integer> next;
        ArrayList<Integer> pre;

        public Node(int species) {
            this.species = species;
            next = new ArrayList<>();
            pre = new ArrayList<>();
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

    }

    static class QWriter implements Closeable {
        private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
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
