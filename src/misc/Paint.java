package misc;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.Arrays;

public class Paint {

    public static final String RED = "fg_red,bg_white,intensity_bold";
    public static final String BLACK = "fg_black,bg_white";
    public static final String YELLOW = "fg_yellow,intensity_bold";
    public static final String GREEN = "fg_green,intensity_bold";
    public static final String MAGENTA = "fg_magenta,intensity_bold";
    public static final String WHITE = "fg_white,intensity_bold";

    public static String getAnsiString(String attributes, String text) {

        return ansi().render(String.format("@|%s %s|@", attributes, text)).toString();
    }

    public static String getAnsiString(String attributes, int value) {

        return getAnsiString(attributes, Integer.toString(value));
    }

    public static void separator(int number) {

        char[] charLine = new char[number];
        Arrays.fill(charLine, '-');

        System.out.println(getAnsiString(YELLOW, String.valueOf(charLine)));
    }
}
