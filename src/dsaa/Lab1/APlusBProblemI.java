package dsaa.Lab1;

import java.io.*;
import java.util.StringTokenizer;

public class APlusBProblemI {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int c = in.nextInt();
        int[] list = new int[n];
        for (int i = 0; i < n; ++i) {
            list[i] = in.nextInt();
        }

        int cnt = 0;
        for (int i = 0; i < n - 1; ++i) {
            if (list[i] >= c) {
                break;
            }
            for (int j = i + 1; j < n; ++j) {
                if (list[j] >= c) {
                    break;
                }
                if (list[i] + list[j] == c) {
                    cnt++;
                }
            }
        }

        out.println(cnt);
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

    static class QWriter implements Closeable {
        private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
            } catch (IOException ignored) {
            }
        }

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