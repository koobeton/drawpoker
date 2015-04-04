package db;

import cards.Combination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static final String TOTAL_HANDS = "Total hands";

    private static final String URL = "jdbc:derby:stats";
    private static final String START = URL + ";create=true";
    private static final String SHUTDOWN = URL + ";shutdown=true";

    private static final String CREATE_STATS =
            "create table stats (" +
                    "id int primary key " +
                    "generated always as identity (start with 1, increment by 1), " +
                    "name varchar(20) unique, " +
                    "value int default 0" +
                    ")";
    private static final String CREATE_PLAYER =
            "create table player (" +
                    "id int primary key " +
                    "generated always as identity (start with 1, increment by 1), " +
                    "credit int default 0" +
                    ")";
    private static final String INSERT_DEFAULT_STATS = "insert into stats (name) values (?)";
    private static final String INSERT_DEFAULT_PLAYER = "insert into player (credit) values (default)";

    private static final String SELECT_ALL_FROM_STATS = "select id, name, value from stats";
    private static final String UPDATE_STATS =
            String.format("update stats set value = value + 1 where name in ('%s', ?)", TOTAL_HANDS);

    private static Connection conn;

    public static void init() throws SQLException {

        conn = DriverManager.getConnection(START);
        if (!dbExists(conn.getWarnings())) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_STATS);

            PreparedStatement pstmt = conn.prepareStatement(INSERT_DEFAULT_STATS);
            pstmt.setString(1, TOTAL_HANDS);
            pstmt.addBatch();
            for (Combination combination : Combination.values()) {
                pstmt.setString(1, combination.toString());
                pstmt.addBatch();
            }
            pstmt.executeBatch();

            stmt.executeUpdate(CREATE_PLAYER);
            stmt.executeUpdate(INSERT_DEFAULT_PLAYER);

            pstmt.close();
            stmt.close();
        }
    }

    private static boolean dbExists(SQLWarning sw) {
        boolean exists = false;
        while (sw != null) {
            if (sw.getSQLState().equals("01J01") && sw.getErrorCode() == 10000) exists = true;
            sw = sw.getNextWarning();
        }
        return exists;
    }

    public static List<Stats> getAllFromStats() throws SQLException {
        ArrayList<Stats> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SELECT_ALL_FROM_STATS);
        while(rs.next()) {
            result.add(new Stats(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        return result;
    }

    public static void updateStats(String combination) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_STATS);
        pstmt.setString(1, combination);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public static void shutdownDB() {
        try {
            conn = DriverManager.getConnection(SHUTDOWN);
        } catch (SQLException e) {
            //database shutdown
        } finally {
            conn = null;
        }
    }
}
