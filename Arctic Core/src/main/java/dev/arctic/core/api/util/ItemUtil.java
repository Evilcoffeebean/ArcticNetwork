package dev.arctic.core.api.util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Zvijer on 15.8.2017..
 */
public class ItemUtil {

    private Material type;
    private String name;
    private List<String> description;

    public ItemUtil(Material type, String name, String... description) {
        this.type = type;
        this.name = name;
        this.description = Arrays.asList(description);
    }

    public ItemStack build() {
        return build((byte) -1);
    }

    public ItemStack build(byte b) {
        ItemStack item = b != -1 ? new ItemStack(type, 1, b) : new ItemStack(type, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(StringUtil.color(name));
        if (description != null) {
            List<String> lore = Lists.newArrayList();
            description.forEach(line -> lore.add(StringUtil.color(line)));

            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack buildSkull(final String name) {
        ItemStack skull = new ItemStack(type, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(name);
        skullMeta.setDisplayName(StringUtil.color(name));
        if (description != null) {
            List<String> lore = Lists.newArrayList();
            description.forEach(line -> lore.add(StringUtil.color(line)));
            skullMeta.setLore(lore);
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static ItemStack staticSkull(String owner, String displayName, String... desc) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(owner);
        meta.setDisplayName(StringUtil.color(displayName));

        if (desc != null) {
            List<String> lore = Lists.newArrayList();
            Stream.of(desc).forEach(line -> lore.add(StringUtil.color(line)));
            meta.setLore(lore);
        }
        skull.setItemMeta(meta);
        return skull;
    }
}
