package dsaa.Lab4;

import java.io.*;
import java.util.StringTokenizer;

public class LinkedList2 {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int k = in.nextInt();
        Node head = new Node(-1, -114514, null, null);
        Node tail = head;
        for (int i = 0; i < n; i++) {
            Node temp = new Node(i, in.nextInt(), null, null);
            tail.next = temp;
            temp.pre = tail;
            tail = tail.next;
        }
        int[] studentsGroupID = new int[n];
        int groupedCnt = 0;
        int curGroupID = 1;
        while (groupedCnt < n) {
            Node maxNode = findMax(head);
            studentsGroupID[maxNode.index] = curGroupID;
            groupedCnt++;
            Node lCur = maxNode.pre;
            Node rCur = maxNode.next;
            int lCnt = 0;
            int rCnt = 0;
            while (lCnt < k && lCur != head) {
                studentsGroupID[lCur.index] = curGroupID;
                lCur = lCur.pre;
                lCnt++;
                groupedCnt++;
            }
            while (rCnt < k && rCur != null) {
                studentsGroupID[rCur.index] = curGroupID;
                rCur = rCur.next;
                rCnt++;
                groupedCnt++;
            }
            lCur.next = rCur;
            if (rCur != null) {
                rCur.pre = lCur;
            }
            curGroupID = curGroupID == 1 ? 2 : 1;
        }
        for (int i = 0; i < n; i++)
            out.print(studentsGroupID[i]);
        out.close();
    }

    public static Node findMax(Node head) {
        Node maxNode = head;
        Node cur = head;
        while (cur != null) {
            if (cur.programmingSkill > maxNode.programmingSkill) {
                maxNode = cur;
            }
            cur = cur.next;
        }
        return maxNode;
    }

    private static class Node {
        public int programmingSkill;
        public int index;
        public Node pre;
        public Node next;

        public Node(int index, int programmingSkill, Node pre, Node next) {
            this.index = index;
            this.programmingSkill = programmingSkill;
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
