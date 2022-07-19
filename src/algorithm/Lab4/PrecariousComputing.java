package algorithm.Lab4;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.lang.Math.floor;
import static java.lang.Math.log;

public class PrecariousComputing {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        final int N = in.nextInt();
        final long M = in.nextLong();
        long[][] sparseTable = new long[(int) (log(N) / log(2)) + 1][N];
        int[][] indexSparseTable = new int[(int) (log(N) / log(2)) + 1][N];
        for (int i = 0; i < N; i++) {
            indexSparseTable[0][i] = i;
            sparseTable[0][i] = in.nextLong();
        }

        //region Build sparse table of times and relative index sparse table (min)
        for (int i = 1; i < sparseTable.length; i++) {
            for (int j = 0; j + (1 << i) - 1 < N; j++) {
                if (sparseTable[i - 1][j] < sparseTable[i - 1][j + (1 << (i - 1))]) {
                    sparseTable[i][j] = sparseTable[i - 1][j];
                    indexSparseTable[i][j] = indexSparseTable[i - 1][j];
                } else {
                    sparseTable[i][j] = sparseTable[i - 1][j + (1 << (i - 1))];
                    indexSparseTable[i][j] = indexSparseTable[i - 1][j + (1 << (i - 1))];
                }
            }
        }
        //endregion

        //region Build prefix sum of incomes
        long[] sum = new long[N + 1];
        long tmpSum = 0;
        for (int i = 1; i <= N; i++) {
            tmpSum += in.nextLong();
            sum[i] = tmpSum;
        }
        //endregion

        PriorityQueue<Interval> intervals = new PriorityQueue<>();
        Interval initInterval = new Interval(0, N - 1, sum[N] - sum[0], 0);
        intervals.offer(initInterval);
        long income = 0;
        int timeCost = 0;
        while (timeCost < M && !intervals.isEmpty()) {
            Interval max = intervals.poll();

            //region Search for the minimal time and its index
            long minTime;
            int minIdx;
            int s = (int) floor(log(max.r - max.l + 1) / log(2));
            if (sparseTable[s][max.l] < sparseTable[s][max.r - (1 << s) + 1]) {
                minTime = sparseTable[s][max.l];
                minIdx = indexSparseTable[s][max.l];
            } else {
                minTime = sparseTable[s][max.r - (1 << s) + 1];
                minIdx = indexSparseTable[s][max.r - (1 << s) + 1];
            }
            //endregion

            //region Compute income and time cost
            if (minTime - max.base > M - timeCost) {
                income += (M - timeCost) * (sum[max.r + 1] - sum[max.l]);
                break;
            }
            income += (minTime - max.base) * (sum[max.r + 1] - sum[max.l]);
            timeCost += (minTime - max.base);
            //endregion

            //region Split sub-interval and add it into priority queue
            int left_l = max.l;
            int left_r = minIdx - 1;
            if (left_l <= left_r) {
                Interval left = new Interval(left_l, left_r, sum[left_r + 1] - sum[left_l], minTime);
                intervals.offer(left);
            }
            int right_l = minIdx + 1;
            int right_r = max.r;
            if (right_l <= right_r) {
                Interval right = new Interval(right_l, right_r, sum[right_r + 1] - sum[right_l], minTime);
                intervals.offer(right);
            }
            //endregion

        }

        out.println(income);
        out.close();
    }

    static class Interval implements Comparable<Interval> {
        int l;
        int r;
        long sum;
        long base;

        public Interval(int l, int r, long sum, long base) {
            this.l = l;
            this.r = r;
            this.sum = sum;
            this.base = base;
        }

        @Override
        public int compareTo(Interval o) {
            return Long.compare(o.sum, this.sum);
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
