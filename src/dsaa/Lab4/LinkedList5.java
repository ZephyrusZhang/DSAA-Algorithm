package dsaa.Lab4;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Math.*;

public class LinkedList5 {
    public static QReader in = new QReader();
    public static QWriter out = new QWriter();

    public static void main(String[] args) {
        String str = in.next();
        double tempSubLen = floor(sqrt(str.length()));
        int subLen = (int) tempSubLen;
        int listsNum = (int) ceil(str.length() / tempSubLen);
        Node[] heads = new Node[listsNum];
        int[] listLen = new int[listsNum];
        for (int i = 0; i < listsNum; i++) {
            heads[i] = new Node('$');
            listLen[i] = subLen;
        }
        listLen[listsNum - 1] = str.length() - (listsNum - 1) * subLen;
        int strIndex = 0;
        for (int i = 0; i < listsNum; i++) {
            Node tail = heads[i];
            int len = 0;
            while (len < listLen[i]) {
                tail.next = new Node(str.charAt(strIndex));
                tail = tail.next;
                strIndex++;
                len++;
            }
        }

        int commandNum = in.nextInt();
        for (int i = 0; i < commandNum; i++) {
            int opcode = in.nextInt();
            if (opcode == 1) {
                Node insertedNode = new Node(in.next().charAt(0));
                int position = in.nextInt();
                int listIndex = 0;
                while (position > listLen[listIndex]) {
                    position -= listLen[listIndex];
                    listIndex++;
                }
                Node cur = heads[listIndex];
                for (int j = 0; j < position - 1; j++) {
                    cur = cur.next;
                }
                Node next = cur.next;
                cur.next = insertedNode;
                insertedNode.next = next;
                listLen[listIndex]++;
            } else if (opcode == 2) {
                int position = in.nextInt();
                int listIndex = 0;
                while (position > listLen[listIndex]) {
                    position -= listLen[listIndex];
                    listIndex++;
                }
                Node cur = heads[listIndex];
                for (int j = 0; j < position; j++) {
                    cur = cur.next;
                }
                out.println(cur.val);
            } else {
                int l = in.nextInt();
                int r = in.nextInt();
                int transformedLen = r - l + 1;
                int listIndex = 0;
                while (l > listLen[listIndex]) {
                    l -= listLen[listIndex];
                    listIndex++;
                }
                Node cur = heads[listIndex];
                for (int j = 0; j < l - 1; j++) {
                    cur = cur.next;
                }
                while (transformedLen > 0) {
                    cur = cur.next;
                    if (cur == null) {
                        listIndex++;
                        cur = heads[listIndex].next;
                    }
                    cur.val = (char) ('a' + 'z' - cur.val);
                    transformedLen--;
                }
            }
        }
        out.close();
    }

    static class Node {
        char val;
        Node next;

        public Node(char val) {
            this.val = val;
            this.next = null;
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
