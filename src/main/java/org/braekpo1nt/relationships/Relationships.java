package org.braekpo1nt.relationships;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.braekpo1nt.relationships.database.Database;
import org.braekpo1nt.relationships.entities.Achievement;
import org.braekpo1nt.relationships.entities.Guild;
import org.braekpo1nt.relationships.entities.GuildPlayer;
import org.braekpo1nt.relationships.listeners.JoinListener;
import org.braekpo1nt.relationships.service.AchievementService;
import org.braekpo1nt.relationships.service.GuildService;
import org.braekpo1nt.relationships.service.PlayerService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public final class Relationships extends JavaPlugin {
    
    private Database database;
    private PlayerService playerService;
    private GuildService guildService;
    private AchievementService achievementService;
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            // ensure the plugins data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            // create database connection
            this.database = new Database(getDataFolder().getAbsolutePath() + "/database.db");
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, "Can't reach database", e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // create our services
        this.playerService = new PlayerService(database.getGuildPlayerDao(), getLogger());
        this.guildService = new GuildService(database.getGuildDao(), database.getGuildPlayerDao(), getLogger());
        this.achievementService = new AchievementService(database.getGuildPlayerDao(), database.getAchievementDao(), database.getPlayerAchievementDao(), getLogger());
        
        // register listeners
        getServer().getPluginManager().registerEvents(new JoinListener(playerService), this);
        
        // register commands
        LiteralCommandNode<CommandSourceStack> createGuild = Commands.literal("createguild")
                .then(Commands.argument("name", StringArgumentType.word())
                        .executes(ctx -> {
                            String name = ctx.getArgument("name", String.class);
                            CommandSender sender = ctx.getSource().getSender();
                            if (!(sender instanceof Player player)) {
                                sender.sendMessage(Component.text("You are not a player", NamedTextColor.RED));
                                return 0;
                            }
                            GuildPlayer guildPlayer = this.playerService.findByUuid(player.getUniqueId().toString());
                            if (guildPlayer == null) {
                                player.sendMessage(Component.text("You are not in the database", NamedTextColor.RED));
                                return 0;
                            }
                            
                            if (guildPlayer.getGuild() != null) {
                                player.sendMessage(Component.text("You are already in a guild", NamedTextColor.RED));
                                return 0;
                            }
                            
                            if (guildService.findByName(name) != null) {
                                player.sendMessage(Component.text("A guild with that name already exists", NamedTextColor.RED));
                                return 0;
                            }
                            
                            Guild guild = guildService.create(new Guild(name));
                            playerService.update(guildPlayer);
                            
                            player.sendMessage("Guild created! You are now the owner of " + guild.getName());
                            return Command.SINGLE_SUCCESS;
                        }))
                .build();
        
        LiteralCommandNode<CommandSourceStack> createAchievements = Commands.literal("createachievements")
                .then(Commands.argument("player", ArgumentTypes.player())
                        .executes(ctx -> {
                            PlayerSelectorArgumentResolver playerResolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
                            Player player = playerResolver.resolve(ctx.getSource()).getFirst();
                            GuildPlayer guildPlayer = playerService.findByUuid(player.getUniqueId().toString());
                            if (guildPlayer == null) {
                                ctx.getSource().getSender().sendMessage(Component.empty()
                                        .append(player.displayName())
                                        .append(Component.text(" doesn't exist")));
                                return Command.SINGLE_SUCCESS;
                            }
                            
                            // ugly, just for testing purposes, running this multiple times will create them over and over
                            Achievement achievement1 = new Achievement("First Achievement", "this is the first achievement");
                            Achievement achievement2 = new Achievement("Second Achievement", "this is the second achievement");
                            Achievement achievement3 = new Achievement("Third Achievement", "this is the third achievement");
                            achievementService.create(achievement1);
                            achievementService.create(achievement2);
                            achievementService.create(achievement3);
                            
                            achievementService.addAchievementToPlayer(guildPlayer, achievement1);
                            achievementService.addAchievementToPlayer(guildPlayer, achievement2);
                            achievementService.addAchievementToPlayer(guildPlayer, achievement3);
                            return Command.SINGLE_SUCCESS;
                        }))
                .build();
        
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(createGuild);
            commands.registrar().register(createAchievements);
        });
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
