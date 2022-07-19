package algorithm.Lab3;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class LegendaryGrabbingMachine {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        int c = in.nextInt();
        int t = in.nextInt();
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = in.nextInt();
        }
        int[] q = new int[m];
        int[] remainingCapacity = new int[m];
        for (int i = 0; i < m; i++) {
            q[i] = in.nextInt();
            remainingCapacity[i] = c;
        }
        Arrays.sort(p);
        Arrays.sort(q);
        int numOfBunniesAlive = 0;
        int curNestIndex = 0;
        int curBunnyIndex = 0;
        while (curNestIndex < m && curBunnyIndex < n) {
            if (Math.abs(q[curNestIndex] - p[curBunnyIndex]) <= t) {
                if (remainingCapacity[curNestIndex] > 0) {
                    numOfBunniesAlive++;
                    remainingCapacity[curNestIndex]--;
                    curBunnyIndex++;
                } else {
                    curNestIndex++;
                }
            } else {
                if (p[curBunnyIndex] < q[curNestIndex]) {
                    curBunnyIndex++;
                } else {
                    curNestIndex++;
                }
            }
        }
        out.println(numOfBunniesAlive);
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
