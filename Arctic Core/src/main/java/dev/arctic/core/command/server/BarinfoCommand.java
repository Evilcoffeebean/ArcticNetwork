package dev.arctic.core.command.server;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.ServerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Zvijer on 15.8.2017..
 */
public class BarinfoCommand extends CommandBase {

    public BarinfoCommand() {
        super("barinfo", 3, Rank.ADMIN);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length < getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/barinfo add [seconds] [message]");
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            int duration;
            String msg;
            try {
                duration = Integer.parseInt(args[1]);
                msg = StringUtil.join(2, args);

                Bukkit.getOnlinePlayers().forEach(p -> ServerUtil.persistActionbar(p, msg, duration));
            } catch (NumberFormatException e) {
                PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Invalid duration parameter.");
            }
        }
    }
}
