package dev.arctic.core.api.rank;

import lombok.Getter;
import org.bukkit.ChatColor;

/**
 * Created by Zvijer on 27.7.2017..
 */
public enum Rank {

    MEMBER("", ChatColor.AQUA),

    //Purchasable donor ranks

    DONOR("Donor", ChatColor.BLUE),
    PREMIUM("Premium", ChatColor.YELLOW),
    VIP("Vip", ChatColor.GREEN),
    LEGEND("Legend", ChatColor.LIGHT_PURPLE),
    ULTIMATE("Ultimate", ChatColor.RED),

    //Miscellaneous ranks

    MOJANG("Mojang", ChatColor.GOLD),
    HOST("Host", ChatColor.DARK_RED),
    TWITCH("Twitch", ChatColor.DARK_PURPLE),
    YOUTUBE("YouTube", ChatColor.RED),

    //Staff ranks

    DESIGNER("Designer", ChatColor.LIGHT_PURPLE),
    BUILDER("Builder", ChatColor.DARK_AQUA),
    HELPER("Helper", ChatColor.YELLOW),
    MOD("Mod", ChatColor.GREEN),
    SNR_MOD("Sr.Mod", ChatColor.DARK_GREEN),
    ADMIN("Admin", ChatColor.GOLD),
    JNR_DEV("Jr.Dev", ChatColor.DARK_PURPLE),
    DEV("Dev", ChatColor.LIGHT_PURPLE),
    MANAGER("Manager", ChatColor.RED),
    OWNER("Owner", ChatColor.BLUE);

    private final String tag;
    private @Getter final ChatColor rankColor;
    private final ChatColor nameColor = ChatColor.AQUA;

    Rank(String tag, ChatColor color) {
        this.tag = tag;
        this.rankColor = color;
    }

    public String getName() {
        return this == MEMBER ? "Default" : tag;
    }

    public String getName(boolean space) {
        if (this == MEMBER)
            return nameColor.toString();

        return rankColor.toString() + ChatColor.BOLD.toString() + tag.toUpperCase() + nameColor + (space ? " " : "");
    }

    public boolean has(Rank rank) {
        return has(rank, (Rank[]) null);
    }

    public boolean has(Rank rank, Rank... specific) {
        if (specific != null) {
            for (Rank r : specific) {
                if (compareTo(r) == 0)
                    return true;
            }
        }

        return compareTo(rank) >= 0;
    }
}
