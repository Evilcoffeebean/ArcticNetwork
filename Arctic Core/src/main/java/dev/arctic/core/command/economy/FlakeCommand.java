package dev.arctic.core.command.economy;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.command.CommandBase;
import dev.arctic.core.object.ArcticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 17.8.2017..
 */
public class FlakeCommand extends CommandBase {

    public FlakeCommand() {
        super("flakes", 3, Rank.ADMIN);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length != getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/flakes [add/set] <player> <amount>");
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            if (!target.hasPlayedBefore()) {
                PlayerUtil.help(caller, PlayerUtil.Module.DATABASE, String.format("'&e%s&7' couldn't have been found in the server's database.", args[1]));
                return;
            }
            int amount;
            try {
                amount = Integer.parseInt(args[2]);
                final ArcticPlayer targetObj = Core.getCore().getAccountCache().getPlayer(target.getUniqueId());
                targetObj.updateFlakes(amount, true);

                PlayerUtil.help(caller, PlayerUtil.Module.ECONOMY, String.format("You updated &e%s&7's flake amount to &e%d&7.", targetObj.getName(), targetObj.getFlakes() + amount));
            } catch (NumberFormatException e) {
                PlayerUtil.help(caller, PlayerUtil.Module.ECONOMY, "Invalid amount parameter.");
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            if (!target.hasPlayedBefore()) {
                PlayerUtil.help(caller, PlayerUtil.Module.DATABASE, String.format("'&e%s&7' couldn't have been found in the server's database.", args[1]));
                return;
            }
            int amount;
            try {
                amount = Integer.parseInt(args[2]);
                final ArcticPlayer targetObj = Core.getCore().getAccountCache().getPlayer(target.getUniqueId());
                targetObj.updateFlakes(amount, false);

                PlayerUtil.help(caller, PlayerUtil.Module.ECONOMY, String.format("You set &e%s&7's flake amount to &e%d&7.", targetObj.getName(), amount));
            } catch (NumberFormatException e) {
                PlayerUtil.help(caller, PlayerUtil.Module.ECONOMY, "Invalid amount parameter.");
            }
        }
    }
}
