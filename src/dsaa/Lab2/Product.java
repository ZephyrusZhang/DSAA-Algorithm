package dsaa.Lab2;

import java.io.*;
import java.util.StringTokenizer;

public class Product {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        long k = in.nextLong();
        long[] a = new long[n];
        long[] b = new long[m];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }
        for (int i = 0; i < m; i++) {
            b[i] = in.nextLong();
        }

        long low = a[0] * b[0];
        long high = a[a.length - 1] * b[b.length - 1];
        while (low < high) {
            long mid = (low + high) / 2;
            long cnt = 0;
            int j = b.length - 1;
            for (long tmp : a) {
                if (tmp * b[0] > mid) break;
                while (j >= 0 && tmp * b[j] > mid) j--;
                cnt += j + 1;
            }
            if (cnt >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        out.println(low);
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

        public long nextLong() {
            return Long.parseLong(next());
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