package dsaa.Lab8;

import java.io.*;
import java.util.StringTokenizer;

public class AdvancedTreeProblem3 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        final int Q = in.nextInt();
        MedianHeap median = new MedianHeap();
        long[] nums = new long[200005];
        int idx = 0;
        long constant = 0L;
        for (int i = 0; i < Q; i++) {
            int opcode = in.nextInt();
            switch (opcode) {
                case 1 -> {
                    long a = in.nextLong();
                    long b = in.nextLong();
                    median.insert(a);
                    nums[idx++] = a;
                    constant += b;
                }
                case 2 -> {
                    long x = median.median();
                    out.println(x + " " + fx(nums, constant, x, idx));
                }
            }
        }
        out.close();
    }

    public static long fx(long[] points, long constant, long x, int size) {
        long res = 0L;
        for (int i = 0; i < size; i++) {
            res += Math.abs(x - points[i]);
        }
        return res + constant;
    }

    static class MedianHeap {
        private final Heap maxHeap;
        private final Heap minHeap;
        int size;

        public MedianHeap() {
            maxHeap = new Heap(-1);
            minHeap = new Heap(1);
            size = 0;
        }

        public void insert(long num) {
            size++;
            if (size == 0) {
                maxHeap.offer(num);
            } else if (maxHeap.size == minHeap.size) {
                if (num >= maxHeap.peek()) {
                    minHeap.offer(num);
                    maxHeap.offer(minHeap.poll());
                } else {
                    maxHeap.offer(num);
                }
            } else {
                if (num >= maxHeap.peek()) {
                    minHeap.offer(num);
                } else {
                    maxHeap.offer(num);
                    minHeap.offer(maxHeap.poll());
                }
            }
        }

        public long median() {
            return maxHeap.peek();
        }
    }

    static class Heap {
        private int size;
        private final int type;
        private final long[] array;

        /**
         * @param type 1 for min heap; -1 for max heap
         */
        public Heap(int type) {
            this.type = type;
            array = new long[200005];
            size = 0;
        }

        public void offer(long val) {
            array[size] = val;
            size++;
            int idx = size - 1;
            long temp;
            while (hasParent(idx) && (array[(idx - 1) >> 1] - array[idx]) * type > 0) {
                temp = array[idx];
                array[idx] = array[(idx - 1) >> 1];
                array[(idx - 1) >> 1] = temp;
                idx = (idx - 1) >> 1;
            }
        }

        public long poll() {
            long res = array[0];
            array[0] = array[--size];
            int idx = 0;
            long temp;
            while (hasLeftChild(idx)) {
                int swapChildIdx = 2 * idx + 1;
                if (hasRightChild(idx) && (array[2 * idx + 2] - array[2 * idx + 1]) * type < 0) {
                    swapChildIdx = 2 * idx + 2;
                }
                if ((array[idx] - array[swapChildIdx]) * type > 0) {
                    temp = array[idx];
                    array[idx] = array[swapChildIdx];
                    array[swapChildIdx] = temp;
                } else {
                    break;
                }
                idx = swapChildIdx;
            }
            return res;
        }

        public long peek() {
            return array[0];
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
