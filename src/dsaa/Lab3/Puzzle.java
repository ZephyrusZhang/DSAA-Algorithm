package dsaa.Lab3;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Puzzle {

    public static void main(String[] args) {

        QReader in = new QReader();
        int n = in.nextInt();
        int[][] points = new int[n][2];
        for (int i = 0; i < n; ++i) {
            points[i][0] = in.nextInt();
            points[i][1] = in.nextInt();
        }
        solve(points);
    }

    public static void solve(int[][] coordinates) {

        int size = coordinates.length;
        int y = calcMinRectArea(coordinates, 0, size);

        // sort by x coordinates
        Arrays.sort(coordinates, Comparator.comparingInt(o -> o[0]));

        @SuppressWarnings("SuspiciousNameCombination") int x = y;
        for (int i = 1; i < size; i++) {
            if (coordinates[i][0] == coordinates[i - 1][0])
                continue; // several coordinates with the same x coordinates
            x = Math.min(calcMinRectArea(coordinates, 0, i) + calcMinRectArea(coordinates, i, size - i), x);
        }

        // sort by y coordinates
        Arrays.sort(coordinates, Comparator.comparingInt(o -> o[1]));

        for (int i = 1; i < size; i++) {
            if (coordinates[i][1] == coordinates[i - 1][1])
                continue; // several coordinates with the same y coordinates
            x = Math.min(calcMinRectArea(coordinates, 0, i) + calcMinRectArea(coordinates, i, size - i), x);
        }

        System.out.printf("Y = %d, X = %d, Y - X = %d\n", y, x, y - x);
    }

    private static int calcMinRectArea(int[][] coords, int start, int length) {

        if (length == 0)
            return 0;

        int minX = coords[start][0];
        int maxX = minX;
        int minY = coords[start][1];
        int maxY = minY;

        for (int i = start + 1; i < start + length; i++) {
            int x = coords[i][0];
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            int y = coords[i][1];
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        return (maxX - minX) * (maxY - minY);
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
