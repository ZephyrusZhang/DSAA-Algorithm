package dsaa.Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WoodCut {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long k = in.nextLong();
        long[] logs_length = new long[n];
        for (int i = 0; i < logs_length.length; ++i) {
            logs_length[i] = in.nextLong();
        }

        Arrays.sort(logs_length);

        System.out.println(binarySearch(logs_length, k));
    }

    public static long binarySearch(long[] logs_length, long k) {
        long low = 1;
        long high = logs_length[logs_length.length - 1];
        while (low + 1 < high) {
            long mid = low + (high - low) / 2;
            if (calculatePiecesNum(logs_length, mid) >= k) {
                low = mid;
            } else {
                high = mid;
            }
        }
        if (calculatePiecesNum(logs_length, high) >= k) {
            return high;
        }
        if (calculatePiecesNum(logs_length, low) >= k) {
            return low;
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
