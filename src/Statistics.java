import java.io.*;
import java.util.SortedMap;
import java.util.TreeMap;

class Statistics {

    private static File file = new File("stats");
    private static SortedMap<Combination, Integer> map = new TreeMap<>();
    private static int total;

    static {
        //initialize map
        for (Combination combination : Combination.values()) {
            map.put(combination, 0);
        }
        //initialize file
        try {
            if (file.createNewFile()) writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void show() {

        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        separator();
        System.out.printf("Total hands\t\t%7d%n", total);
        separator();
        for (Combination key : map.keySet()) {
            String tab = key.equals(Combination.FLUSH) ? "\t\t" : "\t";
            int value = map.get(key);
            double percent = (double)value / total * 100;
            System.out.printf("%s%s%7d%7.2f%%%n", key, tab, value, percent);
        }
    }

    private static void separator() {

        for (int i = 0; i < 31; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    static void update(Combination combination) {

        try {
            readFile();
            total++;
            if (combination != null) {
                for (Combination key : map.keySet()) {
                    if (key.equals(combination)) map.put(key, map.get(key) + 1);
                }
            }
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile() throws IOException {

        try (BufferedReader input = new BufferedReader(new FileReader(file))) {

            total = readInt(input);
            for (Combination key : map.keySet()) {
                map.put(key, readInt(input));
            }
        }
    }

    private static int readInt(BufferedReader buffer) throws IOException {

        String str = buffer.readLine().replaceAll("[^0-9]", "");
        str = str.equals("") ? "0" : str;
        return Integer.parseInt(str);
    }

    private static void writeFile() throws IOException {

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {

            output.write(total + System.lineSeparator());
            for (Integer value : map.values()) {
                output.write(value + System.lineSeparator());
            }
        }
    }
}
