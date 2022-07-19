package dsaa.Lab5;

import java.io.*;
import java.util.StringTokenizer;

public class AsukasDeque {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        Deque deque = new Deque();
        for (int i = 0; i < n; i++) {
            int opcode = in.nextInt();
            switch (opcode) {
                case 1 -> deque.offerLast();
                case 2 -> deque.pollLast();
                case 3 -> deque.offerFirst();
                case 4 -> deque.pollFirst();
                default -> out.println(deque.data[deque.mid]);
            }
        }
        out.close();
    }

    static class Deque {
        static final int DEFAULT_CAPACITY = 100005;
        static int c = 1;

        int front;
        int rear;
        int mid;
        int[] data;
        int size;

        private final int o = DEFAULT_CAPACITY + 1;

        public Deque() {
            data = new int[DEFAULT_CAPACITY + 1];
            front = 0;
            rear = 0;
            data[rear++] = 1;
            mid = 0;
            size = 1;
        }

        public void offerFirst() {
            if (isFull()) return;
            front = (front + DEFAULT_CAPACITY) % o;
            data[front] = ++c;
            mid = (size % 2 == 1) ? (mid + DEFAULT_CAPACITY) % o : mid;
            size++;
        }

        public void offerLast() {
            if (isFull()) return;
            data[rear] = ++c;
            rear = (rear + 1) % o;
            mid = (size % 2 == 1) ? mid : (mid + 1) % o;
            size++;
        }

        public void pollFirst() {
            if (isEmpty()) return;
            front = (front + 1) % o;
            mid = (size % 2 == 1) ? mid : (mid + 1) % o;
            size--;
        }

        public void pollLast() {
            if (isEmpty()) return;
            rear = (rear + DEFAULT_CAPACITY) % o;
            mid = (size % 2 == 1) ? (mid + DEFAULT_CAPACITY) % o : mid;
            size--;
        }

        public boolean isEmpty() {
            return front == rear;
        }

        public boolean isFull() {
            return (rear + 1) % o == front;
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
