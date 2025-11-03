package org.braekpo1nt.relationships.service;

import com.j256.ormlite.dao.Dao;
import org.braekpo1nt.relationships.entities.Achievement;
import org.braekpo1nt.relationships.entities.GuildPlayer;
import org.braekpo1nt.relationships.entities.PlayerAchievement;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AchievementService {
    private final Dao<GuildPlayer, String> guildPlayerDao;
    private final Dao<Achievement, String> achievementDao;
    private final Dao<PlayerAchievement, String> playerAchievementDao;
    private final Logger logger;
    
    public AchievementService(Dao<GuildPlayer, String> guildPlayerDao, Dao<Achievement, String> achievementDao, Dao<PlayerAchievement, String> playerAchievementDao, Logger logger) {
        this.guildPlayerDao = guildPlayerDao;
        this.achievementDao = achievementDao;
        this.playerAchievementDao = playerAchievementDao;
        this.logger = logger;
    }
    
    public boolean addAchievementToPlayer(GuildPlayer guildPlayer, Achievement achievement) {
        try {
            PlayerAchievement playerAchievement = new PlayerAchievement();
            playerAchievement.setGuildPlayer(guildPlayer);
            playerAchievement.setAchievement(achievement);
            playerAchievement.setDateAchieved(new Date());
            playerAchievementDao.create(playerAchievement);
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "could not create PlayerAchievement", e);
            return false;
        }
    }
    
    public @Nullable List<Achievement> getAchievementForPlayer(GuildPlayer player) {
        try {
            List<PlayerAchievement> playerAchievements = playerAchievementDao.queryBuilder()
                    .where()
                    .eq("player_id", player.getUuid())
                    .query();
            
            if (playerAchievements == null) {
                return null;
            }
            
            List<Achievement> achievements = new ArrayList<>();
            for (PlayerAchievement playerAchievement : playerAchievements) {
                achievements.add(playerAchievement.getAchievement());
            }
            return achievements;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "could not get achievements for player: "+ player.getName(), e);
            return null;
        }
    }
    
    public @Nullable List<GuildPlayer> getPlayersWithAchievement(Achievement achievement) {
        try {
            List<PlayerAchievement> playerAchievements = playerAchievementDao.queryBuilder()
                    .where()
                    .eq("achievement_id", achievement.getId())
                    .query();
            
            if (playerAchievements == null) {
                return null;
            }
            
            List<GuildPlayer> players = new ArrayList<>();
            for (PlayerAchievement playerAchievement : playerAchievements) {
                players.add(playerAchievement.getGuildPlayer());
            }
            return players;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "could not get players for achievement: "+ achievement.getName(), e);
            return null;
        }
    }
}
