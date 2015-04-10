package db;

import org.apache.derby.jdbc.EmbeddedDataSource;

import cards.Combination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static final String TOTAL_HANDS = "Total hands";

    private static final String DB_NAME = "stats";
    private static final String CREATE = "create";
    private static final String SHUTDOWN = "shutdown";

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

    private static final String SELECT_FROM_PLAYER = "select id, credit from player where id = ?";
    private static final String UPDATE_PLAYER = "update player set credit = ? where id = ?";

    private static EmbeddedDataSource ds;
    private static Connection conn;

    public static void init() throws SQLException {

        ds = new EmbeddedDataSource();
        ds.setDatabaseName(DB_NAME);
        ds.setCreateDatabase(CREATE);
        conn = ds.getConnection();

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

    public static List<Stats> getAllStats() throws SQLException {
        ArrayList<Stats> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SELECT_ALL_FROM_STATS);
        while(rs.next()) {
            result.add(new Stats(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        stmt.close();
        return result;
    }

    public static void updateStats(String combination) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_STATS);
        pstmt.setString(1, combination);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public static Player getPlayer(int id) throws SQLException {
        Player result = null;
        PreparedStatement pstmt = conn.prepareStatement(SELECT_FROM_PLAYER);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) result = new Player(rs.getInt(1), rs.getInt(2));
        pstmt.close();
        return result;
    }

    public static void updatePlayer(int id, int credit) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_PLAYER);
        pstmt.setInt(2, id);
        pstmt.setInt(1, credit);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public static void shutdownDB() {
        if (ds != null) {
            ds.setShutdownDatabase(SHUTDOWN);
            try {
                conn.close();
                ds.getConnection();
            } catch (SQLException e) {
                //database shutdown
            } finally {
                conn = null;
                ds = null;
            }
        }
    }
}
