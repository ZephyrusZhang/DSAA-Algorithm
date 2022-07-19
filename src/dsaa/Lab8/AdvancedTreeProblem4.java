package dsaa.Lab8;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class AdvancedTreeProblem4 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        AVL avl = new AVL();
        final int Q = in.nextInt();
        for (int i = 0; i < Q; i++) {
            int opcode = in.nextInt();
            int operand = in.nextInt();
            switch (opcode) {
                case 1 -> avl.insert(operand);
                case 2 -> avl.delete(operand);
                case 3 -> out.println(avl.ranking(operand));
                case 4 -> out.println(avl.findKthSmallest(operand));
                case 5 -> out.println(avl.precursor(operand));
                case 6 -> out.println(avl.successor(operand));
            }
        }
        out.close();
    }

    static class AVL {
        private Node root;

        public AVL() {
            this.root = Node.fakeNull;
        }

        public void insert(int data) {
            root = insert(root, data);
        }

        public void delete(int data) {
            root = delete(root, data);
            if (root != Node.fakeNull) {
                root.height = max(root.lChild.height, root.rChild.height) + 1;
                root.subTreeNodesNum = root.lChild.subTreeNodesNum + root.rChild.subTreeNodesNum + 1;
            }
        }

        public int findKthSmallest(int k) {
            return findKthSmallest(root, k);
        }

        public int ranking(int data) {
            return ranking(root, data) + 1;
        }

        public int precursor(int data) {
            return precursor(root, data);
        }

        public int successor(int data) {
            return successor(root, data);
        }

        private int precursor(Node root, int data) {
            if (root == Node.fakeNull) return Integer.MIN_VALUE;
            if (data <= root.weight) return precursor(root.lChild, data);
            else {
                int temp = precursor(root.rChild, data);
                if (temp != Integer.MIN_VALUE) return temp;
                else return root.weight;
            }
        }

        private int successor(Node root, int data) {
            if (root == Node.fakeNull) return Integer.MIN_VALUE;
            if (data >= root.weight) return successor(root.rChild, data);
            else {
                int temp = successor(root.lChild, data);
                if (temp != Integer.MIN_VALUE) return temp;
                else return root.weight;
            }
        }

        private int ranking(Node root, int data) {
            if (root == Node.fakeNull) return 0;
            if (data <= root.weight) return ranking(root.lChild, data);
            else return root.lChild.subTreeNodesNum + 1 + ranking(root.rChild, data);
        }

        private int findKthSmallest(Node root, int k) {
            if (root.lChild.subTreeNodesNum + 1 == k) return root.weight;
            else if (k <= root.lChild.subTreeNodesNum) return findKthSmallest(root.lChild, k);
            else return findKthSmallest(root.rChild, k - root.lChild.subTreeNodesNum - 1);
        }

        private Node delete(Node root, int data) {
            if (root == Node.fakeNull) return Node.fakeNull;
            if (data < root.weight) root.lChild = delete(root.lChild, data);
            else if (data > root.weight) root.rChild = delete(root.rChild, data);
            else {
                if (root.lChild == Node.fakeNull && root.rChild == Node.fakeNull) {
                    return Node.fakeNull;
                } else if (root.lChild != Node.fakeNull && root.rChild != Node.fakeNull) {
                    root.weight = findPre(root);
                    root.lChild = delete(root.lChild, root.weight);
                } else if (root.lChild != Node.fakeNull) {
                    root = root.lChild;
                } else {
                    root = root.rChild;
                }
            }
            if (root != Node.fakeNull) {
                root.height = max(root.lChild.height, root.rChild.height) + 1;
                root.subTreeNodesNum = root.lChild.subTreeNodesNum + root.rChild.subTreeNodesNum + 1;
            }
            return balance(root);
        }

        private int findPre(Node node) {
            Node cur = node.lChild;
            Node precursor = node.lChild;
            while (cur != Node.fakeNull) {
                precursor = cur;
                cur = cur.rChild;
            }
            return precursor.weight;
        }

        private Node insert(Node root, int data) {
            if (root == Node.fakeNull) {
                return new Node(data);
            }
            if (data < root.weight) root.lChild = insert(root.lChild, data);
            else root.rChild = insert(root.rChild, data);
            root.height = max(root.lChild.height, root.rChild.height) + 1;
            root.subTreeNodesNum = root.lChild.subTreeNodesNum + root.rChild.subTreeNodesNum + 1;
            return balance(root);
        }

        private Node balance(Node root) {
            if (root.lChild.height - root.rChild.height > 1) {
                if (root.lChild.lChild.height >= root.lChild.rChild.height) {
                    root = rotateRight(root);
                } else {
                    root = rotateLeftRight(root);
                }
            } else if (root.rChild.height - root.lChild.height > 1) {
                if (root.rChild.rChild.height >= root.rChild.lChild.height) {
                    root = rotateLeft(root);
                } else {
                    root = rotateRightLeft(root);
                }
            }
            return root;
        }

        private Node rotateLeft(Node node) {
            Node rChild = node.rChild;
            node.rChild = rChild.lChild;
            rChild.lChild = node;
            node.height = max(node.lChild.height, node.rChild.height) + 1;
            node.subTreeNodesNum = node.lChild.subTreeNodesNum + node.rChild.subTreeNodesNum + 1;
            rChild.height = max(rChild.lChild.height, rChild.rChild.height) + 1;
            rChild.subTreeNodesNum = rChild.lChild.subTreeNodesNum + rChild.rChild.subTreeNodesNum + 1;
            return rChild;
        }

        private Node rotateRight(Node node) {
            Node lChild = node.lChild;
            node.lChild = lChild.rChild;
            lChild.rChild = node;
            node.height = max(node.lChild.height, node.rChild.height) + 1;
            node.subTreeNodesNum = node.lChild.subTreeNodesNum + node.rChild.subTreeNodesNum + 1;
            lChild.height = max(lChild.lChild.height, lChild.rChild.height) + 1;
            lChild.subTreeNodesNum = lChild.lChild.subTreeNodesNum + lChild.rChild.subTreeNodesNum + 1;
            return lChild;
        }

        private Node rotateLeftRight(Node node) {
            node.lChild = rotateLeft(node.lChild);
            return rotateRight(node);
        }

        private Node rotateRightLeft(Node node) {
            node.rChild = rotateRight(node.rChild);
            return rotateLeft(node);
        }

        private static class Node {
            int weight;
            int subTreeNodesNum;
            int height;
            Node lChild;
            Node rChild;
            static Node fakeNull;

            static {
                fakeNull = new Node();
                fakeNull.lChild = fakeNull;
                fakeNull.rChild = fakeNull;
            }

            public Node() {
                this.weight = Integer.MIN_VALUE;
                this.subTreeNodesNum = 0;
                this.height = 0;
            }

            public Node(int weight) {
                this.weight = weight;
                this.subTreeNodesNum = 1;
                this.height = 1;
                this.lChild = fakeNull;
                this.rChild = fakeNull;
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
