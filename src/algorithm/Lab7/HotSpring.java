package algorithm.Lab7;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class HotSpring {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static long[] v;
    private static long[] sum;
    private static long[] min_max;
    private static ArrayList<ArrayList<Query>> lists;

    public static void main(String[] args) {
        final int N = in.nextInt();
        v = new long[N + 1];
        sum = new long[N + 1];
        lists = new ArrayList<>(N + 1);
        min_max = new long[N + 1];
        lists.add(new ArrayList<>());
        long tempSum = 0L;
        for (int i = 1; i <= N; i++) {
            v[i] = in.nextLong();
            tempSum += v[i];
            sum[i] = tempSum;
            lists.add(new ArrayList<>());
        }

        final int Q = in.nextInt();
        int l, r;
        Query[] queries = new Query[Q];
        for (int i = 0; i < Q; i++) {
            l = in.nextInt();
            r = in.nextInt();
            queries[i] = new Query(l, r);
            lists.get(l).add(queries[i]);
            lists.get(r).add(queries[i]);
        }
        solve(1, N);
        for (Query query : queries) {
            out.println(query.weight);
        }
        out.close();
    }

    public static long solve(int l, int r) {
        if (l == r) {
            for (int i = 0; i < lists.get(l).size(); i++) {
                lists.get(l).get(i).weight = max(v[l], lists.get(l).get(i).weight);
            }
            return v[l];
        }
        int mid = (l + r) >> 1;
        long lRes = solve(l, mid);
        long rRes = solve(mid + 1, r);
        long minTmp = Long.MAX_VALUE;
        for (int i = mid - 1; i >= l - 1; i--) {
            minTmp = Math.min(sum[i], minTmp);
            min_max[i] = minTmp;
        }
        long maxTmp = Long.MIN_VALUE;
        for (int i = mid + 1; i <= r; i++) {
            maxTmp = Math.max(sum[i], maxTmp);
            min_max[i] = maxTmp;
        }
        long best = Math.max(Math.max(lRes, rRes), min_max[r] - min_max[l - 1]);
        for (int i = l; i <= r; i++) {
            for (Query query : lists.get(i)) {
                int ql = query.l;
                int qr = query.r;
                long across;
                if (l < ql && ql <= mid && r <= qr) {
                    across = min_max[r] - min_max[ql - 1];
                    query.weight = max(max(rRes, across), query.weight);
                } else if ((l == ql && r <= qr) || (ql <= l && qr == r)) {
                    query.weight = max(best, query.weight);
                } else if (ql <= l && mid < qr && qr < r) {
                    across = min_max[qr] - min_max[l - 1];
                    query.weight = max(max(lRes, across), query.weight);
                } else if (ql <= mid && mid < qr) {
                    across = min_max[qr] - min_max[ql - 1];
                    query.weight = max(across, query.weight);
                }
            }
        }
        return best;
    }

    static class Query {
        long weight;
        int l;
        int r;

        public Query(int l, int r) {
            this.l = l;
            this.r = r;
            this.weight = Long.MIN_VALUE;
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
