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

    //show statistics and exit
    static void show() {

        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int separatorLength = 31;
        Paint.separator(separatorLength);
        System.out.printf("%s\t\t%7d%n", Paint.getAnsiString(Paint.GREEN, "Total hands"), total);
        Paint.separator(separatorLength);

        for (Combination key : map.keySet()) {
            int value = map.get(key);
            double percent = (double)value / total * 100;
            System.out.printf("%s%s%7d%7.2f%%%n",
                                key,
                                getTab(key),
                                value,
                                Double.isNaN(percent) ? 0 : percent);
        }

        Deal.exit();
    }

    //show payouts and exit
    static void showPayouts() {

        int separatorLength = 23;
        Paint.separator(separatorLength);
        System.out.println(Paint.getAnsiString(Paint.MAGENTA, "Payouts"));
        Paint.separator(separatorLength);

        for (Combination combination : Combination.values()) {
            System.out.printf("%s%s%7d%n",
                                combination,
                                getTab(combination),
                                combination.getPayout());
        }

        Deal.exit();
    }

    private static String getTab(Combination combination) {
        return combination.equals(Combination.FLUSH) ? "\t\t" : "\t";
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
