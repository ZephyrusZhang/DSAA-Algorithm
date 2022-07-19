package dsaa.Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TwoRectangle {
    public static void main(String[] args) {
        QReader in = new QReader();
        int n = in.nextInt();
        int[][] points = new int[n][2];
        for (int i = 0; i < n; ++i) {
            points[i][0] = in.nextInt();
            points[i][1] = in.nextInt();
        }
        System.out.println(solve(points));
    }

    public static long solve(int[][] points) {
        long minRectAreaX = Long.MAX_VALUE;
        long minRectAreaY = Long.MAX_VALUE;
        int n = points.length;

        //region [0, i]U[i+1, n-1] <=> X
        Arrays.sort(points, (o1, o2) -> {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            else
                return o1[0] - o2[0];
        });
        long minYLeft = Integer.MAX_VALUE;
        long maxYLeft = Integer.MIN_VALUE;
        long[] leftRectAreaX = new long[n - 1];
        int leftRectAreaIndexX = 0;
        for (int i = 0; i < n - 1; ++i) {
            minYLeft = Math.min(minYLeft, points[i][1]);
            maxYLeft = Math.max(maxYLeft, points[i][1]);
            if (points[i][0] != points[i + 1][0])
                leftRectAreaX[leftRectAreaIndexX++] = (points[i][0] - points[0][0]) * (maxYLeft - minYLeft);
        }

        long minYRight = Integer.MAX_VALUE;
        long maxYRight = Integer.MIN_VALUE;
        long[] rightRectAreaX = new long[n - 1];
        int rightRectAreaIndexX = leftRectAreaIndexX - 1;
        for (int i = n - 1; i >= 1; --i) {
            minYRight = Math.min(minYRight, points[i][1]);
            maxYRight = Math.max(maxYRight, points[i][1]);
            if (points[i][0] != points[i - 1][0]) {
                rightRectAreaX[rightRectAreaIndexX--] = (points[n - 1][0] - points[i][0]) * (maxYRight - minYRight);
            }
        }

        int areaNumX = leftRectAreaIndexX;
        for (int i = 0; i < areaNumX; ++i) {
            minRectAreaX = Math.min(minRectAreaX, leftRectAreaX[i] + rightRectAreaX[i]);
        }
        //endregion

        //region [0, i]U[i+1, n-1] <=> Y
        Arrays.sort(points, (o1, o2) -> {
            if (o1[1] == o2[1])
                return o1[0] - o2[0];
            else
                return o1[1] - o2[1];
        });

        long minXLower = Integer.MAX_VALUE;
        long maxXLower = Integer.MIN_VALUE;
        long[] lowerRectAreaY = new long[n - 1];
        int lowerRectAreaIndexY = 0;
        for (int i = 0; i < n - 1; ++i) {
            minXLower = Math.min(minXLower, points[i][0]);
            maxXLower = Math.max(maxXLower, points[i][0]);
            if (points[i][1] != points[i + 1][1])
                lowerRectAreaY[lowerRectAreaIndexY++] = (points[i][1] - points[0][1]) * (maxXLower - minXLower);
        }

        long minXUpper = Integer.MAX_VALUE;
        long maxXUpper = Integer.MIN_VALUE;
        long[] upperRectAreaY = new long[n - 1];
        int upperRectAreaIndexY = lowerRectAreaIndexY - 1;
        for (int i = n - 1; i >= 1; --i) {
            minXUpper = Math.min(minXUpper, points[i][0]);
            maxXUpper = Math.max(maxXUpper, points[i][0]);
            if (points[i][1] != points[i - 1][1]) {
                upperRectAreaY[upperRectAreaIndexY--] = (points[n - 1][1] - points[i][1]) * (maxXUpper - minXUpper);
            }
        }

        int areaNumY = lowerRectAreaIndexY;
        for (int i = 0; i < areaNumY; ++i) {
            minRectAreaY = Math.min(minRectAreaY, lowerRectAreaY[i] + upperRectAreaY[i]);
        }
        //endregion

        return Math.min(minRectAreaX, minRectAreaY);
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