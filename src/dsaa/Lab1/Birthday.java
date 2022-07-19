package dsaa.Lab1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Birthday {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        Person[] people = new Person[n];
        for (int i = 0; i < n; ++i) {
            people[i] = new Person(in.next(), in.nextInt(), in.nextInt(), in.nextInt());
        }
        Arrays.stream(people).sorted().forEach(person -> out.println(person.name));
        out.close();
    }

    static class Person implements Comparable<Person> {
        public String name;
        public int year;
        public int month;
        public int day;

        public Person(String name, int year, int month, int day) {
            this.name = name;
            this.year = year;
            this.month = month;
            this.day = day;
        }

        @Override
        public int compareTo(Person o) {
            if (this.year == o.year && this.month == o.month && this.day == o.day) {
                return 0;
            }
            if (this.year < o.year || (this.year == o.year && this.month < o.month) || (this.year == o.year && this.month == o.month && this.day < o.day)) {
                return -1;
            } else {
                return 1;
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
