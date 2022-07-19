package algorithm.Lab12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Flowwwww {
    private static final QReader in = new QReader();
    private static final Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt();
        int t = in.nextInt();
        long[][] residual = new long[n + 1][n + 1];
        int[] pre = new int[n + 1];
        long[] dis = new long[n + 1];
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            long c = in.nextLong();
            residual[u][v] += c;
        }
        long maxflow = 0L;
        while (true) {
            long flow = -1L;

            //region BFS
            Arrays.fill(pre, -1);
            Arrays.fill(dis, Long.MAX_VALUE);
            queue.clear();

            queue.offer(s);
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                if (cur == t) break;
                for (int i = 1; i <= n; i++) {
                    if (residual[cur][i] > 0 && pre[i] == -1) {
                        pre[i] = cur;
                        dis[i] = Math.min(dis[cur], residual[cur][i]);
                        queue.offer(i);
                    }
                }
            }
            if (pre[t] != -1) {
                flow = dis[t];
            }
            //endregion

            if (flow == -1L) break;
            for (int cur = t; cur != s; cur = pre[cur]) {
                residual[pre[cur]][cur] -= flow;
                residual[cur][pre[cur]] += flow;
            }
            maxflow += flow;
        }
        System.out.println(maxflow);
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
