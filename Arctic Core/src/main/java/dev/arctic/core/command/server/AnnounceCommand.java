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
public class AnnounceCommand extends CommandBase {

    public AnnounceCommand() {
        super("announce", 1, Rank.ADMIN);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length < getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.CLIENT, "Usage: &e/announce <message>");
            return;
        }

        final String msg = StringUtil.join(0, args);
        Bukkit.getOnlinePlayers().forEach(p -> ServerUtil.sendTitle(p, "&b&lANNOUNCEMENT", msg));
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(StringUtil.color("&f&l★ &7[&bAnnouncement&7] &f&l★ &6" + msg)));
    }
}
