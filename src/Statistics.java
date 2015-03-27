import java.sql.*;

class Statistics {

    private static final String TOTAL_HANDS = "Total hands";

    private static final String URL = "jdbc:derby:stats";
    private static final String START = URL + ";create=true";
    private static final String SHUTDOWN = URL + ";shutdown=true";
    private static final String CREATE =
            "create table stats (" +
                    "id int primary key " +
                        "generated always as identity (start with 1, increment by 1), " +
                    "name varchar(20) unique, " +
                    "value int default 0" +
                    ")";
    private static final String INSERT_DEFAULT = "insert into stats (name) values (?)";
    private static final String UPDATE =
            String.format("update stats set value = value + 1 where name in ('%s', ?)", TOTAL_HANDS);
    private static final String SELECT = "select name, value from stats";

    private static Connection conn;

    static void init() throws SQLException {

        conn = DriverManager.getConnection(START);
        if (!dbExists(conn.getWarnings())) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(CREATE);
            stmt.close();
            PreparedStatement pstmt = conn.prepareStatement(INSERT_DEFAULT);
            pstmt.setString(1, TOTAL_HANDS);
            pstmt.addBatch();
            for (Combination combination : Combination.values()) {
                pstmt.setString(1, combination.toString());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
        }
    }

    //show statistics and exit
    static void show() throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SELECT);
        int separatorLength = 31;
        int total = 0;
        Paint.separator(separatorLength);
        if (rs.next()) {
            System.out.printf("%s\t\t%s%n",
                    Paint.getAnsiString(Paint.GREEN, rs.getString("name")),
                    Paint.getAnsiString(Paint.WHITE, String.format("%7d", total = rs.getInt("value"))));
        }
        Paint.separator(separatorLength);
        while(rs.next()) {
            String name = rs.getString("name");
            int value = rs.getInt("value");
            double percent = (double)value / total * 100;
            System.out.printf("%s%s%7d%7.2f%%%n",
                    name,
                    getTab(name),
                    value,
                    Double.isNaN(percent) ? 0 : percent);
        }
        stmt.close();

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

        PreparedStatement pstmt = conn.prepareStatement(UPDATE);
        pstmt.setString(1, combination == null ? null : combination.toString());
        pstmt.executeUpdate();
        pstmt.close();
    }

    private static boolean dbExists(SQLWarning sw) {
        boolean exists = false;
        while (sw != null) {
            if (sw.getSQLState().equals("01J01") && sw.getErrorCode() == 10000) exists = true;
            sw = sw.getNextWarning();
        }
        return exists;
    }

    static void shutdownDB() {
        try {
            conn = DriverManager.getConnection(SHUTDOWN);
        } catch (SQLException e) {
            //database shutdown
        } finally {
            conn = null;
        }
    }
}
