package dev.arctic.core.npc;

import dev.arctic.core.Core;
import dev.arctic.core.api.backend.mysql.util.QueryBuilder;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.npc.packet.AI;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Created by Zvijer on 27.8.2017..
 */
public class NPC {

    private static QueryBuilder builder;

    private final String name;
    private final EntityType type;

    private Entity entity;
    private double x, y, z;
    private float yaw, pitch;
    private World world;

    public NPC(String name, EntityType type, Location location) {
        builder = Core.getCore().getDatabaseManager().getQueryBuilder();
        this.name = name;
        this.type = type;
        if (!builder.exists("npc", new String[] {"name", name})) {
            this.world = location.getWorld();
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();

            builder.insert("npc (name, type, x, y, z, yaw, pitch, world)", name, type, x, y, z, yaw, pitch, world.getName());
        }
        loadData();
    }

    private void loadData() {
        this.world = Bukkit.getWorld((String) builder.fetch("world", "npc", new String[] {"name", name}));
        this.x = builder.fetch("x", "npc", new String[] {"name", name});
        this.y = builder.fetch("y", "npc", new String[] {"name", name});
        this.z = builder.fetch("z", "npc", new String[] {"name", name});
        this.yaw = builder.fetch("yaw", "npc", new String[] {"name", name});
        this.pitch = builder.fetch("pitch", "npc", new String[] {"name", name});
    }

    public void spawn() {
        if (entity != null)
            return;
        final Location location = new Location(world, x, y, z, yaw, pitch);
        this.entity = this.world.spawnEntity(location, type);
        this.entity.setCustomNameVisible(true);
        this.entity.setCustomName(StringUtil.color(this.name));
        new AI(this.entity, false);
    }

    public void remove(boolean packet) {
        if (!packet) {
            if (entity != null) {
                this.entity.remove();
            } else {
                throw new NullPointerException("Entity is null.");
            }
        } else {
            if (entity != null) {
                final PacketPlayOutEntityDestroy obj = new PacketPlayOutEntityDestroy(entity.getEntityId());
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    Bukkit.getOnlinePlayers().forEach(p -> ((CraftPlayer) p).getHandle().playerConnection.sendPacket(obj));
                } else {
                    Bukkit.getOnlinePlayers().stream().findFirst().ifPresent(p -> ((CraftPlayer) p).getHandle().playerConnection.sendPacket(obj));
                }
            } else {
                throw new NullPointerException("Entity is null.");
            }
        }
    }
}
