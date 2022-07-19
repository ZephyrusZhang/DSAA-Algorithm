package dsaa.Lab6;

import java.io.*;
import java.util.StringTokenizer;

public class StringProblem3 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        String str = in.next();

        if (n % 2 == 0) {
            out.println("NOT POSSIBLE");
            out.close();
            return;
        }

        String left;
        String right;
        int cnt = 0;

        //region Assume the inserted character is at left part
        left = str.substring(0, (n - 1) / 2 + 1);
        right = str.substring((n - 1) / 2 + 1);
        int firstDifferentIndex_L = -1;
        for (int i = 0; i < right.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                firstDifferentIndex_L = i;
                break;
            }
        }
        if (firstDifferentIndex_L == -1) {
            if (right.equals(left.substring(0, left.length() - 1))) {
                firstDifferentIndex_L = left.length() - 1;
                cnt++;
            }
        } else if (firstDifferentIndex_L == 0) {
            if (right.equals(left.substring(1))) {
                cnt++;
            } else {
                firstDifferentIndex_L = -1;
            }
        } else {
            if (right.equals(left.substring(0, firstDifferentIndex_L) + left.substring(firstDifferentIndex_L + 1))) {
                cnt++;
            } else {
                firstDifferentIndex_L = -1;
            }
        }
        //endregion

        //region Assume the inserted character is at right part
        left = str.substring(0, (n - 1) / 2);
        right = str.substring((n - 1) / 2);
        int firstDifferentIndex_R = -1;
        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                firstDifferentIndex_R = i;
                break;
            }
        }
        if (firstDifferentIndex_R == -1) {
            if (left.equals(right.substring(0, right.length() - 1))) {
                firstDifferentIndex_R = right.length() - 1;
                cnt++;
            }
        } else if (firstDifferentIndex_R != 0){
            if (left.equals(right.substring(0, firstDifferentIndex_R) + right.substring(firstDifferentIndex_R + 1))) {
                cnt++;
            } else {
                firstDifferentIndex_R = -1;
            }
        } else {
            firstDifferentIndex_R = -1;
        }
        //endregion

        if (cnt > 1) {
            out.println("NOT UNIQUE");
        } else if (cnt == 1) {
            if (firstDifferentIndex_R != -1) {
                out.println(str.substring(0, (n - 1) / 2));
            } else if (firstDifferentIndex_L != - 1) {
                out.println(str.substring((n - 1) / 2 + 1));
            }
        } else {
            out.println("NOT POSSIBLE");
        }

        out.close();
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
