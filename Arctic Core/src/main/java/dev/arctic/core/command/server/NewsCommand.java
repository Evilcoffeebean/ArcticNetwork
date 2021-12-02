package dev.arctic.core.command.server;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.command.CommandSender;

/**
 * Created by Zvijer on 28.7.2017..
 */
public class NewsCommand extends CommandBase {

    public NewsCommand() {
        super("news", 2, Rank.JNR_DEV);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length < getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/news [add/remove] <message>");
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            final String msg = StringUtil.join(1, args);
            Core.getCore().getDatabaseManager().getQueryBuilder().insert("news (name)", msg);
            PlayerUtil.help(caller, PlayerUtil.Module.DATABASE, "Insert complete: &e" + msg);
            return;
        } else if (args[0].equalsIgnoreCase("remove")) {
            final String msg = StringUtil.join(1, args);
            if (Core.getCore().getDatabaseManager().getQueryBuilder().exists("news", new String[] {"name", msg})) {
                Core.getCore().getDatabaseManager().getQueryBuilder().delete("news", new String[] {"name", msg});
                PlayerUtil.help(caller, PlayerUtil.Module.DATABASE, "Delete complete.");
                return;
            } else {
                PlayerUtil.help(caller, PlayerUtil.Module.DATABASE, "The specified news doesn't exist.");
            }
        }
    }
}
