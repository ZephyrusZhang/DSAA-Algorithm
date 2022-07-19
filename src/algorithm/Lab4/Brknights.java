package algorithm.Lab4;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import static java.lang.Math.log;
import static java.lang.Math.max;

public class Brknights {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static final long MOD = 1000000007L;

    public static void main(String[] args) {
        final int N = in.nextInt();
        final int M = in.nextInt();
        final int C = in.nextInt();
        Node[] nodes = new Node[N + 1];
        for (int i = 1; i <= N; i++) {
            nodes[i] = new Node(i, in.nextLong(), in.nextLong(), in.nextLong(), in.nextLong());
        }
        int[] outDegree = new int[N + 1];
        int[] inDegree = new int[N + 1];
        for (int i = 0; i < M; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].next.add(nodes[y]);
            outDegree[x]++;
            inDegree[y]++;
        }

        Queue<Node> queue = new LinkedList<>();
        if (C >= nodes[1].h) {
            nodes[1].modCE = C;
            nodes[1].modCE = max(nodes[1].modCE + nodes[1].a, max(nodes[1].modCE * nodes[1].b, nodes[1].c));
            if (nodes[1].modCE > 1000000000) {
                nodes[1].isStage2 = true;
                nodes[1].logCE = log(nodes[1].modCE);
                nodes[1].modCE %= MOD;
            }
            queue.offer(nodes[1]);
        }

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (int i = 0; i < cur.next.size(); i++) {
                Node next = cur.next.get(i);
                inDegree[next.id]--;
                if (inDegree[next.id] == 0) {
                    queue.offer(next);
                }
                if ((!cur.isStage2 && cur.modCE < next.h) || (cur.isStage2) && cur.logCE < log(next.h)) continue;
                if (cur.isStage2) {
                    double possibleLogCE = cur.logCE + log(next.b);
                    if (possibleLogCE > next.logCE) {
                        next.logCE = possibleLogCE;
                        next.modCE = cur.modCE * next.b;
                        next.modCE %= MOD;
                        next.isStage2 = true;
                    }
                } else {
                    long a_ce = cur.modCE + next.a;
                    long b_ce = cur.modCE * next.b;
                    long c_ce = next.c;
                    long maxCE = max(a_ce, max(b_ce, c_ce));
                    if (maxCE > 1000000000) {
                        double maxLogCE = log(maxCE);
                        long maxModCE = maxCE % MOD;
                        if (next.isStage2) {
                            if (maxLogCE > next.logCE) {
                                next.logCE = maxLogCE;
                                next.modCE = maxModCE;
                            }
                        } else {
                            next.logCE = maxLogCE;
                            next.modCE = maxModCE;
                            next.isStage2 = true;
                        }
                    } else {
                        if (!next.isStage2) {
                            next.modCE = max(next.modCE, maxCE);
                        }
                    }
                }
            }
        }

        long maxCE_MOD = -1;
        double maxCE_LOG = -1;
        for (int i = 1; i <= N; i++) {
            if (outDegree[i] != 0) continue;
            if (nodes[i].isStage2) {
                if (nodes[i].logCE > maxCE_LOG) {
                    maxCE_LOG = nodes[i].logCE;
                    maxCE_MOD = nodes[i].modCE;
                }
            } else {
                if (log(nodes[i].modCE) > maxCE_LOG) {
                    maxCE_LOG = log(nodes[i].modCE);
                    maxCE_MOD = nodes[i].modCE;
                }
            }
        }
        out.println(maxCE_MOD);
        out.close();
    }

    static class Node {
        int id;
        long modCE;
        double logCE;
        long h;
        long a;
        long b;
        long c;
        ArrayList<Node> next;
        boolean isStage2;

        public Node(int id, long h, long a, long b, long c) {
            this.id = id;
            modCE = -1;
            logCE = -1;
            this.h = h;
            this.a = a;
            this.b = b;
            this.c = c;
            next = new ArrayList<>();
            isStage2 = false;
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

        public long nextLong() {
            return Long.parseLong(next());
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
