package algorithm.Lab9;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class KValid {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final long MOD = 1000000007L;
    private static long[][] temp;

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        temp = new long[m][m];
        long[][] mat = new long[m][m];
        for (int i = 0; i < m; i++) mat[i][0] = 1L;
        for (int i = 1; i <= m - 1; i++) mat[i - 1][i] = 1L;
        long[][] dp = new long[1][m];
        dp[0][0] = 1L;
        mat = pow(mat, n);
        mul(dp, mat, dp);
        long res = 0L;
        for (int i = 0; i < m; i++) {
            res += dp[0][i];
            res %= MOD;
        }
        out.println(res);
        out.close();
    }

    public static long[][] pow(long[][] mat, long n) {
        long[][] res = new long[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++)
            res[i][i] = 1L;
        while (n > 0) {
            if (n % 2 == 1L) mul(res, mat, res);
            mul(mat, mat, mat);
            n >>= 1;
        }
        return res;
    }

    public static void mul(long[][] a, long[][] b, long[][] res) {
        long tmp;
        for (long[] longs : temp) Arrays.fill(longs, 0L);
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[0].length; k++) {
                tmp = a[i][k];
                for (int j = 0; j < b[0].length; j++) {
                    temp[i][j] += tmp * b[k][j];
                    temp[i][j] %= MOD;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(temp[i], 0, res[i], 0, b[0].length);
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
