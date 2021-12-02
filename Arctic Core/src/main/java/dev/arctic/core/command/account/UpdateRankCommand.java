package dev.arctic.core.command.account;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import dev.arctic.core.object.ArcticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 28.7.2017..
 */
public class UpdateRankCommand extends CommandBase {

    public UpdateRankCommand() {
        super("group", 2, Rank.ADMIN);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length != getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/group <player> <rank>");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, String.format("'&e%s&7' has never joined before.", args[0]));
            return;
        }

        ArcticPlayer targetObject = Core.getCore().getAccountCache().getPlayer(target.getUniqueId());
        Rank temp = null;
        try {
            temp = Rank.valueOf(args[1]);
            if (caller instanceof Player) {
                ArcticPlayer playerObject = Core.getCore().getAccountCache().getPlayer(((Player) caller).getUniqueId());
                if (temp.compareTo(playerObject.getRank()) > 0) {
                    PlayerUtil.help(caller, PlayerUtil.Module.RANK, "You cannot set ranks higher than your own.");
                    return;
                }
            }

            targetObject.updateRank(temp);
            if (target instanceof Player) {
                ((Player) target).setPlayerListName(temp.getName(true) + targetObject.getName());
            }
            PlayerUtil.help(caller, PlayerUtil.Module.RANK, String.format("You set &e%s&7's rank to &e%s&7.", targetObject.getName(), temp.getName()));

            if (target instanceof Player) {
                PlayerUtil.help(((Player) target), PlayerUtil.Module.RANK, String.format("Your rank was updated to &e%s&7.", temp.getName()));
            }
        } catch (Exception e) {
            PlayerUtil.help(caller, PlayerUtil.Module.RANK, String.format("Invalid rank parameter '&e%s&7'.", args[1]));
        }
    }
}
