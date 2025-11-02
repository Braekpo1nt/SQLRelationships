package org.braekpo1nt.relationships.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
}
