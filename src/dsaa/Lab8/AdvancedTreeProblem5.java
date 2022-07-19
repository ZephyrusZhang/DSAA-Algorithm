package dsaa.Lab8;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Math.min;

public class AdvancedTreeProblem5 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final Heap heap = new Heap();
    private static final long[] marks = new long[200005];
    private static final ArrayList<ArrayList<Interval>> lists = new ArrayList<>(200005);

    static {
        for (int i = 0; i < 200005; i++) {
            lists.add(new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        final int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();
            int x = in.nextInt();
            long[] array = new long[n + 1];
            for (ArrayList<Interval> list : lists) {
                list.clear();
            }
            long lowerBound = Long.MAX_VALUE;
            long upperBound;
            for (int j = 1; j <= n; j++) {
                array[j] = in.nextLong();
                lowerBound = min(lowerBound, array[j]);
            }
            upperBound = lowerBound + (long) k * x;
            for (int j = 0; j < m; j++) {
                int l = in.nextInt();
                int r = in.nextInt();
                lists.get(l).add(new Interval(l, r));
            }
            while (upperBound - lowerBound > 1) {
                Arrays.fill(marks, 0L);
                long mid = (lowerBound + upperBound) >> 1;
                if (solve(Arrays.copyOf(array, array.length), mid, x, k)) {
                    lowerBound = mid;
                } else {
                    upperBound = mid - 1;
                }
            }
            Arrays.fill(marks, 0L);
            if (solve(array, upperBound, x, k)) {
                out.println(upperBound);
            } else {
                out.println(lowerBound);
            }
        }
        out.close();
    }

    public static boolean solve(long[] array, long y, long x, int k) {
        heap.clear();
        long mark = 0;
        int cnt = 0;
        for (int i = 1; i < array.length; i++) {
            if (lists.get(i).size() != 0) {
                for (Interval interval : lists.get(i)) {
                    heap.insert(interval);
                }
            }
            mark += marks[i];
            array[i] += mark;
            while (array[i] < y) {
                Interval temp;
                if (heap.isEmpty()) {
                    return false;
                } else {
                    temp = heap.delete();
                    cnt++;
                }
                if (temp.r < i) {
                    return false;
                } else {
                    array[i] += x;
                    mark += x;
                    marks[temp.r + 1] -= x;
                }
            }
        }
        return cnt <= k;
    }

    static class Interval implements Comparable<Interval> {
        int l;
        int r;

        public Interval(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public int compareTo(Interval o) {
            return Integer.compare(this.r, o.r);
        }
    }

    //region Heap
    static class Heap {
        private int size;
        private final Interval[] array;

        public Heap() {
            array = new Interval[200005];
            size = 0;
        }

        public void insert(Interval interval) {
            array[size] = interval;
            size++;
            int idx = size - 1;
            Interval temp;
            while (hasParent(idx) && array[(idx - 1) >> 1].compareTo(array[idx]) < 0) {
                temp = array[idx];
                array[idx] = array[(idx - 1) >> 1];
                array[(idx - 1) >> 1] = temp;
                idx = (idx - 1) >> 1;
            }
        }

        public Interval delete() {
            Interval res = array[0];
            array[0] = array[--size];
            int idx = 0;
            Interval temp;
            while (hasLeftChild(idx)) {
                int smallerChildIdx = 2 * idx + 1;
                if (hasRightChild(idx) && array[2 * idx + 2].compareTo(array[2 * idx + 1]) > 0) {
                    smallerChildIdx = 2 * idx + 2;
                }
                if (array[idx].compareTo(array[smallerChildIdx]) < 0) {
                    temp = array[idx];
                    array[idx] = array[smallerChildIdx];
                    array[smallerChildIdx] = temp;
                } else {
                    break;
                }
                idx = smallerChildIdx;
            }
            return res;
        }

        public void clear() {
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private boolean hasParent(int idx) {
            return (idx - 1) >> 1 >= 0;
        }

        private boolean hasLeftChild(int idx) {
            return (2 * idx + 1) < size;
        }

        private boolean hasRightChild(int idx) {
            return (2 * idx + 2) < size;
        }
    }
    //endregion

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
