package algorithm.Lab1;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class StableMatching {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        //region Preprocess
        int n = in.nextInt();
        HashMap<String, Integer> menName2Index = new HashMap<>();
        Queue<Integer> freeMenQueue = new LinkedList<>();
        String[] menIndex2Name = new String[n];
        for (int i = 0; i < n; ++i) {
            String menName  = in.next();
            menName2Index.put(menName, i);
            menIndex2Name[i] = menName;
            freeMenQueue.add(i);
        }
        HashMap<String, Integer> womenName2Index = new HashMap<>();
        String[] womenIndex2Name = new String[n];
        for (int i = 0; i < n; ++i) {
            String womenName = in.next();
            womenName2Index.put(womenName, i);
            womenIndex2Name[i] = womenName;
        }
        int[][] menPreferenceList = new int[n][n];
        int[][] womenPreferenceList = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                menPreferenceList[i][j] = womenName2Index.get(in.next());
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                womenPreferenceList[i][j] = menName2Index.get(in.next());
            }
        }
        int[][] womenInverseList = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                womenInverseList[i][womenPreferenceList[i][j]] = j;
            }
        }

        int[] wifeOfMen = new int[n];
        int[] husbandOfWomen = new int[n];
        for (int i = 0; i < n; ++i) {
            wifeOfMen[i] = -1;
            husbandOfWomen[i] = -1;
        }
        int[] countOfMen = new int[n];

        //endregion

        while (!freeMenQueue.isEmpty()) {
            int m = freeMenQueue.poll();
            int w = menPreferenceList[m][countOfMen[m]];
            if (husbandOfWomen[w] == -1) {
                wifeOfMen[m] = w;
                husbandOfWomen[w] = m;
            } else if (womenInverseList[w][m] < womenInverseList[w][husbandOfWomen[w]]) {
                freeMenQueue.add(husbandOfWomen[w]);
                wifeOfMen[m] = w;
                husbandOfWomen[w] = m;
            } else {
                freeMenQueue.add(m);
                countOfMen[m]++;
            }
        }

        for (int i = 0; i < n; ++i) {
            out.println(menIndex2Name[i] + " " + womenIndex2Name[wifeOfMen[i]]);
        }
        out.close();
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
