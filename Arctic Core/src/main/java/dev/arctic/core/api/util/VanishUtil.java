package dev.arctic.core.api.util;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Created by Zvijer on 19.8.2017..
 */
public class VanishUtil {

    private @Getter static List<UUID> vanished;

    public VanishUtil() {
        vanished = Lists.newArrayList();
    }

    public boolean isHidden(Player player) {
        return vanished.contains(player.getUniqueId());
    }

    public void hidePlayer(Player player) {
        vanished.add(player.getUniqueId());
        Bukkit.getOnlinePlayers().stream().filter(PlayerUtil::isStaff).forEach(p -> p.hidePlayer(player));
    }

    public void showPlayer(Player player) {
        vanished.remove(player.getUniqueId());
        Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));
    }

    public void clearCache() {
        if (!vanished.isEmpty())
            vanished.clear();
    }
}
