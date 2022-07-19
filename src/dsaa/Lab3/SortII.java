package dsaa.Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SortII {
    public static void main(String[] args) {
        QReader in = new QReader();
        int n = in.nextInt();
        int[] array = new int[n];
        int[] sortedArray = new int[n];
        boolean[] isVisited = new boolean[n];
        for (int i = 0; i < n; ++i) {
            array[i] = in.nextInt();
            sortedArray[i] = array[i];
        }

        Arrays.sort(sortedArray);
        int globalMin = sortedArray[0];
        HashMap<Integer, Integer> num2Index = new HashMap<>();
        for (int i = 0; i < n; ++i)
            num2Index.put(sortedArray[i], i);
        long res = 0L;
        for (int i = 0; i < n; ++i) {
            if (isVisited[i]) continue;
            long sum = 0L;
            int num = 0;
            int min = Integer.MAX_VALUE;
            int cur = i;
            while (!isVisited[cur]) {
                sum += array[cur];
                num++;
                min = Math.min(min, array[cur]);
                isVisited[cur] = true;
                cur = num2Index.get(array[cur]);
            }
            if (num > 0) {
                res += Math.min(sum + (long) (num - 2) * min, sum + min + (long) (num + 1) * globalMin);
            }
        }
        System.out.println(res);
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
