package dev.arctic.core.api.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Created by Zvijer on 17.8.2017..
 */
public class JSONUtil {

    public static enum Color {
        BLACK("black"),
        DARK_BLUE("dark_blue"),
        DARK_GREEN("dark_green"),
        DARK_AQUA("dark_aqua"),
        DARK_RED("dark_red"),
        DARK_PURPLE("dark_purple"),
        GOLD("gold"),
        GRAY("gray"),
        DARK_GRAY("dark_gray"),
        BLUE("blue"),
        GREEN("green"),
        AQUA("aqua"),
        RED("red"),
        LIGHT_PURPLE("light_purple"),
        YELLOW("yellow"),
        WHITE("white");

        private String minecraftString;

        Color(String minecraftString) {
            this.minecraftString = minecraftString;
        }

        @Override
        public String toString() {
            return minecraftString;
        }
    }

    public static enum ClickEvent {
        RUN_COMMAND("run_command"),
        SUGGEST_COMMAND("suggest_command"),
        OPEN_URL("open_url"),
        CHANGE_PAGE("change_page");

        private String string;

        ClickEvent(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return this.string;
        }
    }

    public static enum HoverEvent {
        SHOW_TEXT("show_text"),
        SHOW_ITEM("show_item"),
        SHOW_ACHIEVEMENT("show_achievement");

        private String string;

        HoverEvent(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return this.string;
        }
    }

    private StringBuilder builder;

    public JSONUtil(String text) {
        this(new StringBuilder(), text);
    }

    public JSONUtil(StringBuilder builder, String text) {
        this.builder = builder;
        builder.append("{\"text\":\"" + text + "\"");
    }

    public JSONUtil color(String color) {
        builder.append(", color:" + color);
        return this;
    }

    public JSONUtil bold() {
        builder.append(", bold:true");
        return this;
    }

    public JSONUtil italic() {
        builder.append(", italic:true");
        return this;
    }

    public JSONUtil underlined() {
        builder.append(", underlined:true");
        return this;
    }

    public JSONUtil strikethrough() {
        builder.append(", strikethrough:true");
        return this;
    }

    public JSONUtil obfuscated() {
        builder.append(", obfuscated:true");
        return this;
    }

    public JSONUtil click(String action, String value) {
        builder.append(", \"clickEvent\":{\"action\":\"" + action + "\",\"value\":\"" + value + "\"}");
        return this;
    }

    public JSONUtil hover(String action, String value) {
        builder.append(", \"hoverEvent\":{\"action\":\"" + action + "\",\"value\":\"" + value + "\"}");
        return this;
    }

    public JSONUtil click(ClickEvent event, String value) {
        return click(event.toString(), value);
    }

    public JSONUtil hover(HoverEvent event, String value) {
        return hover(event.toString(), value);
    }

    public JSONUtil color(Color color) {
        return color(color.toString());
    }

    public String toString() {
        builder.append("}");
        return builder.toString();
    }

    public void sendJson(Player p, boolean reflection) {
        if (reflection) {
            final String COMPONENT = "IChatBaseComponent";
            final String PACKET = "PacketPlayOutChat";

            Object component, packet;
            Constructor<?> constructor;

            try {
                component = ReflectionUtil.getNMS(COMPONENT).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, toString());
                constructor = ReflectionUtil.getNMS(PACKET).getConstructor(ReflectionUtil.getNMS(COMPONENT));
                packet = constructor.newInstance(component);

                ReflectionUtil.sendPacket(p, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + toString());
        }
    }
}
