package dev.arctic.core.command.npc;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.CommandBase;
import dev.arctic.core.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

/**
 * Created by Zvijer on 28.8.2017..
 */
public class DeleteNPC extends CommandBase {

    public DeleteNPC() {
        super("deletenpc", 1, Rank.DEV);
    }

    private NPC getNPC(final String name) {
        final EntityType type = EntityType.valueOf(Core.getCore().getDatabaseManager().getQueryBuilder().fetch("type", "npc", new String[] {"name", name}));
        final double x = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("x", "npc", new String[] {"name", name});
        final double y = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("y", "npc", new String[] {"name", name});
        final double z = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("z", "npc", new String[] {"name", name});
        final float yaw = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("yaw", "npc", new String[] {"name", name});
        final float pitch = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("pitch", "npc", new String[] {"name", name});
        final World world = Bukkit.getWorld((String) Core.getCore().getDatabaseManager().getQueryBuilder().fetch("world", "npc", new String[] {"name", name}));

        final Location location = new Location(world, x, y, z, yaw, pitch);

        return new NPC(name, type, location);
    }

    @Override
    public void run(CommandSender caller, String[] args) {
        if (args.length < getLength()) {
            PlayerUtil.help(caller, PlayerUtil.Module.NPC, "Usage: &e/deletenpc <name>");
            return;
        }
        final String name = StringUtil.join(0, args);
        getNPC(name).remove(true);
        Core.getCore().getDatabaseManager().getQueryBuilder().delete("npc", new String[] {"name", name});
    }
}
