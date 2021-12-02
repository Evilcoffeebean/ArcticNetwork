package dev.arctic.lobby.gui;

import dev.arctic.core.api.util.GUIBuilder;
import dev.arctic.core.api.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GamesGUI extends GUIBuilder {

    public GamesGUI(Player player) {
        super(player, 54, "&b&lArctic Game Selector");
    }

    @Override
    public void build() {
        final String[] design = {
                "o", "o", "o", "o", "o", "o", "o", "o", "o",
                "o", "sg", "", "sb", "", "mz", "", "gr", "o",
                "o", "", "", "", "", "", "", "", "o",
                "o", "ms", "", "sf", "", "k", "", "p", "o",
                "o", "", "", "", "", "", "", "", "o",
                "o", "o", "o", "o", "o", "o", "o", "o", "o",
        };

        for (int i = 0; i < design.length; i++) {
            switch (design[i]) {
                case "o":
                    set(i, new ItemUtil(Material.STAINED_GLASS_PANE, " ", " ").build((byte) 3));
                    continue;
                case "sg":
                    set(i, new ItemUtil(Material.IRON_SWORD, "&eSurvival Games", "&fTest your abilities in a hand-to-hand match", "&fagainst other players. Who will win?", " ", "&7[Right Click]").build());
                    continue;
                case "sb":
                    set(i, new ItemUtil(Material.IRON_SPADE, "&eSky Block", "&fA fast paced game set in the", "&fmystical realm of the skies. Test your prowess.", " ", "&7[Right Click]").build());
                    continue;
                case "mz":
                    set(i, new ItemUtil(Material.TNT, "&eMineZ", "&fA zombie survival type of game for", "&fonly the hardest of veterans.", " ", "&7[Right Click]").build());
                    continue;
                case "gr":
                    set(i, new ItemUtil(Material.FEATHER, "&eGravity Rush", "&fA free falling racing game made for", "&fthose with a passion for speed and winning.", " ", "&7[Right Click]").build());
                    continue;
                case "ms":
                    set(i, new ItemUtil(Material.FIREWORK_CHARGE, "&eMine Strike", "&fCopy of Counter Strike, only adapted to", "&fMinecraft. How will you fare here?", " ", "&7[Right Click]").build());
                    continue;
                case "sf":
                    set(i, new ItemUtil(Material.BEACON, "&eSkyfall", "&fA flying game focused more on dynamic flight", "&faspect than Gravity Rush. Be one with the skies!", " ", "&7[Right Click]").build());
                    continue;
                case "k":
                    set(i, new ItemUtil(Material.IRON_SWORD, "&c&lKingdoms", "&fStill in the works.", " ", "&7[Right Click]").build());
                    continue;
                case "p":
                    set(i, new ItemUtil(Material.IRON_PICKAXE, "&b&lArctic Prison", "&fStill in the works.", " ", "&7[Right Click]").build());
                    continue;
            }
        }
        open();
    }
}
