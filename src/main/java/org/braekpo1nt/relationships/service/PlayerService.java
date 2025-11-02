package org.braekpo1nt.relationships.service;

import com.j256.ormlite.dao.Dao;
import org.braekpo1nt.relationships.entities.GuildPlayer;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the business logic, the actual plugin logic, that does things with the players
 */
public class PlayerService {
    private final Dao<GuildPlayer, String> playerDao;
    private final Logger logger;
    
    public PlayerService(Dao<GuildPlayer, String> playerDao, Logger logger) {
        this.playerDao = playerDao;
        this.logger = logger;
    }
    
    public GuildPlayer create(GuildPlayer player) {
        try {
            playerDao.create(player);
            return player;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "could not create GuildPlayer", e);
            return null;
        }
    }
}
