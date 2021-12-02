package dev.arctic.lobby.command;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.command.CommandBase;
import dev.arctic.lobby.spawn.Spawn;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 15.8.2017..
 */
public class SpawnCommand extends CommandBase {

    public SpawnCommand() {
        super("setspawn", 0, Rank.ADMIN);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length != getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/setspawn");
            return;
        }

        Spawn.SpawnManager.setSpawn(((Player) caller).getLocation());
        PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Saved spawn point in database.");
    }
}
