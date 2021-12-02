package dev.arctic.core.api.util;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Zvijer on 28.7.2017..
 */
public class PlayerUtil {

    public static enum Module {
        CHAT,
        EVENT,
        SERVER,
        PUNISH,
        NPC,
        DATABASE,
        CLIENT,
        RANK,
        PERMISSIONS,
        ECONOMY,
        REPORT;

        public static String toString(final Module module) {
            String result = "&b%s";
            switch (module) {
                case CHAT:
                    result = String.format(result, "Chat");
                    break;
                case EVENT:
                    result = String.format(result, "Event");
                    break;
                case SERVER:
                    result = String.format(result, "Server");
                    break;
                case PUNISH:
                    result = String.format(result, "Punish");
                    break;
                case NPC:
                    result = String.format(result, "NPC");
                    break;
                case DATABASE:
                    result = String.format(result, "Database");
                    break;
                case CLIENT:
                    result = String.format(result, "Client");
                    break;
                case RANK:
                    result = String.format(result, "Rank");
                    break;
                case PERMISSIONS:
                    result = String.format(result, "Permissions");
                    break;
                case ECONOMY:
                    result = String.format(result, "Economy");
                    break;
                case REPORT:
                    result = String.format(result, "Report");
                    break;
            }
            return result;
        }
    }

    public static void staff(Player dispatcher, String message) {
        Bukkit.getOnlinePlayers().stream().filter(PlayerUtil::isStaff).forEach(p -> {
            p.sendMessage(StringUtil.color(String.format("&b&lSTAFF> %s%s&7: &f%s",
                    Core.getCore().getAccountCache().getPlayer(dispatcher.getUniqueId()).getRank().getName(true),
                    dispatcher.getName(),
                    message)));
        });
    }

    public static void report(Player dispatcher, UUID uuid, String reason) {
        Bukkit.getOnlinePlayers().stream().filter(PlayerUtil::isStaff).forEach(p -> {
            help(p, Module.REPORT, "Report issued. Details are as follows;");
            help(p, Module.REPORT, String.format("Issuer: &e%s", dispatcher.getName()));
            help(p, Module.REPORT, String.format("Date: &e%s", new Date()));
            help(p, Module.REPORT, String.format("Violator: &e%s", Bukkit.getPlayer(uuid).getName()));
            help(p, Module.REPORT, String.format("Reason: &e%s", reason));
        });
    }

    public static void help(CommandSender caller, Module module, String body) {
        help(caller, Module.toString(module), body);
    }

    public static void help(CommandSender caller, String module, String body) {
        caller.sendMessage(StringUtil.color(module + "> &7" + body));
    }

    public static void playSound(Player player, Sound sound) {
        playSound(player.getLocation(), sound);
    }

    public static void playSound(Location location, Sound sound) {
        location.getWorld().playSound(location, sound, 1f, 1f);
    }

    public static boolean isStaff(Player player) {
        return Core.getCore().getAccountCache().getPlayer(player.getUniqueId()).getRank().has(Rank.HELPER);
    }

    public static String getName(UUID uuid) {
        if (Bukkit.getPlayer(uuid) != null) return Bukkit.getPlayer(uuid).getName();
        if (!Bukkit.getOfflinePlayer(uuid).hasPlayedBefore()) return "";
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    public static void kick(Player p, String reason) {
        if (p != null)
            p.kickPlayer(StringUtil.color(reason));
    }
}
