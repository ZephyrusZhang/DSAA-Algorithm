package dsaa.Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CuttingWood {
    public static void main(String[] args) {
        QReader in = new QReader();
        int n = in.nextInt();
        long k = in.nextLong();
        long[] logs_length = new long[n];
        for (int i = 0; i < logs_length.length; ++i) {
            logs_length[i] = in.nextLong();
        }
        Arrays.sort(logs_length);

        System.out.println(binarySearch(1, logs_length[logs_length.length - 1], logs_length, k));
    }

    public static long binarySearch(long low, long high, long[] logs_length, long k) {
        while (low + 1 < high) {
            long mid = (low + high) / 2;
            if (calculatePiecesNum(logs_length, mid) >= k) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return 0;
    }

    public static long calculatePiecesNum(long[] logs_length, long length) {
        long res = 0;
        for (long l : logs_length) {
            res += l / length;
        }
        return res;
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
