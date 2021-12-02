package dev.arctic.core.command.server;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.JSONUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 17.8.2017..
 */
public class RuleCommand extends CommandBase {

    public RuleCommand() {
        super("rules", 0, Rank.MEMBER);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        new JSONUtil("Hover here to view our server's rules!")
                .color(JSONUtil.Color.GRAY)
                .bold()
                .hover(JSONUtil.HoverEvent.SHOW_TEXT, StringUtil.join(
                        "&7By connecting to the Arctic Network; our servers or hardware,\n&7you automatically agree to abide by our network rules and understand\n&7that failing to do so will result in network punishments and restrictions.",
                        "\n",
                        "&b1) &7Swearing, racism and nazism isn't allowed! You're obliged to respect all members.",
                        "&b2) &7Spamming and flooding chat isn't allowed. Please slow down and vary your messages.",
                        "&b3) &7You aren't allowed to attempt to bypass/circumvent the filter.",
                        "&b4) &7Using hacked clients or obtaining unfair advantages is strictly forbidden and is bannable.",
                        "&b5) &7DDOS/DOS threats aren't allowed.",
                        "&b6) &7You aren't allowed to post any personal or sensitive information about another member. This includes exposing somebody.",
                        "&b7) &7Creating false/fraudulant reports against other players isn't allowed.",
                        "&b8) &cOur most important rule is that you must have fun! :)",
                        "\n",
                        "&bFor anymore information regarding our network rules; ask a member of staff!"
                ))
                .sendJson((Player) caller, false);
    }
}
