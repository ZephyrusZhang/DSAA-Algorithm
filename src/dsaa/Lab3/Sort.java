package dsaa.Lab3;

import java.io.*;
import java.util.StringTokenizer;

public class Sort {
    private static int[] aux;

    public static void main(String[] args) {
        QReader in = new QReader();
        int n = in.nextInt();
        int[] arr = new int[n];
        aux = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = in.nextInt();
        }
        if (n <= 100000) {
            mergeSort(arr, 0, n - 1);
        } else {
            bucketSort(arr);
        }

        if (n > 100000) {
            for (int i = 0; i < arr.length; i += 100) {
                System.out.print(arr[i] + " ");
            }
        } else {
            for (int i : arr) {
                System.out.print(i + " ");
            }
        }
    }

    public static void bucketSort(int[] arr) {
        int[] buckets = new int[2000001];
        for (int j : arr) buckets[j + 1000000]++;
        int index = 0;
        for (int i = 0; i < buckets.length; ++i) {
            if (index >= arr.length) break;
            if (buckets[i] == 0) continue;
            while (buckets[i] > 0) {
                arr[index++] = i - 1000000;
                buckets[i]--;
            }
        }
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l >= r) return;
        int mid = (l + r) >> 1;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        if (arr[mid] > arr[mid + 1]) {
            merge(arr, l, mid, r);
        }
    }

    public static void merge(int[] arr, int l, int mid, int r) {
        int index = l;
        int i = l;
        int j = mid + 1;
        while (i <= mid && j <= r) {
            if (arr[i] < arr[j]) {
                aux[index++] = arr[i++];
            } else {
                aux[index++] = arr[j++];
            }
        }
        while (i <= mid) aux[index++] = arr[i++];
        while (j <= r) aux[index++] = arr[j++];
        if (r + 1 - l >= 0) System.arraycopy(aux, l, arr, l, r + 1 - l);
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
