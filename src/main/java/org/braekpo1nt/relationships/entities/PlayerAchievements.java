package org.braekpo1nt.relationships.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * The join table, represents the relationship between a single player and a single achievement
 */
@DatabaseTable(tableName = "player_achievements")
public class PlayerAchievements {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "player_id")
    private GuildPlayer guildPlayer;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "achievement_id")
    private Achievement achievement;
    
    @DatabaseField(canBeNull = false)
    private Date dateAchieved;
    
    public PlayerAchievements() {
    }
    
    public int getId() {
        return id;
    }
    
    public GuildPlayer getGuildPlayer() {
        return guildPlayer;
    }
    
    public void setGuildPlayer(GuildPlayer guildPlayer) {
        this.guildPlayer = guildPlayer;
    }
    
    public Achievement getAchievement() {
        return achievement;
    }
    
    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
    
    public Date getDateAchieved() {
        return dateAchieved;
    }
    
    public void setDateAchieved(Date dateAchieved) {
        this.dateAchieved = dateAchieved;
    }
}
