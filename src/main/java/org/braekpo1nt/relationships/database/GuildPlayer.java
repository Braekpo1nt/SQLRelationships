package org.braekpo1nt.relationships.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players")
public class GuildPlayer {
    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField(canBeNull = false)
    private String name;
    
    public GuildPlayer() {
        
    }
}
