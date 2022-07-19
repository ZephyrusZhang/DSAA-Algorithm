package dsaa.Lab3;

import java.io.*;
import java.util.StringTokenizer;

public class RankingMoviesTest {
    public static void main(String[] args) {
        QReader in = new QReader();
        int n = in.nextInt();
        int[] rankingListOfMe = new int[n];
        int[] rankingListOfPeter = new int[n];
        int[] peterMovie2RankingIndex = new int[n + 1];
        for (int i = 0; i < n; ++i) rankingListOfMe[i] = in.nextInt();
        for (int i = 0; i < n; ++i) {
            rankingListOfPeter[i] = in.nextInt();
            peterMovie2RankingIndex[rankingListOfPeter[i]] = i;
        }
        long similarity = 0L;
        for (int i = 1; i < n; ++i) {
            if (rankingListOfMe[i] == rankingListOfPeter[0]) continue;
            int curJMovieIndex = peterMovie2RankingIndex[rankingListOfMe[i]];
            for (int j = 0; j < i; ++j) {
                if (peterMovie2RankingIndex[rankingListOfMe[j]] < curJMovieIndex) similarity++;
            }
        }
        System.out.println(similarity);
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
