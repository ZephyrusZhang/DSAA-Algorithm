package algorithm.Lab8;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class YouCanMakeIt {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static int distinctLen;

    public static void main(String[] args) {
        final int N = in.nextInt();
        final int Q = in.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = in.nextInt();
        }

        //region Discretization
        int[] array = discrete(a);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            map.put(array[i], a[i]);
        }
        //endregion

        //region Build Segment Trees
        Node[] roots = new Node[N + 1];
        for (int i = 0; i <= N; i++) {
            roots[i] = new Node(1, distinctLen);
        }
        roots[0] = init(roots[0]);
        Node preTreeCur = roots[0];
        Node curTreeCur;
        for (int i = 1; i <= N; i++) {
            int element = array[i - 1];
            int curL = 1;
            int curR = distinctLen;
            curTreeCur = roots[i];
            while (curL != curR) {
                assert preTreeCur != null;
                curTreeCur.cnt = preTreeCur.cnt + 1;
                if (element <= ((curL + curR) >> 1)) {
                    curR = (curL + curR) >> 1;
                    curTreeCur.lChild = new Node(curL, curR);
                    curTreeCur.rChild = preTreeCur.rChild;
                    curTreeCur = curTreeCur.lChild;
                    preTreeCur = preTreeCur.lChild;
                } else {
                    curL = ((curL + curR) >> 1) + 1;
                    curTreeCur.rChild = new Node(curL, curR);
                    curTreeCur.lChild = preTreeCur.lChild;
                    curTreeCur = curTreeCur.rChild;
                    preTreeCur = preTreeCur.rChild;
                }
            }
            assert curTreeCur != null;
            assert preTreeCur != null;
            curTreeCur.cnt = preTreeCur.cnt + 1;
            preTreeCur = roots[i];
        }
        //endregion

        //region Query
        int ql, qr, intervalLength, rank, res;
        for (int i = 0; i < Q; i++) {
            ql = in.nextInt();
            qr = in.nextInt();
            curTreeCur = roots[qr];
            preTreeCur = roots[ql - 1];
            intervalLength = qr - ql + 1;
            rank = (1 + intervalLength) >> 1;
            res = query(preTreeCur, curTreeCur, rank);
            out.println(map.get(res));
        }
        //endregion

        out.close();
    }

    public static int query(Node preTree, Node curTree, int rank) {
        if (curTree.l == curTree.r) {
            return curTree.l;
        }
        int lDiff = curTree.lChild.cnt - preTree.lChild.cnt;
        if (rank <= lDiff) return query(preTree.lChild, curTree.lChild, rank);
        else return query(preTree.rChild, curTree.rChild, rank - lDiff);
    }

    public static int[] discrete(int[] array) {
        int[] sortedDistinct = Arrays.stream(array).distinct().sorted().toArray();
        distinctLen = sortedDistinct.length;
        int[] res = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int target = array[i];
            res[i] = binarySearch(sortedDistinct, target) + 1;
        }
        return res;
    }

    public static Node init(Node root) {
        if (root.l == root.r) {
            return null;
        }
        root.lChild = new Node(root.l, (root.l + root.r) >> 1);
        root.rChild = new Node(((root.l + root.r) >> 1) + 1, root.r);
        init(root.lChild);
        init(root.rChild);
        return root;
    }

    public static int binarySearch(int[] array, int key) {
        int l = 0;
        int r = array.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] < key) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    static class Node {
        int l;
        int r;
        int cnt;
        Node lChild;
        Node rChild;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
            this.cnt = 0;
            this.lChild = null;
            this.rChild = null;
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
