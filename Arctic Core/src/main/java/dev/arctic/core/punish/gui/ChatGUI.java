package dev.arctic.core.punish.gui;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.GUIBuilder;
import dev.arctic.core.api.util.ItemUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.punish.Durations;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChatGUI extends GUIBuilder {

    private Player player;

    public ChatGUI(Player player) {
        super(player, 27, "&c&lChoose a severity...");
        this.player = player;
    }

    @Override
    public void build() {
        final Rank current = Core.getCore().getAccountCache().getPlayer(player.getUniqueId()).getRank();

        final String[] helper = {
                "o", "o", "o", "o", "o", "o", "o", "o", "o",
                "o", "", "", "1", "2", "3", "", "o",
                "o", "o", "o", "o", "o", "o", "o", "o", "o"
        };

        final String[] mod = {
                "o", "o", "o", "o", "o", "o", "o", "o", "o",
                "o", "", "1", "2", "3", "4", "", "o",
                "o", "o", "o", "o", "o", "o", "o", "o", "o"
        };

        final String[] normal = {
                "o", "o", "o", "o", "o", "o", "o", "o", "o",
                "o", "p", "1", "2", "3", "4", "5", "p", "o",
                "o", "o", "o", "o", "o", "o", "o", "o", "o"
        };

        final ItemStack spacer = new ItemUtil(Material.STAINED_GLASS_PANE, "&4&ka", "&4&ka").build((byte) 14);
        final ItemStack powder = new ItemUtil(Material.BLAZE_POWDER, "&6&l&ka", "&6&l&ka").build();
        final ItemStack first = new ItemUtil(Material.INK_SACK, "&aSeverity 1", String.format("&fDuration: &e%s", StringUtil.duration(Durations.CHAT_SEVERITY_1.getDuration()))).build((byte) 10);
        final ItemStack second = new ItemUtil(Material.INK_SACK, "&aSeverity 2", String.format("&fDuration: &e%s", StringUtil.duration(Durations.CHAT_SEVERITY_2.getDuration()))).build((byte) 2);
        final ItemStack third = new ItemUtil(Material.INK_SACK, "&aSeverity 3", String.format("&fDuration: &e%s", StringUtil.duration(Durations.CHAT_SEVERITY_3.getDuration()))).build((byte) 11);
        final ItemStack fourth = new ItemUtil(Material.INK_SACK, "&aSeverity 4", String.format("&fDuration: &e%s", StringUtil.duration(Durations.CHAT_SEVERITY_4.getDuration()))).build((byte) 14);
        final ItemStack fifth = new ItemUtil(Material.INK_SACK, "&aSeverity 5", String.format("&fDuration: &e%s", StringUtil.duration(Durations.CHAT_SEVERITY_5.getDuration()))).build((byte) 1);

        if (current == Rank.HELPER) {
            for (int i = 0; i < helper.length; i++) {
                switch (helper[i]) {
                    case "o":
                        set(i, spacer);
                        continue;
                    case "": continue;
                    case "1":
                        set(i, first);
                        continue;
                    case "2":
                        set(i, second);
                        continue;
                    case "3":
                        set(i, third);
                        continue;
                }
            }
            open();
        } else if (current == Rank.MOD) {
            for (int i = 0; i < mod.length; i++) {
                switch (mod[i]) {
                    case "o":
                        set(i, spacer);
                        continue;
                    case "": continue;
                    case "1":
                        set(i, first);
                        continue;
                    case "2":
                        set(i, second);
                        continue;
                    case "3":
                        set(i, third);
                        continue;
                    case "4":
                        set(i, fourth);
                        continue;
                }
            }
            open();
        } else if (current.has(Rank.SNR_MOD)) {
            for (int i = 0; i < normal.length; i++) {
                switch (normal[i]) {
                    case "o":
                        set(i, spacer);
                        continue;
                    case "p":
                        set(i, powder);
                        continue;
                    case "": continue;
                    case "1":
                        set(i, first);
                        continue;
                    case "2":
                        set(i, second);
                        continue;
                    case "3":
                        set(i, third);
                        continue;
                    case "4":
                        set(i, fourth);
                        continue;
                    case "5":
                        set(i, fifth);
                        continue;
                }
            }
            open();
        }
    }
}
