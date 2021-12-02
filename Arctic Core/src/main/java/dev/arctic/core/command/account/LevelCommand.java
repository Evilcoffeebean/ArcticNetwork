package dev.arctic.core.command.account;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.NumberUtil;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.command.CommandBase;
import dev.arctic.core.object.ArcticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 20.8.2017..
 */
public class LevelCommand extends CommandBase {

    public LevelCommand() {
        super("level", 2, Rank.ADMIN);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length != getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.SERVER, "Usage: &e/level <player> <level>");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            PlayerUtil.help(caller, PlayerUtil.Module.SERVER, String.format("'&e%s&7' has never played before.", args[0]));
            return;
        }

        final ArcticPlayer targetObj = Core.getCore().getAccountCache().getPlayer(target.getUniqueId());
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
            if (amount > 100) {
                PlayerUtil.help(caller, PlayerUtil.Module.SERVER, "Network level cannot be greater than 100!");
                return;
            }
            targetObj.updateNetLevel(amount, false);
            PlayerUtil.help(caller, PlayerUtil.Module.SERVER, String.format("You set &e%s&7's network level to &e%.0f&7.", targetObj.getName(), NumberUtil.round(amount, 0)));
            if (target instanceof Player)
                PlayerUtil.help(((Player) target), PlayerUtil.Module.SERVER, String.format("Your network level has been set to &e%.0f&7.", NumberUtil.round(amount, 0)));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            PlayerUtil.help(caller, PlayerUtil.Module.SERVER, "Invalid amount parameter.");
        }
    }
}
