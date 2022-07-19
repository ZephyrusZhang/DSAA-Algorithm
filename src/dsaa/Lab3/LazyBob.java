package dsaa.Lab3;

import java.io.*;
import java.util.StringTokenizer;

public class LazyBob {
    public static void main(String[] args) {
        QReader in = new QReader();
        int n = in.nextInt();
        int k = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = in.nextInt();
        }
        System.out.println(search(arr, 0, n - 1, k));
    }

    public static int search(int[] arr, int l, int r, int k) {
        if (l >= r) return arr[l];
        int i = l;
        int j = r;
        int base = arr[l];
        while (i < j) {
            while (base >= arr[j] && i < j) --j;
            while (base <= arr[i] && i < j) ++i;
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[l] = arr[i];
        arr[i] = base;
        if (j - l + 1 >= k) return search(arr, l, j, k);
        else return search(arr, j + 1, r, k - (j - l + 1));
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

}
