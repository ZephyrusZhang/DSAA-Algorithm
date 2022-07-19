package algorithm.Lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class StrangeCourses {
    private static final QReader in = new QReader();
    private static final long MOD = 1000000007L;

    public static void main(String[] args) {
        int n = in.nextInt(), m = in.nextInt();
        int[][] graph = new int[n + 1][n + 1];
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph[v][u] = 1;
        }
        long[] dp = new long[1 << n];
        dp[0] = 1L;
        for (int cur = 0; cur < (1 << n) - 1; cur++) {
            for (int i = 0; i < n; i++) {
                if ((cur >> i & 1) == 1) continue;
                int node = i + 1;
                int cnt = 0;
                for (int j = 1; j <= n; j++) {
                    if ((((cur >> (j - 1)) & 1) == 1) && graph[j][node] == 1)
                        cnt++;
                }
                int next = cur + (1 << (node - 1));
                dp[next] += dp[cur] * (1L << cnt);
                dp[next] %= MOD;
            }
        }
        System.out.println(dp[(1 << n) - 1]);
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
}
