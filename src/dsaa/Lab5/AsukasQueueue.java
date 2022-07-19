package dsaa.Lab5;

import java.io.*;
import java.util.StringTokenizer;

public class AsukasQueueue {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        Queueue queueue = new Queueue();
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int opcode = in.nextInt();
            if (opcode == 1) {
                long x = in.nextLong();
                long q = in.nextLong();
                queueue.push(q, x);
            } else {
                long y = in.nextLong();
                out.println(queueue.pop(y));
            }
        }
        out.close();
    }

    static class Queueue {
        static int DEFAULT_CAPACITY = 100005;

        private int front;
        private int rear;
        long[] data;
        long[] cnt;
        int size;

        public Queueue() {
            data = new long[DEFAULT_CAPACITY];
            cnt = new long[DEFAULT_CAPACITY];
            front = 0;
            rear = 0;
            size = 0;
        }

        public void push(long val, long num) {
            if (isFull()) return;
            if (notEmpty() && val == data[(rear - 1 + DEFAULT_CAPACITY) % DEFAULT_CAPACITY]) {
                cnt[(rear - 1 + DEFAULT_CAPACITY) % DEFAULT_CAPACITY] += num;
                return;
            }
            data[rear] = val;
            cnt[rear] = num;
            rear = (rear + 1) % DEFAULT_CAPACITY;
        }

        public long pop(long num) {
            long res = 0L;
            while (num > 0 && notEmpty()) {
                if (cnt[front] > num) {
                    res += num * data[front];
                    cnt[front] -= num;
                    return res;
                } else {
                    res += cnt[front] * data[front];
                    num -= cnt[front];
                    front = (front + 1) % DEFAULT_CAPACITY;
                }
            }
            return res;
        }

        public boolean notEmpty() {
            return front != rear;
        }

        public boolean isFull() {
            return (rear + 1) % DEFAULT_CAPACITY == front;
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
