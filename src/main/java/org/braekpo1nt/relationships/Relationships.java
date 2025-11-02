package org.braekpo1nt.relationships;

import org.braekpo1nt.relationships.database.Database;
import org.braekpo1nt.relationships.service.PlayerService;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public final class Relationships extends JavaPlugin {
    
    private Database database;
    private PlayerService playerService;
    
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
            getLogger().log(Level.SEVERE, "Can't reach database", e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // create our services
        this.playerService = new PlayerService(database.getGuildPlayerDao(), getLogger());
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
