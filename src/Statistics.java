import java.io.*;
import java.util.SortedMap;
import java.util.TreeMap;

class Statistics {

    private static File file = new File("stats");
    private static SortedMap<Combination, Integer> map = new TreeMap<>();
    private static int total;

    static {
        //initializing map
        for (Combination combination : Combination.values()) {
            map.put(combination, 0);
        }
    }

    static void show() {

        try {
            readFile();
        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s%n", e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Total\t\t%7d%n", total);
        for (Combination key : map.keySet()) {
            String tab = key.equals(Combination.FLUSH) ? "\t\t" : "\t";
            int value = map.get(key);
            double percent = (double)value / total * 100;
            System.out.printf("%s%s%7d%7.2f%%%n", key, tab, value, percent);
        }
    }

    static void update(Combination combination) {

        try {
            initFile();
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

    private static void initFile() throws IOException {

        if (!file.exists()) {
            file.createNewFile();
            writeFile();
        }
    }

    private static void readFile() throws IOException {

        try (BufferedReader input = new BufferedReader(new FileReader(file))) {

            total = input.read();
            for (Combination key : map.keySet()) {
                map.put(key, input.read());
            }
        }
    }

    private static void writeFile() throws IOException {

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {

            output.write(total);
            for (Integer value : map.values()) {
                output.write(value);
            }
        }
    }
}
