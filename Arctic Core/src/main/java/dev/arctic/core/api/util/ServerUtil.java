package dev.arctic.core.api.util;

import dev.arctic.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class ServerUtil {

    public static void setTablist(Player player, final String top, final String bottom) {
        Object packet = null;

        try {
            Constructor<?> constructor = ReflectionUtil.getNMS("PacketPlayOutPlayerListHeaderFooter").getConstructor(
                    ReflectionUtil.getNMS("IChatBaseComponent"));
            packet = constructor.newInstance(
                    ReflectionUtil.getNMS("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                            .invoke(null, StringUtil.json(top)));

            Field footer = ReflectionUtil.getNMS("PacketPlayOutPlayerListHeaderFooter").getDeclaredField("b");
            footer.setAccessible(true);
            footer.set(packet,
                    ReflectionUtil.getNMS("IChatBaseComponent")
                            .getDeclaredClasses()[0].getMethod("a", String.class)
                            .invoke(null, StringUtil.json(bottom)));
            footer.setAccessible(!footer.isAccessible());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ReflectionUtil.sendPacket(player, packet);
    }

    public static void sendTitle(Player player, String title, String subtitle) {

        final String packet = "PacketPlayOutTitle";
        final String component = "IChatBaseComponent";

        Object titlePacket, subtitlePacket;
        Object enumTitle, enumSubtitle;
        Object titleComponent, subtitleComponent;
        Constructor<?> titleConstructor, subtitleConstructor;

        try {
            enumTitle = ReflectionUtil.getNMS(packet).getDeclaredClasses()[0].getDeclaredField("TITLE").get(null);
            enumSubtitle = ReflectionUtil.getNMS(packet).getDeclaredClasses()[0].getDeclaredField("SUBTITLE").get(null);

            titleComponent = ReflectionUtil.getNMS(component).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, StringUtil.json(title));
            subtitleComponent = ReflectionUtil.getNMS(component).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, StringUtil.json(subtitle));

            titleConstructor = ReflectionUtil.getNMS(packet).getConstructor(ReflectionUtil.getNMS(packet).getDeclaredClasses()[0], ReflectionUtil.getNMS(component), int.class, int.class, int.class);
            subtitleConstructor = ReflectionUtil.getNMS(packet).getConstructor(ReflectionUtil.getNMS(packet).getDeclaredClasses()[0], ReflectionUtil.getNMS(component), int.class, int.class, int.class);

            titlePacket = titleConstructor.newInstance(enumTitle, titleComponent, 20, 20*5, 20);
            subtitlePacket = subtitleConstructor.newInstance(enumSubtitle, subtitleComponent, 20, 20*5, 20);

            ReflectionUtil.sendPacket(player, titlePacket, subtitlePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void persistActionbar(Player player, String message, int seconds) {
        callSyncRepeat(
                new BukkitRunnable() {
                    double t = 0;
                    @Override
                    public void run() {
                        t += .5;
                        sendActionbar(player, message, (byte) 2); //changeable for debug

                        if (t >= seconds) {
                            this.cancel();
                        }
                    }
                }, seconds);
    }

    public static void sendActionbar(Player player, String message, byte data) {
        final String packet = "PacketPlayOutChat";
        final String component = "IChatBaseComponent";

        Object chatPacket = null;
        Object chatComponent = null;
        Constructor<?> packetConstructor = null;

        try {
            chatComponent = ReflectionUtil.getNMS(component).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, StringUtil.json(message));
            packetConstructor = ReflectionUtil.getNMS(packet).getConstructor(ReflectionUtil.getNMS(component), byte.class);
            chatPacket = packetConstructor.newInstance(chatComponent, data);

            ReflectionUtil.sendPacket(player, chatPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getCore(), runnable);
    }

    public static void callSyncRepeat(BukkitRunnable runnable, int seconds) {
        runnable.runTaskTimer(Core.getCore(), 0L, 20*seconds);
    }

    public static void callEvent(final Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
