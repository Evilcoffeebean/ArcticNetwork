package dev.arctic.core.command.account;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 15.8.2017..
 */
public class GmCommand extends CommandBase {

    public GmCommand() {
        super("gm", 2, Rank.ADMIN);
    }

    private GameMode getGameMode(int mode) {
        GameMode gameMode = null;
        switch (mode) {
            case 1:
                gameMode = GameMode.CREATIVE;
                break;
            case 0:
                gameMode = GameMode.SURVIVAL;
                break;
            case 3:
                gameMode = GameMode.ADVENTURE;
                break;
        }
        return gameMode;
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length != getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/gm <player> <game mode>");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            PlayerUtil.help(caller, PlayerUtil.Module.SERVER, String.format("'&e%s&7' couldn't have been found online.", args[0]));
            return;
        }

        int gm;
        try {
            gm = Integer.valueOf(args[1]);
            target.setGameMode(getGameMode(gm));

            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, String.format("You set &e%s&7's game mode to &e%s&7.", target.getName(), getGameMode(gm)));
            PlayerUtil.help(target, PlayerUtil.Module.SERVER, String.format("Your game mode was updated to &e%s&7.", getGameMode(gm)));
        } catch (Exception e) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Invalid game mode parameter type.");
        }
    }
}
