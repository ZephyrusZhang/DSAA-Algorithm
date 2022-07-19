package algorithm.Lab9;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Rectangle {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final long MOD = 1000000007;
    private static long[][] dp;
    private static final long[] sumL = new long[20000];
    private static final long[] sumR = new long[20000];

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        int depth = (min(n, m) + 1) >> 1;
        int length = max(n, m);
        dp = new long[depth + 1][length + 1];
        solve(depth, length);
        long res = 0L;
        for (int i = 1; i <= depth; i++) {
            res += dp[i][n] * dp[i][m];
            res %= MOD;
        }
        out.println(res >= 0 ? res : res + MOD);
        out.close();
    }

    public static void solve(int depth, int length) {
        for (int j = 1; j <= length; j++) {
            dp[1][j] = 1L;
        }
        for (int i = 2; i <= depth; i++) {
            long sum = 0L;
            for (int j = 1; j <= length; j++) {
                sum += dp[i - 1][j];
                sum %= MOD;
                sumL[j] = sum;
            }
            sum = 0L;
            for (int j = 1; j <= length; j++) {
                sum += dp[i - 1][j] * j;
                sum %= MOD;
                sumR[j] = sum;
            }
            for (int j = 2; j <= length; j++) {
                dp[i][j] = (((j - 1) * sumL[j - 2]) % MOD - sumR[j - 2]) % MOD;
            }
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

        public void println(Object object) {
            try {
                writer.write(object.toString());
                writer.write("\n");
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
