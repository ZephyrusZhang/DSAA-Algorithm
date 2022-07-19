package dsaa.Lab6;

import java.io.*;
import java.util.StringTokenizer;

public class StringProblem5 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        String text = in.next();
        String pattern = in.next();
        int[] next = buildNext(pattern);
        MyStack characters = new MyStack();

        for (int i = 0, j = 0; i < text.length(); i++) {
            characters.push(text.charAt(i));
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                characters.pop(pattern.length());
                j = kmp(characters, pattern, next, characters.size);
            }
        }

        for (int i = 0; i < characters.size; i++) {
            out.print(characters.stack[i]);
        }
        out.close();
    }

    public static int kmp(MyStack text, String pattern, int[] next, int size) {
        int lastMatchingIndex = 0;
        int j = 0;
        for (int i = 0; i < size; i++) {
            while (j > 0 && text.stack[i] != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (text.stack[i] == pattern.charAt(j)) {
                lastMatchingIndex = i;
                j++;
            }
        }
        if (lastMatchingIndex == size - 1 && j != pattern.length()) {
            return j;
        } else {
            return 0;
        }
    }

    public static int[] buildNext(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    static class MyStack {
        private final int DEFAULT_CAPACITY = 1000005;
        private int top;
        final char[] stack;
        int size;

        public MyStack() {
            stack = new char[DEFAULT_CAPACITY];
            top = -1;
            size = 0;
        }

        public void push(char val) {
            if (top >= DEFAULT_CAPACITY - 1) {
                return;
            }
            stack[++top] = val;
            size++;
        }

        public void pop(int num) {
            if (top == -1) {
                return;
            }
            top -= num;
            size -= num;
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
