package org.braekpo1nt.relationships.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

/**
 * Establish connection to database via JDBC
 */
public class Database {
    
    public Database(String path) throws SQLException {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + path);
    }
}
