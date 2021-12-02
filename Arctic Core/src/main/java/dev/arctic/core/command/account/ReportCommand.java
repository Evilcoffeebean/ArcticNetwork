package dev.arctic.core.command.account;

import com.google.common.collect.Lists;
import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Created by Zvijer on 21.8.2017..
 */
public class ReportCommand extends CommandBase {

    private List<UUID> cooldown = Lists.newArrayList();

    public ReportCommand() {
        super("report", 2, Rank.MEMBER);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (!cooldown.contains(((Player) caller).getUniqueId())) {
            if (args.length < getLength()) {
                PlayerUtil.help(caller, PlayerUtil.Module.REPORT, "Usage: &e/report <player> <reason>");
                return;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore()) {
                PlayerUtil.help(caller, PlayerUtil.Module.REPORT, String.format("'&e%s&7' has never joined before.", args[0]));
                return;
            }
            if (target instanceof Player && target == caller) {
                PlayerUtil.help(caller, PlayerUtil.Module.REPORT, "Why would you report yourself?");
                return;
            }
            final String reason = StringUtil.join(1, args);
            PlayerUtil.report((Player) caller, target.getUniqueId(), reason);
            cooldown.add(((Player) caller).getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getCore(), () -> {
                cooldown.remove(((Player) caller).getUniqueId());
            }, 100L);
        } else {
            PlayerUtil.help(caller, PlayerUtil.Module.REPORT, "Please refrain from reporting again.");
        }
    }
}
