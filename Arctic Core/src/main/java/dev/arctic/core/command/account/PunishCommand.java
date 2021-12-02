package dev.arctic.core.command.account;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import dev.arctic.core.object.ArcticPlayer;
import dev.arctic.core.punish.gui.PunishGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand extends CommandBase {

    public static OfflinePlayer target;
    public static String reason;

    public PunishCommand() {
        super("punish", 2, Rank.HELPER);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length < getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.PUNISH, "Usage: &e/punish <player> <reason>");
            return;
        }
        target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            PlayerUtil.help(caller, PlayerUtil.Module.PUNISH, String.format("'&e%s&7' has never joined before.", args[0]));
            return;
        }

        final ArcticPlayer obj = Core.getCore().getAccountCache().getPlayer(target.getUniqueId());
        reason = StringUtil.join(1, args);

        new PunishGUI((Player) caller, PlayerUtil.getName(obj.getUuid()), reason).build();
    }
}
