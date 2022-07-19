package algorithm.Lab2;

import java.io.*;
import java.util.StringTokenizer;

public class ABunnyAtLarge {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final long MOD = 998244353L;

    public static void main(String[] args) {
        int n = in.nextInt();
        int[] childCnt = new int[n + 1];
        for (int i = 0; i < n - 1; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            childCnt[x]++;
            childCnt[y]++;
        }

        long res = 1L;
        for (int i = 1; i <= childCnt[1]; i++) {
            res *= i;
            res %= MOD;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= childCnt[i] - 1; j++) {
                res *= j;
                res %= MOD;
            }
        }

        out.println(res);
        out.close();
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