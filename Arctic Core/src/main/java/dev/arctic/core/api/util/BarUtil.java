package dev.arctic.core.api.util;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Zvijer on 16.8.2017..
 */
public class BarUtil {

    private @Getter static Map<UUID, Bar> barMap = Maps.newConcurrentMap();

    public static void setBar(Player player, String msg) {
        if (!barMap.containsKey(player.getUniqueId())) {
            barMap.put(player.getUniqueId(), new Bar(player, msg));
        }
    }

    public static void remove(Player player) {
        if (barMap.containsKey(player.getUniqueId())) {
            barMap.remove(player.getUniqueId());
        }
    }

    public static void clear() {
        if (!barMap.isEmpty())
            barMap.clear();
    }

    public static class Bar {
        private Player player;
        private String msg;
        private EntityWither wither;

        public Bar(Player player, String msg) {
            this.player = player;
            this.msg = msg;
            update();
        }

        public void update() {
            Vector vector = player.getLocation().getDirection();
            Location location = player.getLocation().add(vector.multiply(20));

            remove();

            WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
            wither = new EntityWither(nmsWorld);
            wither.setLocation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
            wither.setCustomName(StringUtil.color(msg));
            wither.setInvisible(true);

            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(wither);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }

        private void remove() {
            if (wither != null) {
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(wither.getId());
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static class BarTask implements Runnable {

        private static final String[] BAR = {
                "&b&lWelcome to the Arctic Network!",
                "&7We're currently in &a&lBETA&7!",
                "&7Need help? Ask a staff member.",
                "&7Report all bugs on the &b/forums&7!"
        };

        private String random(String[] s) {
            int index = new Random().nextInt(s.length);
            return s[index];
        }

        @Override
        public void run() {
            Bukkit.getOnlinePlayers().forEach(p -> {
                BarUtil.setBar(p, random(BAR));
                BarUtil.getBarMap().values().forEach(Bar::update);
            });
        }
    }
}
