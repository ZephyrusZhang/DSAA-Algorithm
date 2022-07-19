package dsaa.Lab8;

import java.io.*;
import java.util.StringTokenizer;

public class AdvancedTreeProblem1 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        final int Q = in.nextInt();
        Heap heap = new Heap();
        for (int i = 0; i < Q; i++) {
            int opt = in.nextInt();
            switch (opt) {
                case 1 -> heap.insert(in.nextInt());
                case 2 -> out.println(heap.peek());
                case 3 -> heap.delete();
            }
        }
        out.close();
    }

    static class Heap {
        private int size;
        private final int[] array;

        public Heap() {
            array = new int[1000005];
            size = 0;
        }

        public void insert(int val) {
            array[size] = val;
            size++;
            int idx = size - 1;
            int temp;
            while (hasParent(idx) && array[(idx - 1) >> 1] > array[idx]) {
                temp = array[idx];
                array[idx] = array[(idx - 1) >> 1];
                array[(idx - 1) >> 1] = temp;
                idx = (idx - 1) >> 1;
            }
        }

        public void delete() {
            array[0] = array[--size];
            int idx = 0;
            int temp;
            while (hasLeftChild(idx)) {
                int smallerChildIdx = 2 * idx + 1;
                if (hasRightChild(idx) && array[2 * idx + 2] < array[2 * idx + 1]) {
                    smallerChildIdx = 2 * idx + 2;
                }
                if (array[idx] > array[smallerChildIdx]) {
                    temp = array[idx];
                    array[idx] = array[smallerChildIdx];
                    array[smallerChildIdx] = temp;
                } else {
                    break;
                }
                idx = smallerChildIdx;
            }
        }

        public int peek() {
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
