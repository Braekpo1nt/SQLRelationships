package org.braekpo1nt.relationships.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.braekpo1nt.relationships.entities.Guild;
import org.braekpo1nt.relationships.entities.GuildPlayer;

import java.sql.SQLException;

/**
 * Establish connection to database via JDBC
 */
public class Database {
    
    public Database(String path) throws SQLException {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + path);
        
        // create the tables, this also registers the foreign keys
        TableUtils.createTableIfNotExists(connectionSource, Guild.class);
        TableUtils.createTableIfNotExists(connectionSource, GuildPlayer.class);
    }
}
