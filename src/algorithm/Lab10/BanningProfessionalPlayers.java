package algorithm.Lab10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BanningProfessionalPlayers {
    private static final QReader in = new QReader();
    private static final long MOD = 1000000000L + 7L;

    public static void main(String[] args) {
        int n = in.nextInt();
        int l = in.nextInt();
        int r = in.nextInt();
        int[] a = new int[n + 1];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
            max = max(max, a[i]);
        }
        int sum = Arrays.stream(a).sum();
        int[] cnt = new int[max + 1];
        for (int i = 1; i <= n; i++) {
            cnt[a[i]]++;
        }
        long[] dp = new long[r + 1];
        dp[0] = 1L;
        for (int i = 1; i <= max; i++) {
            for (int j = r; j >= i; j--) {
                for (int k = 1; k <= min(cnt[i], j / i); k++) {
                    dp[j] += dp[j - k * i];
                    dp[j] %= MOD;
                }
            }
        }
        long res = 0L;
        for (int i = max(sum - r, l); i <= min(sum - l, r); i++) {
            res += dp[i];
            res %= MOD;
        }
        System.out.println(res);
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
