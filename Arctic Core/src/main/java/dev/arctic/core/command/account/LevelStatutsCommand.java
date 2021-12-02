package dev.arctic.core.command.account;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.command.CommandSender;

/**
 * Created by Zvijer on 21.8.2017..
 */
public class LevelStatutsCommand extends CommandBase {

    public LevelStatutsCommand() {
        super("levelstatus", 0, Rank.MEMBER);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        caller.sendMessage(StringUtil.join(
                "&7[&bLevel Information&7]",
                "&70 - 10",
                "&810 - 20",
                "&e20 - 35",
                "&c35 - 45",
                "&445-65",
                "&965 - 75",
                "&375-95",
                "&b95-100"));
    }
}
