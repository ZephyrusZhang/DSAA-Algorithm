package dsaa.Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ElegantSong {
    private static final QReader in = new QReader();

    public static void main(String[] args) {
        int t = in.nextInt();
        boolean[] res = new boolean[t];
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            int l1 = in.nextInt();
            int r1 = in.nextInt();
            int l2 = in.nextInt();
            int r2 = in.nextInt();
            if (r2 < l1) {
                int tmp = l2;
                l2 = l1;
                l1 = tmp;
                tmp = r2;
                r2 = r1;
                r1 = tmp;
            }
            int gap = l2 - r1;
            int dis_1 = r1 - l1;
            int dis_2 = r2 - l2;
            int[] tones = new int[n];
            for (int j = 0; j < n; ++j) {
                tones[j] = in.nextInt();
            }

            Arrays.sort(tones);

            if (tones[n - 1] - tones[0] > r2 - l1) {
                res[i] = false;
            } else if (tones[n - 1] - tones[0] <= dis_1 || tones[n - 1] - tones[0] <= dis_2) {
                res[i] = true;
            } else {
                boolean val = false;
                for (int j = 0; j < n - 1; j++) {
                    if ((tones[j + 1] - tones[j] >= gap) && (tones[j] - tones[0] <= dis_1) && (tones[n - 1] - tones[j + 1] <= dis_2)) {
                        val = true;
                        break;
                    }
                }
                res[i] = val;
            }
        }

        for (int i = 0; i < t; ++i) {
            if (res[i]) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
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

}
