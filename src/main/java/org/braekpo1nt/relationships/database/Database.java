package org.braekpo1nt.relationships.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.braekpo1nt.relationships.entities.Achievement;
import org.braekpo1nt.relationships.entities.Guild;
import org.braekpo1nt.relationships.entities.GuildPlayer;
import org.braekpo1nt.relationships.entities.PlayerAchievement;

import java.sql.SQLException;

/**
 * Establish connection to database via JDBC
 */
public class Database {
    
    private final Dao<Guild, Integer> guildDao;
    private final Dao<GuildPlayer, String> guildPlayerDao;
    private final Dao<Achievement, String> achievementDao;
    private final Dao<PlayerAchievement, String> playerAchievementDao;
    
    public Database(String path) throws SQLException {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + path);
        
        // create the tables, this also registers the foreign keys
        TableUtils.createTableIfNotExists(connectionSource, Guild.class);
        TableUtils.createTableIfNotExists(connectionSource, GuildPlayer.class);
        TableUtils.createTableIfNotExists(connectionSource, Achievement.class);
        
        // create the DAOs
        this.guildDao = DaoManager.createDao(connectionSource, Guild.class);
        this.guildPlayerDao = DaoManager.createDao(connectionSource, GuildPlayer.class);
        this.achievementDao = DaoManager.createDao(connectionSource, Achievement.class);
        this.playerAchievementDao = DaoManager.createDao(connectionSource, PlayerAchievement.class);
    }
    
    // the service layer uses the daos, adding abstraction
    
    
    public Dao<Guild, Integer> getGuildDao() {
        return guildDao;
    }
    
    public Dao<GuildPlayer, String> getGuildPlayerDao() {
        return guildPlayerDao;
    }
    
    public Dao<Achievement, String> getAchievementDao() {
        return achievementDao;
    }
    
    public Dao<PlayerAchievement, String> getPlayerAchievementDao() {
        return playerAchievementDao;
    }
}
