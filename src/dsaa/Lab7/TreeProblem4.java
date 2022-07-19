package dsaa.Lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TreeProblem4 {
    private static final QReader in = new QReader();

    public static void main(String[] args) {
        int n = in.nextInt();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        ArrayList<Node> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Node node = new Node(in.nextInt());
            priorityQueue.offer(node);
            leaves.add(node);
        }
        Node head = huffman(priorityQueue);
        int levelCnt = 0;
        Queue<Node> queue = new LinkedList<>();
        head.level = levelCnt;
        queue.offer(head);

        //region BFS
        while (!queue.isEmpty()) {
            int size = queue.size();
            levelCnt++;
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                assert cur != null;
                if (cur.lChild != null) {
                    cur.lChild.level = levelCnt;
                    queue.offer(cur.lChild);
                }
                if (cur.rChild != null) {
                    cur.rChild.level = levelCnt;
                    queue.offer(cur.rChild);
                }
            }
        }
        //endregion

        long minLength = 0L;
        long longestMinCodeLength = Long.MIN_VALUE;
        for (Node leaf : leaves) {
            minLength += (leaf.weight * leaf.level);
            longestMinCodeLength = Math.max(longestMinCodeLength, leaf.level);
        }
        System.out.println(minLength + "\n" + longestMinCodeLength);
    }

    public static Node huffman(PriorityQueue<Node> priorityQueue) {
        while (priorityQueue.size() >= 2) {
            Node lChild = priorityQueue.poll();
            Node rChild = priorityQueue.poll();
            assert rChild != null;
            Node father = new Node(lChild.weight + rChild.weight);
            father.lChild = lChild;
            father.rChild = rChild;
            priorityQueue.offer(father);
        }
        return priorityQueue.poll();
    }

    static class Node implements Comparable<Node> {
        long weight;
        int level;
        Node lChild;
        Node rChild;

        public Node() {
            new Node();
        }

        public Node(long weight) {
            this.weight = weight;
            this.level = 0;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.weight, o.weight);
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

}
