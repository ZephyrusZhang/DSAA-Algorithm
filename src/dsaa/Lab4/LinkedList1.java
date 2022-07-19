package dsaa.Lab4;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class LinkedList1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        Node head = new Node(0, null);
        Node tail = head;
        for (int i = 1; i <= n; i++) {
            tail.next = new Node(i, null);
            tail = tail.next;
        }
        tail.next = head.next;
        int outCnt = 0;
        Node pre = head;
        Node cur = head.next;
        while (outCnt < n) {
            int indexCnt = 1;
            while (indexCnt < m) {
                pre = cur;
                cur = cur.next;
                indexCnt++;
            }
            out.print(cur.id + " ");
            pre.next = pre.next.next;
            cur = cur.next;
            outCnt++;
        }
        out.close();
    }

    private static class Node {
        public Node next;
        public int id;

        public Node(int id, Node next) {
            this.id = id;
            this.next = next;
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
