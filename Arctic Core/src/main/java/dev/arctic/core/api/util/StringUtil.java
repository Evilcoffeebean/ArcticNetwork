package dev.arctic.core.api.util;

import org.bukkit.ChatColor;

import java.util.stream.Stream;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class StringUtil {

    public static String color(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String json(String input) {
        return json(input, true);
    }

    public static String json(String input, boolean color) {
        return "{\"text\":\"" + (color ? color(input) : input) + "\"}";
    }

    public static String join(int index, String... text) {
        final StringBuilder builder = new StringBuilder();
        for (int i = index; i < text.length; i++)
            builder.append(text[i]).append(i >= text.length - 1 ? "" : " ");

        return builder.toString();
    }

    public static String join(final String... data) {
        final StringBuilder builder = new StringBuilder();
        Stream.of(data).forEach(line -> builder.append(line).append("\n"));

        return color(builder.toString());
    }

    public static String level(int d, boolean space) {
        final String container = color("&7[%s&7]");
        if (d < 10) {
            return String.format(container, color("&7" + d)) + (space ? " " : "");
        } else if (d >= 10 && d < 20) {
            return String.format(container, color("&8" + d)) + (space ? " " : "");
        } else if (d >= 20 && d < 35) {
            return String.format(container, color("&e" + d)) + (space ? " " : "");
        } else if (d >= 35 && d < 45) {
            return String.format(container, color("&c" + d)) + (space ? " " : "");
        } else if (d >= 45 && d < 65) {
            return String.format(container, color("&4" + d)) + (space ? " " : "");
        } else if (d >= 65 && d < 75) {
            return String.format(container, color("&9" + d)) + (space ? " " : "");
        } else if (d >= 75 && d < 95) {
            return String.format(container, color("&3" + d)) + (space ? " " : "");
        } else if (d >= 95 && d <= 100) {
            return String.format(container, color("&b" + d)) + (space ? " " : "");
        }
        return null;
    }

    public static String duration(long d) {
        if (d < 24) return d + " Hours";
        else if (d == 24) return "1 Day";
        else if (d % 24 == 0) return d/24 + " Days";
        else if (d == 9999) return "Permanent";
        return Math.round(d/24) + " Days";
    }
}
