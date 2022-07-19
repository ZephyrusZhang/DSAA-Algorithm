package dsaa.Lab7;

import java.io.*;
import java.util.StringTokenizer;

// The algorithm is referenced from https://blog.csdn.net/qq_41888422/article/details/100762487

public class TreeProblem1 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static String inOrder;
    private static String postOrder;
    private static int n;

    public static void main(String[] args) {
        inOrder = in.next();
        postOrder = in.next();
        n = inOrder.length();
        preOrder(n - 1, 0, n - 1);
        out.close();
    }

    public static void preOrder(int curTreeRootIndex, int curTreeStart, int curTreeEnd) {
        if (!(curTreeStart <= curTreeEnd)) {
            return;
        }
        int rootIndex_Inorder = 0;
        for (int i = 0; i < n; i++) {
            if (inOrder.charAt(i) == postOrder.charAt(curTreeRootIndex)) {
                rootIndex_Inorder = i;
                break;
            }
        }
        out.print(postOrder.charAt(curTreeRootIndex));
        preOrder(curTreeRootIndex - (curTreeEnd - rootIndex_Inorder + 1), curTreeStart, rootIndex_Inorder - 1);
        preOrder(curTreeRootIndex - 1, rootIndex_Inorder + 1, curTreeEnd);
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
