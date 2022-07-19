package algorithm.Lab5;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class LRUCache {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int N = in.nextInt();
        int M = in.nextInt();
        Cache cache = new Cache(N);
        for (int i = 0; i < M; i++) {
            String opcode = in.next();
            if (opcode.equals("get")) {
                out.println(cache.get(in.nextInt()));
            } else {
                cache.put(in.nextInt(), in.nextInt());
            }
        }
        out.close();
    }

    static class Cache {
        int capacity;
        HashMap<Integer, Node> cache;
        int size;
        Node head;
        Node tail;

        public Cache(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.nxt = tail;
            tail.pre = head;
        }

        public int get(int key) {
            if (cache.containsKey(key)) {
                Node cur = cache.get(key);
                cur.nxt.pre = cur.pre;
                cur.pre.nxt = cur.nxt;
                cur.pre = head;
                cur.nxt = head.nxt;
                head.nxt = cur;
                cur.nxt.pre = cur;
                return cur.val;
            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (!cache.containsKey(key)) {
                Node cur = new Node(key, value);
                cur.pre = head;
                cur.nxt = head.nxt;
                head.nxt = cur;
                cur.nxt.pre = cur;
                cache.put(key, cur);
                size++;
                if (size > capacity) {
                    cache.remove(tail.pre.key);
                    tail.pre = tail.pre.pre;
                    tail.pre.nxt = tail;
                    size--;
                }
            } else {
                Node cur = cache.get(key);
                cur.val = value;
                cur.nxt.pre = cur.pre;
                cur.pre.nxt = cur.nxt;
                cur.pre = head;
                cur.nxt = head.nxt;
                head.nxt = cur;
                cur.nxt.pre = cur;
            }
        }

        static class Node {
            Node pre;
            Node nxt;
            int key;
            int val;

            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
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

        public void hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }

    static class QWriter implements Closeable {
        private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

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
