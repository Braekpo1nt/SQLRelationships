package org.braekpo1nt.relationships;

import org.braekpo1nt.relationships.database.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Relationships extends JavaPlugin {
    
    private Database database;
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            // ensure the plugins data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            // create database connection
            this.database = new Database(getDataFolder().getAbsolutePath() + "database.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
