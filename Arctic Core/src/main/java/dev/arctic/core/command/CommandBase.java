package dev.arctic.core.command;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 28.7.2017..
 */
public abstract class CommandBase implements CommandExecutor {

    private @Getter String command;
    private @Getter Rank rank;
    private @Getter int length;

    public CommandBase(String command, int length, Rank rank) {
        this.command = command;
        this.length = length;
        this.rank = rank;
    }

    public abstract void run(CommandSender caller, String[] args);

    @Override
    public boolean onCommand(CommandSender caller, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(getCommand())) {
            if (caller instanceof Player) {
                final Player player = (Player) caller;
                Rank current = Core.getCore().getAccountCache().getPlayer(player.getUniqueId()).getRank();

                if (!current.has(rank)) {
                    PlayerUtil.help(player, PlayerUtil.Module.PERMISSIONS, String.format("Insufficient rank privileges (%s).", rank.getName()));
                    return true;
                }
            }

            run(caller, args);
            return true;
        }
        return true;
    }
}
