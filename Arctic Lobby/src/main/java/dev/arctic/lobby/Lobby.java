package dev.arctic.lobby;

import dev.arctic.core.api.util.BarUtil;
import dev.arctic.lobby.command.SpawnCommand;
import dev.arctic.lobby.event.LobbyEvents;
import dev.arctic.lobby.scoreboard.BoardEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Zvijer on 15.8.2017..
 */
public class Lobby extends JavaPlugin {

    private @Getter static Lobby lobby = null;

    @Override
    public void onEnable() {
        lobby = this;

        getServer().getPluginManager().registerEvents(new LobbyEvents(), this);
        getServer().getPluginManager().registerEvents(new BoardEvent(), this);

        getServer().getScheduler().runTaskTimerAsynchronously(this, new BarUtil.BarTask(), 0L, 10L);

        getCommand("setspawn").setExecutor(new SpawnCommand());
        Bukkit.getWorlds().forEach(world -> {
            world.setTime(1000);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doFireTick", "false");

            world.setDifficulty(Difficulty.EASY);
            System.out.println("Server> Setting world difficulty to " + world.getDifficulty() + ".");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                if (world.hasStorm())
                    world.setStorm(false);
            }, 20L, 100L);
        });
    }
}
