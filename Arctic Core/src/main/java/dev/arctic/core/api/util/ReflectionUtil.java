package dev.arctic.core.api.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

/**
 * Created by Zvijer on 28.7.2017..
 */
public class ReflectionUtil {

    public static Class<?> getNMS(final String name) {
        Class<?> clazz = null;
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            clazz = Class.forName("net.minecraft.server." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static void broadcastPacket(Object... packet) {
        Bukkit.getOnlinePlayers().forEach(p -> sendPacket(p, packet));
    }

    public static void sendPacket(Player player, Object... packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

            Stream.of(packet).forEach(p -> {
                try {
                    playerConnection.getClass().getMethod("sendPacket", getNMS("Packet")).invoke(playerConnection, p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
