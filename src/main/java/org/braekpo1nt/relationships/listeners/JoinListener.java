package org.braekpo1nt.relationships.listeners;

import org.braekpo1nt.relationships.entities.GuildPlayer;
import org.braekpo1nt.relationships.service.PlayerService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    
    private final PlayerService playerService;
    
    public JoinListener(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (playerService.findByUuid(player.getUniqueId().toString()) == null) {
            playerService.create(new GuildPlayer(player));
        }
    }
    
}
