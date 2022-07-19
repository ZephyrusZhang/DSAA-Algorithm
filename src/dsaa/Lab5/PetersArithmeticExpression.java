package dsaa.Lab5;

import java.io.*;
import java.util.StringTokenizer;

public class PetersArithmeticExpression {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        String infix = in.next();
        Stack operators = new Stack();
        Stack temp = new Stack();
        for (int i = 0; i < infix.length(); i++) {
            char cur = infix.charAt(i);
            if (Character.isAlphabetic(cur)) {
                temp.push(cur);
            } else if (cur == '(') {
                operators.push(cur);
            } else if (cur == ')') {
                while (operators.peek() != '(') {
                    temp.push(operators.pop());
                }
                operators.pop();
            } else {
                while (!operators.isEmpty() && precedence(cur) <= precedence(operators.peek()) && operators.peek() != '(') {
                    temp.push(operators.pop());
                }
                operators.push(cur);
            }
        }
        while (operators.isEmpty()) {
            temp.push(operators.pop());
        }
        for (int i = 0; i <= temp.top; i++) {
            out.print(temp.stack[i]);
        }
        out.close();
    }

    public static int precedence(char c) {
        if (c == '*' || c == '/') {
            return 1;
        } else {
            return 0;
        }
    }

    static class Stack {
        static final int CAPACITY = 150000;
        int top;
        final char[] stack;

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

        public char peek() {
            if (top < 0) {
                return '@';
            }
            return stack[top];
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
