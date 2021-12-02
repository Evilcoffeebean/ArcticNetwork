package dev.arctic.core.command.server;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 21.8.2017..
 */
public class StaffChatCommand extends CommandBase {

    public StaffChatCommand() {
        super("sc", 1, Rank.HELPER);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length < getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/sc <message>");
            return;
        }
        final String msg = StringUtil.join(0, args);
        PlayerUtil.staff((Player) caller, msg);
    }
}
