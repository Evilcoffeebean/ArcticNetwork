package dev.arctic.core.command.account;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 20.8.2017..
 */
public class VanishCommand extends CommandBase {

    public VanishCommand() {
        super("vanish", 0, Rank.HELPER);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (!Core.getCore().getVanishUtil().isHidden((Player) caller)) {
            Core.getCore().getVanishUtil().hidePlayer((Player) caller);
            PlayerUtil.help(caller, PlayerUtil.Module.SERVER, "You've hidden yourself from non-staff!");
        } else {
            Core.getCore().getVanishUtil().showPlayer((Player) caller);
            PlayerUtil.help(caller, PlayerUtil.Module.SERVER, "You've shown yourself.");
        }
    }
}
