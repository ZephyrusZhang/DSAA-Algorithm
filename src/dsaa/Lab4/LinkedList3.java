package dsaa.Lab4;

import java.io.*;
import java.util.StringTokenizer;

public class LinkedList3 {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int q = in.nextInt();
        Node[] cars = new Node[n + 1];
        for (int i = 1; i <= n; i++)
            cars[i] = new Node(i, null, null);
        for (int i = 0; i < q; i++) {
            int command = in.nextInt();
            if (command == 1) {
                int x = in.nextInt();
                int y = in.nextInt();
                cars[x].next = cars[y];
                cars[y].pre = cars[x];
            } else if (command == 2) {
                cars[in.nextInt()].next = null;
                cars[in.nextInt()].pre = null;
            } else {
                Node head = cars[in.nextInt()];
                while (head.pre != null)
                    head = head.pre;
                Node cur = head;
                int num = 0;
                while (cur != null) {
                    num++;
                    cur = cur.next;
                }
                out.print(num + " ");
                cur = head;
                while (cur != null) {
                    out.print(cur.carID + " ");
                    cur = cur.next;
                }
                out.println("");
            }
        }
        out.close();
    }

    private static class Node {
        public int carID;
        public Node pre;
        public Node next;

        public Node(int carID, Node pre, Node next) {
            this.carID = carID;
            this.pre = pre;
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
