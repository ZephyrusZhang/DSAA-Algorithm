package dsaa.Lab5;

import java.io.*;
import java.util.StringTokenizer;

// The algorithm is reference to https://blog.csdn.net/pengwill97/article/details/79595698

public class AsukasConcert {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        Stack stack = new Stack();
        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = in.nextInt();
        }
        long pairsCnt = 0L;
        for (int height : heights) {
            int tempCnt = 0;
            while (stack.notEmpty() && stack.peek() <= height) {
                if (stack.peek() == height) tempCnt++;
                pairsCnt++;
                stack.pop();
            }
            if (stack.notEmpty()) pairsCnt++;
            for (int j = 0; j < tempCnt + 1; j++) {
                stack.push(height);
            }
        }
        out.println(pairsCnt);
        out.close();
    }

    static class Stack {
        static final int CAPACITY = 200005;
        int top;
        int[] stack;

        public Stack() {
            top = -1;
            stack = new int[CAPACITY];
        }

        public void push(int val) {
            if (top >= CAPACITY - 1) {
                return;
            }
            stack[++top] = val;
        }

        public void pop() {
            if (top < 0) {
                return;
            }
            top--;
        }

        public int peek() {
            if (top < 0) {
                return -1;
            }
            return stack[top];
        }

        public boolean notEmpty() {
            return top != -1;
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
