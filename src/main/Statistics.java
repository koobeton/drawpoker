package main;

import cards.Combination;
import db.DBManager;
import db.Stats;
import misc.Paint;

import java.sql.SQLException;
import java.util.Iterator;

class Statistics {

    //show statistics and exit
    static void show() throws SQLException {

        Iterator<Stats> iterator = DBManager.getAllStats().iterator();
        Stats current;
        int separatorLength = 31;
        int total = 0;

        //table header
        Paint.separator(separatorLength);
        if (iterator.hasNext()) {
            current = iterator.next();
            System.out.printf("%s\t\t%s%n",
                    Paint.getAnsiString(Paint.GREEN, current.getName()),
                    Paint.getAnsiString(Paint.WHITE, String.format("%7d", total = current.getValue())));
        }

        //table body
        Paint.separator(separatorLength);
        while(iterator.hasNext()) {
            current = iterator.next();
            String name = current.getName();
            int value = current.getValue();
            double percent = (double)value / total * 100;
            System.out.printf("%s%s%7d%7.2f%%%n",
                    name,
                    getTab(name),
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
                                getTab(combination.toString()),
                                combination.getPayout());
        }

        Deal.exit();
    }

    private static String getTab(String combinationName) {
        return combinationName.equals(Combination.FLUSH.toString()) ? "\t\t" : "\t";
    }

    static void update(Combination combination) throws SQLException {

        DBManager.updateStats(combination == null ? null : combination.toString());
    }
}
