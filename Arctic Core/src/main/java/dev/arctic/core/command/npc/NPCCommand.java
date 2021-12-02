package dev.arctic.core.command.npc;

import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import dev.arctic.core.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Created by Zvijer on 19.8.2017..
 */
public class NPCCommand extends CommandBase {

    public NPCCommand() {
        super("npc", 3, Rank.JNR_DEV);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length != getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.NPC, "Usage: &e/npc add <type> <name>");
            return;
        }
        if (args[0].equalsIgnoreCase("add")) {
            EntityType type = null;
            try {
                type = EntityType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                PlayerUtil.help(caller, PlayerUtil.Module.NPC, "Invalid entity parameter.");
            }
            final String name = StringUtil.join(2, args);
            new NPC(name, type , ((Player) caller).getLocation()).spawn();
            PlayerUtil.help(caller, PlayerUtil.Module.NPC, "Saving NPC at your location.");
        }
    }
}
