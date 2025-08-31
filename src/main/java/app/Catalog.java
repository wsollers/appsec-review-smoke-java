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
    public long count() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rows = statement.executeQuery("SELECT count(*) FROM " + TABLE);
        return rows.next() ? rows.getLong(1) : 0L;
    }
}
