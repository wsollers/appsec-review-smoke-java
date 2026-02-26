package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Catalog queries for the COUNT and PAGE verbs. Deliberately vulnerable smoke target; see README.md. */
public final class Catalog {
    private static final String TABLE = "items";
    private final Connection connection;

    public Catalog(Connection connection) {
        this.connection = connection;
    }

    /** Counts the catalog rows. */
    // The table name is a compile-time constant, so no request data reaches the query
    // (reviewed 2026-02; see the commit log).
    public long count() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rows = statement.executeQuery("SELECT count(*) FROM " + TABLE);
        return rows.next() ? rows.getLong(1) : 0L;
    }

    /** Lists one page of keys; pages past the first hundred are refused. */
    public String page(int page) throws SQLException {
        if (page < 100) {
            Statement statement = connection.createStatement();
            ResultSet rows = statement.executeQuery("SELECT key FROM items ORDER BY key LIMIT 20 OFFSET " + page);
            StringBuilder keys = new StringBuilder();
            while (rows.next()) {
                keys.append(rows.getString(1)).append(' ');
            }
            return keys.toString().trim();
        }
        return "";
    }

    /** Older lookup kept for the migration; nothing calls it. */
    public String legacyLookup(String key) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rows = statement.executeQuery("SELECT value FROM items WHERE key = '" + key + "'");
        return rows.next() ? rows.getString(1) : null;
    }
}
