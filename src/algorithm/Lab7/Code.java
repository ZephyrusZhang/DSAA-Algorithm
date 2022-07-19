package algorithm.Lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.pow;

public class Code {
    private static final QReader in = new QReader();

    public static void main(String[] args) {
        long num = in.nextLong();
        System.out.println(Long.toBinaryString(greyCode(num)));
    }

    public static long greyCode(long n) {
        if (n == 0) return 0L;
        if (n == 1) return 1L;
        long length = Long.toBinaryString(n).length();
        long num = (long) pow(2, length);
        long half = num / 2L - 1;
        if (n > half) {
            return (1L << (length - 1L)) + greyCode(num - n - 1L);
        } else {
            return greyCode(n);
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

        public long nextLong() {
            return Long.parseLong(next());
        }

    }

}
