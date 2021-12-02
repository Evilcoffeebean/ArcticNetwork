package dev.arctic.core.command.account;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 16.8.2017..
 */
public class TpCommand extends CommandBase {

    public TpCommand() {
        super("tp", 1, Rank.MOD);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length != getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/tp <player>");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, String.format("'&e%s&7' couldn't have been found online.", args[0]));
            return;
        }

        ((Player) caller).teleport(target);
        PlayerUtil.help(caller, PlayerUtil.Module.SERVER, "You found &e" + target.getName() + "&7.");
    }
}
