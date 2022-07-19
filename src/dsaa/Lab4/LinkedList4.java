package dsaa.Lab4;

import java.io.*;
import java.util.StringTokenizer;

public class LinkedList4 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        Node[] mat = new Node[n + 1];
        for (int i = 1; i <= n; i++)
            mat[i] = new Node(-114514, null);
        for (int i = 1; i <= n; i++) {
            Node tail = mat[i];
            for (int j = 0; j < m; j++) {
                tail.next = new Node(in.nextInt(), null);
                tail = tail.next;
            }
        }

        for (int x = 0; x < q; x++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int h = in.nextInt();
            int w = in.nextInt();

            for (int i = 0; i < h; i++) {
                Node cur = mat[a + i];
                for (int j = 0; j < b - 1; j++) {
                    cur = cur.next;
                }
                Node leftPreA = cur;
                for (int j = 0; j < w; j++) {
                    cur = cur.next;
                }
                Node rightA = cur;

                cur = mat[c + i];
                for (int j = 0; j < d - 1; j++) {
                    cur = cur.next;
                }
                Node leftPreB = cur;
                for (int j = 0; j < w; j++) {
                    cur = cur.next;
                }
                Node rightB = cur;

                Node temp = leftPreA.next;
                leftPreA.next = leftPreB.next;
                leftPreB.next = temp;

                temp = rightA.next;
                rightA.next = rightB.next;
                rightB.next = temp;
            }
        }

        for (int i = 1; i <= n; i++) {
            Node cur = mat[i].next;
            while (cur != null) {
                out.print(cur.value + " ");
                cur = cur.next;
            }
            out.println("");
        }
        out.close();
    }

    static class Node {
        int value;
        Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
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
