package dsaa.Lab3;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RankingMovies {

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int[] rankingListOfMe = new int[n];
        int[] rankingListOfPeter = new int[n];
        for (int i = 0; i < n; ++i) rankingListOfMe[i] = in.nextInt();
        for (int i = 0; i < n; ++i) rankingListOfPeter[i] = in.nextInt();
        int[] peterRanking2MyRanking = new int[n];
        Map<Integer, Integer> myMovieID2RankingMap = new HashMap<>();
        for (int i = 0; i < n; ++i)
            myMovieID2RankingMap.put(rankingListOfMe[i], i);
        for (int i = 0; i < n; ++i)
            peterRanking2MyRanking[n - i - 1] = myMovieID2RankingMap.get(rankingListOfPeter[i]);

        System.out.println(countInversePairs(peterRanking2MyRanking));
        out.close();
    }

    public static long countInversePairs(int[] arr) {
        if (arr.length < 2) return 0L;
        int m = (arr.length + 1) / 2;
        int[] left = Arrays.copyOfRange(arr, 0, m);
        int[] right = Arrays.copyOfRange(arr, m, arr.length);
        return countInversePairs(left) + countInversePairs(right) + merge(arr, left, right);
    }

    public static long merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, count = 0;
        while (i < left.length || j < right.length) {
            if (i == left.length) {
                arr[i + j] = right[j];
                j++;
            } else if (j == right.length) {
                arr[i + j] = left[i];
                i++;
            } else if (left[i] <= right[j]) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                count += left.length - i;
                j++;
            }
        }
        return count;
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
