package dev.arctic.lobby.gui;

import dev.arctic.core.api.util.GUIBuilder;
import dev.arctic.core.api.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CosmeticsGUI extends GUIBuilder {

    public CosmeticsGUI(Player player) {
        super(player, 27, "&b&lMy Cosmetics");
    }

    @Override
    public void build() {
        final String[] design = {
                "", "", "", "", "", "", "", "", "",
                "", "", "p", "", "d", "", "g", "", "",
                "", "", "", "", "", "", "", "", "",
        };

        for (int i = 0; i < design.length; i++) {
            switch (design[i]) {
                case "":
                    set(i, new ItemUtil(Material.STAINED_GLASS_PANE, " ", " ").build((byte) 3));
                    continue;
                case "p":
                    set(i, new ItemUtil(Material.BLAZE_POWDER, "&6Particle Effects", "&fList of your particle effects.", "&7[Right Click]").build());
                    continue;
                case "d":
                    set(i, new ItemUtil(Material.CAKE, "&dDisguises", "&fList of your disguises.", "&7[Right Click]").build());
                    continue;
                case "g":
                    set(i, new ItemUtil(Material.FIREWORK, "&cGadgets", "&fList of your gadgets", "&7[Right Click]").build());
                    continue;
            }
        }
        open();
    }
}
