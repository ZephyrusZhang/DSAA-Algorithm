package dsaa.Lab8;

import java.io.*;
import java.util.StringTokenizer;

public class AdvancedTreeProblem2 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        BST bst = new BST();
        final int Q = in.nextInt();
        for (int i = 0; i < Q; i++) {
            int opt = in.nextInt();
            switch (opt) {
                case 1 -> bst.insert(in.nextInt());
                case 2 -> bst.get(in.nextInt());
                case 3 -> bst.inOrder();
            }
        }
        bst.preOrder();
        out.close();
    }

    static class BST {
        Node root;

        public void insert(int val) {
            if (root == null) {
                root = new Node(val);
                return;
            }
            Node cur = root;
            Node parent;
            while (true) {
                parent = cur;
                if (val < parent.val) {
                    cur = parent.lChild;
                    if (cur == null) {
                        parent.lChild = new Node(val);
                        return;
                    }
                } else if (val > parent.val) {
                    cur = parent.rChild;
                    if (cur == null) {
                        parent.rChild = new Node(val);
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        private int cnt = 0;

        public void get(int idx) {
            get(idx, root);
            cnt = 0;
        }

        public void get(int idx, Node root) {
            if (root == null) {
                return;
            }
            get(idx, root.lChild);
            cnt++;
            if (cnt == idx) {
                out.println(root.val);
                return;
            }
            get(idx, root.rChild);
        }

        public void inOrder() {
            inOrder(root);
            out.println("");
        }

        public void inOrder(Node root) {
            if (root == null) {
                return;
            }
            inOrder(root.lChild);
            out.print(root.val + " ");
            inOrder(root.rChild);
        }

        public void preOrder() {
            preOrder(root);
            out.println("");
        }

        public void preOrder(Node root) {
            if (root == null) {
                return;
            }
            out.print(root.val + " ");
            preOrder(root.lChild);
            preOrder(root.rChild);
        }

        static class Node {
            int val;
            Node lChild;
            Node rChild;

            public Node(int val) {
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
