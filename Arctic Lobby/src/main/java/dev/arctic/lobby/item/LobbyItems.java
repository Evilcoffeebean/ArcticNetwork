package dev.arctic.lobby.item;

import dev.arctic.core.api.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Zvijer on 16.8.2017..
 */
public class LobbyItems {

    public static final ItemStack COSMETICS = new ItemUtil(Material.CAKE, "&b&lCosmetics").build();
    public static final ItemStack GAMES = new ItemUtil(Material.COMPASS, "&6&lGames").build();

    private LobbyItems() {
    }

    public static ItemStack PLAYER_ACCOUNT(final Player player) {
        return new ItemUtil(Material.SKULL_ITEM, "&a&lPreferences").buildSkull(player.getName());
    }
}
