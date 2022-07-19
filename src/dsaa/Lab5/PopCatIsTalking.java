package dsaa.Lab5;

import java.io.*;
import java.util.StringTokenizer;

public class PopCatIsTalking {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final Stack stack = new Stack();

    public static void main(String[] args) {
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            judge(in.next());
        }
        out.close();
    }

    public static void judge(String str) {
        stack.clear();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.pop() != '(') {
                    out.println("N0");
                    return;
                }
            }
        }
        if (stack.isEmpty()) {
            out.println("YE5");
        } else {
            out.println("N0");
        }
    }

    static class Stack {
        static final int CAPACITY = 150000;
        int top;
        char[] stack;

        public Stack() {
            top = -1;
            stack = new char[CAPACITY];
        }

        public void push(char val) {
            if (top >= CAPACITY - 1) {
                throw new RuntimeException("StackOverflow");
            }
            stack[++top] = val;
        }

        public char pop() {
            if (top < 0) {
                return '@';
            }
            return stack[top--];
        }

        public void clear() {
            top = -1;
        }

        public boolean isEmpty() {
            return top < 0;
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
