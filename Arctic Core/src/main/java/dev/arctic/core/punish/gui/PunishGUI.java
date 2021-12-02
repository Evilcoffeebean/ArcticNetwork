package dev.arctic.core.punish.gui;

import dev.arctic.core.api.util.GUIBuilder;
import dev.arctic.core.api.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PunishGUI extends GUIBuilder {

    private String target;
    private String reason;

    public PunishGUI(Player caller, String target, String reason) {
        super(caller, 36, String.format("&c&l%s's Sentence", target));
        this.target = target;
        this.reason = reason;
    }

    @Override
    public void build() {
        final String[] design = {
                "o", "o", "o", "o", "head", "o", "o", "o", "o",
                "o", "chat", "", "", "general", "", "", "client", "o",
                "o", "", "", "kick", "", "warn", "", "", "o",
                "o", "o", "o", "o", "o", "o", "o", "o", "o"
        };

        final ItemStack spacer = new ItemUtil(Material.STAINED_GLASS_PANE, "&4&ka", "&4&ka").build((byte) 14);
        final ItemStack head = ItemUtil.staticSkull(target, String.format("&e%s", target), String.format("&fReason: &e'%s'", reason));
        final ItemStack chat = new ItemUtil(Material.ENCHANTED_BOOK, "&a&lChat Violation", "&fCalled upon when a player disrespects one", "&fof our server's chat policies.", " ", "&7[Right Click]").build();
        final ItemStack general = new ItemUtil(Material.DRAGON_EGG, "&a&lGeneral Violation", "&fUsually rare, any exploits and misconduct of", "&four server's policies applies here.", " ", "&7[Right Click]").build();
        final ItemStack client = new ItemUtil(Material.GOLD_SWORD, "&a&lClient Violation", "&fMost common of violations, called upon when a player", "&fis caught using a forbidden client/mod.", " ", "&7[Right Click]").build();
        final ItemStack kick = new ItemUtil(Material.FEATHER, "&a&lKick", "&fKicks the player off the server.", " ", "&7[Right Click]").build();
        final ItemStack warn = new ItemUtil(Material.GLOWSTONE_DUST, "&a&lWarn", "&fWarns the player for misbehaving.", " ", "&7[Right Click]").build();

        for (int i = 0; i < design.length; i++) {
            switch (design[i]) {
                case "": continue;
                case "o":
                    set(i, spacer);
                    continue;
                case "head":
                    set(i, head);
                    continue;
                case "chat":
                    set(i, chat);
                    continue;
                case "general":
                    set(i, general);
                    continue;
                case "client":
                    set(i, client);
                    continue;
                case "kick":
                    set(i, kick);
                    continue;
                case "warn":
                    set(i, warn);
                    continue;
            }
        }
        open();
    }
}
