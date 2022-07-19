package dsaa.Lab4;

import java.io.*;
import java.util.StringTokenizer;

public class LinkedList4Optimized {
    public static QReader in = new QReader();
    public static QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        Node[] mat = new Node[n + 1];
        for (int i = 0; i < n + 1; i++) {
            mat[i] = new Node(-114514, null, null);
        }
        for (int i = 0; i < n; i++) {
            mat[i].ver_next = mat[i + 1];
        }
        Node[] ver_pres = new Node[m];
        Node tail = mat[0];
        for (int i = 0; i < m; i++) {
            ver_pres[i] = new Node(-114514, null, null);
            tail.hor_next = ver_pres[i];
            tail = tail.hor_next;
        }
        for (int i = 1; i <= n; i++) {
            Node hor_tail = mat[i];
            for (int j = 0; j < m; j++) {
                hor_tail.hor_next = new Node(in.nextInt(), null, null);
                hor_tail = hor_tail.hor_next;
                ver_pres[j].ver_next = hor_tail;
                ver_pres[j] = hor_tail;
            }
        }

        for (int x = 0; x < q; x++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int h = in.nextInt();
            int w = in.nextInt();

            Node upperLeftA = mat[0];
            for (int i = 0; i < a - 1; i++) {
                upperLeftA = upperLeftA.ver_next;
            }
            for (int i = 0; i < b - 1; i++) {
                upperLeftA = upperLeftA.hor_next;
            }
            Node upperLeftB = mat[0];
            for (int i = 0; i < c - 1; i++) {
                upperLeftB = upperLeftB.ver_next;
            }
            for (int i = 0; i < d - 1; i++) {
                upperLeftB = upperLeftB.hor_next;
            }

            Node curA = upperLeftA;
            Node curB = upperLeftB;
            Node temp;
            for (int i = 0; i < w; i++) {
                curA = curA.hor_next;
                curB = curB.hor_next;
                temp = curA.ver_next;
                curA.ver_next = curB.ver_next;
                curB.ver_next = temp;
            }

            for (int i = 0; i < h; i++) {
                curA = curA.ver_next;
                curB = curB.ver_next;
                temp = curA.hor_next;
                curA.hor_next = curB.hor_next;
                curB.hor_next = temp;
            }

            curA = upperLeftA;
            curB = upperLeftB;
            for (int i = 0; i < h; i++) {
                curA = curA.ver_next;
                curB = curB.ver_next;
                temp = curA.hor_next;
                curA.hor_next = curB.hor_next;
                curB.hor_next = temp;
            }

            for (int i = 0; i < w; i++) {
                curA = curA.hor_next;
                curB = curB.hor_next;
                temp = curA.ver_next;
                curA.ver_next = curB.ver_next;
                curB.ver_next = temp;
            }
        }

        Node ptr = mat[1].hor_next;
        for (int i = 0; i < n; i++) {
            Node cur = ptr;
            for (int j = 0; j < m; j++) {
                out.print(cur.val + " ");
                cur = cur.hor_next;
            }
            out.println("");
            ptr = ptr.ver_next;
        }
        out.close();
    }

    static class Node {
        int val;
        Node ver_next;
        Node hor_next;

        public Node(int val, Node ver_next, Node hor_next) {
            this.val = val;
            this.ver_next = ver_next;
            this.hor_next = hor_next;
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
