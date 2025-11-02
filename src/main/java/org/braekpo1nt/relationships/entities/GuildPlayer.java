package org.braekpo1nt.relationships.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.bukkit.entity.Player;

@DatabaseTable(tableName = "players")
public class GuildPlayer {
    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField(canBeNull = false)
    private String name;
    
    // auto refresh makes it so the guild is provided when you get a GuildPlayer object
    // without it, you would have to separately get the guild on your own and supply it
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "guild_id")
    private Guild guild;
    
    public GuildPlayer() {
        
    }
    
    public GuildPlayer(Player player) {
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Guild getGuild() {
        return guild;
    }
    
    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
