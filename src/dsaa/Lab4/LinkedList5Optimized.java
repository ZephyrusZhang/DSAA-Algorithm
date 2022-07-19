package dsaa.Lab4;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

public class LinkedList5Optimized {
    public static QReader in = new QReader();
    public static QWriter out = new QWriter();

    public static void main(String[] args) {
        String str = in.next();
        int strLen = str.length();
        int blockLen = (int) sqrt(strLen);
        int blockNum = (int) ceil(strLen / (double) blockLen);
        Node head = new Node('$');
        Node blockHead = head;
        Node tail = head;
        int tempBlockIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            Node node = new Node(str.charAt(i));
            if (i % blockLen == 0) {
                blockHead.nextBlock = node;
                blockHead = blockHead.nextBlock;
                blockHead.blockLen = tempBlockIndex != blockNum - 1 ? blockLen : strLen - (blockNum - 1) * blockLen;
                tail = blockHead;
                tempBlockIndex++;
            } else {
                tail.nextNode = node;
                tail = tail.nextNode;
            }
        }

        Node preBlockHeadForInsert;
        Node curBlockHeadForInsert;
        Node curBlockHeadForFind;
        Node curForFind;
        Node curBlockHeadForTransform;
        Node curForTransform;
        Node preForSplit;
        Node curForSpilt;
        Node tempNextNodeForInsert;
        Node tempNextBlockForSplit;

        int n = in.nextInt();
        for (int x = 0; x < n; x++) {
            int opcode = in.nextInt();
            if (opcode == 1) {
                Node insertedNode = new Node(in.next().charAt(0));
                int pos = in.nextInt();
                preBlockHeadForInsert = head;
                curBlockHeadForInsert = head.nextBlock;
                while (pos > curBlockHeadForInsert.blockLen) {
                    pos -= curBlockHeadForInsert.blockLen;
                    preBlockHeadForInsert = curBlockHeadForInsert;
                    curBlockHeadForInsert = curBlockHeadForInsert.nextBlock;
                }
                if (pos == 1) {
                    insertedNode.nextNode = curBlockHeadForInsert;
                    insertedNode.nextBlock = curBlockHeadForInsert.nextBlock;
                    curBlockHeadForInsert.nextBlock = null;
                    preBlockHeadForInsert.nextBlock = insertedNode;
                    insertedNode.blockLen = curBlockHeadForInsert.blockLen + 1;
                    curBlockHeadForInsert.blockLen = 0;
                    curBlockHeadForInsert = insertedNode;
                } else {
                    Node cur = curBlockHeadForInsert;
                    for (int i = 1; i < pos - 1; i++) {
                        cur = cur.nextNode;
                    }
                    tempNextNodeForInsert = cur.nextNode;
                    cur.nextNode = insertedNode;
                    insertedNode.nextNode = tempNextNodeForInsert;
                    curBlockHeadForInsert.blockLen++;
                }
                if (curBlockHeadForInsert.blockLen > 2 * blockLen) {
                    preForSplit = curBlockHeadForInsert;
                    curForSpilt = curBlockHeadForInsert.nextNode;
                    while (curForSpilt.nextNode != null) {
                        preForSplit = curForSpilt;
                        curForSpilt = curForSpilt.nextNode;
                    }
                    preForSplit.nextNode = null;
                    curBlockHeadForInsert.blockLen--;
                    tempNextBlockForSplit = curBlockHeadForInsert.nextBlock;
                    curBlockHeadForInsert.nextBlock = curForSpilt;
                    curForSpilt.nextBlock = tempNextBlockForSplit;
                    curForSpilt.blockLen = 1;
                }
            } else if (opcode == 2) {
                int pos = in.nextInt();
                curBlockHeadForFind = head.nextBlock;
                while (pos > curBlockHeadForFind.blockLen) {
                    pos -= curBlockHeadForFind.blockLen;
                    curBlockHeadForFind = curBlockHeadForFind.nextBlock;
                }
                curForFind = curBlockHeadForFind;
                for (int i = 0; i < pos - 1; i++) {
                    curForFind = curForFind.nextNode;
                }
                out.println(curForFind.val);
            } else {
                int l = in.nextInt();
                int r = in.nextInt();
                int len = r - l + 1;
                curBlockHeadForTransform = head.nextBlock;
                while (l > curBlockHeadForTransform.blockLen) {
                    l -= curBlockHeadForTransform.blockLen;
                    curBlockHeadForTransform = curBlockHeadForTransform.nextBlock;
                }
                curForTransform = curBlockHeadForTransform;
                for (int i = 0; i < l - 1; i++) {
                    curForTransform = curForTransform.nextNode;
                }
                while (len > 0) {
                    curForTransform.val = (char) ('a' + 'z' - curForTransform.val);
                    curForTransform = curForTransform.nextNode;
                    if (curForTransform == null) {
                        curForTransform = curBlockHeadForTransform.nextBlock;
                        curBlockHeadForTransform = curBlockHeadForTransform.nextBlock;
                    }
                    len--;
                }
            }
        }

        out.close();
    }

    static class Node {
        char val;
        int blockLen;
        Node nextNode;
        Node nextBlock;

        public Node(char val) {
            this.val = val;
            this.nextNode = null;
            this.nextBlock = null;
            this.blockLen = 0;
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
