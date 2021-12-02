package dev.arctic.lobby.spawn;

import dev.arctic.core.Core;
import dev.arctic.core.api.backend.mysql.util.QueryBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Zvijer on 15.8.2017..
 */
public class Spawn {

    private @Getter double x, y, z;
    private @Getter float yaw, pitch;
    private @Getter World world;

    public Spawn(final Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld();

        SpawnManager.setSpawn(location);
    }

    public static class SpawnManager {

        public static void setSpawn(final Location location) {
            final QueryBuilder builder = Core.getCore().getDatabaseManager().getQueryBuilder();
            if (builder.exists("spawn", null)) return;
            builder.insert("spawn (x,y, z, yaw, pitch, world)", location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
        }

        public static Location getSpawn() {
            QueryBuilder builder = Core.getCore().getDatabaseManager().getQueryBuilder();

            double x = builder.fetch("x", "spawn", null);
            double y = builder.fetch("y", "spawn", null);
            double z = builder.fetch("z", "spawn", null);

            float yaw = builder.fetch("yaw", "spawn", null);
            float pitch = builder.fetch("pitch", "spawn", null);

            World world = Bukkit.getWorld((String) builder.fetch("world", "spawn", null));

            return new Location(world, x, y, z, yaw, pitch);
        }
    }
}
