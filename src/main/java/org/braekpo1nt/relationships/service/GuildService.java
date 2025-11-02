package org.braekpo1nt.relationships.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import org.braekpo1nt.relationships.entities.Guild;
import org.braekpo1nt.relationships.entities.GuildPlayer;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * business logic for creating guilds, grabbing players from guilds, that stuff
 */
public class GuildService {
    private final Dao<Guild, Integer> guildDao;
    private final Dao<GuildPlayer, String> playerDao;
    private final Logger logger;
    
    
    public GuildService(Dao<Guild, Integer> guildDao, Dao<GuildPlayer, String> playerDao, Logger logger) {
        this.guildDao = guildDao;
        this.playerDao = playerDao;
        this.logger = logger;
    }
    
    public Guild create(Guild guild) {
        try {
            // assigns the generated ID to this guild object
            guildDao.create(guild);
            return guild;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "could not create guild", e);
            return null;
        }
    }
    
    public Guild findByName(String name) {
        try {
            return guildDao.queryBuilder().where()
                    .eq("name", name).queryForFirst();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "could not find guild with name " + name, e);
            return null;
        }
    }
    
    public ForeignCollection<GuildPlayer> getGuildMembers(int guildId) {
        try {
            Guild guild = guildDao.queryForId(guildId);
            if (guild == null) {
                return null;
            }
            return guild.getMembers();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "could not find guild with id " + guildId, e);
            return null;
        }
    }
}
