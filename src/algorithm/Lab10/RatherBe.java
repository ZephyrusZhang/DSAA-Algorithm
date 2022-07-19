package algorithm.Lab10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RatherBe {
    private static final QReader in = new QReader();
    private static final int[] a = new int[1005];

    public static void main(String[] args) {
        int n = in.nextInt();
        int[] array = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            array[i] = in.nextInt();
        }
        n = distinct(array, n);
        int[][] dp = new int[n + 1][n + 1];
        for (int x = 2; x <= n; x++) {
            int i = 1;
            int j = x;
            for (int k = 1; k <= n - x + 1; k++, i++, j++) {
                if (a[i] == a[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }
        System.out.println(dp[1][n]);
    }

    public static int distinct(int[] array, int n) {
        int index = 1;
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            if (array[i] == array[i - 1]) continue;
            a[index++] = array[i];
            cnt++;
        }
        return cnt;
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
