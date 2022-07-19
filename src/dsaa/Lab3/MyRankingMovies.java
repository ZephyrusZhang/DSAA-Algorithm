package dsaa.Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MyRankingMovies {
    private static int[] aux;

    public static void main(String[] args) {
        QReader in = new QReader();
        int n = in.nextInt();
        int[] rankingListOfMe = new int[n];
        int[] rankingListOfPeter = new int[n];
        for (int i = 0; i < n; ++i) rankingListOfMe[i] = in.nextInt();
        for (int i = 0; i < n; ++i) rankingListOfPeter[i] = in.nextInt();
        int[] peterRanking2MyRanking = new int[n];
        aux = new int[n];
        Map<Integer, Integer> myMovieID2RankingMap = new HashMap<>();
        for (int i = 0; i < n; ++i)
            myMovieID2RankingMap.put(rankingListOfMe[i], i);
        for (int i = 0; i < n; ++i)
            peterRanking2MyRanking[n - i - 1] = myMovieID2RankingMap.get(rankingListOfPeter[i]);

        System.out.println(countInversePairs(peterRanking2MyRanking, 0, n - 1));
    }

    public static long countInversePairs(int[] arr, int left, int right) {
        if (left >= right) return 0L;
        int mid = (left + right) >> 1;
        return countInversePairs(arr, 0, mid) + countInversePairs(arr, mid + 1, right) + (arr[mid] > arr[mid + 1] ? merge(arr, left, mid, right) : 0L);
    }

    public static long merge(int[] arr, int left, int mid, int right) {
        int index = left;
        int i = left;
        int j = mid + 1;
        long cnt = 0L;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                aux[index++] = arr[i++];
            } else {
                cnt += mid - i + 1;
                aux[index++] = arr[j++];
            }
        }
        while (i <= mid) aux[index++] = arr[i++];
        while (j <= right) aux[index++] = arr[j++];
        if (right + 1 - left >= 0) System.arraycopy(aux, left, arr, left, right + 1 - left);
        return cnt;
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
