package dev.arctic.core.event.bukkit.punish;

import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.command.account.PunishCommand;
import dev.arctic.core.punish.gui.BanGUI;
import dev.arctic.core.punish.gui.ChatGUI;
import dev.arctic.core.punish.gui.GeneralGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import java.time.Instant;

public class PunishClickEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClick(final InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().getName().contains("Sentence")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    switch (e.getCurrentItem().getType()) {
                        case ENCHANTED_BOOK:
                            player.closeInventory();
                            new ChatGUI(player).build();
                            break;
                        case DRAGON_EGG:
                            player.closeInventory();
                            new GeneralGUI(player).build();
                            break;
                        case GOLD_SWORD:
                            player.closeInventory();
                            new BanGUI(player).build();
                            break;
                        case FEATHER:
                            player.closeInventory();
                            if (Bukkit.getPlayer(PunishCommand.target.getUniqueId()) != null) {
                                PlayerUtil.kick(Bukkit.getPlayer(PunishCommand.target.getUniqueId()),
                                        StringUtil.join("&cYou were kicked off the server", "&cPunisher: &e" + player.getName(), "&cDate: &e" + Instant.now().toString(), "&cReason: &e'" + PunishCommand.reason + "'"));
                                return;
                            } else {
                                PlayerUtil.help(player, PlayerUtil.Module.PUNISH, String.format("&c'&e%s&c' couldn't be found online.", PunishCommand.target.getName()));
                                return;
                            }
                        case GLOWSTONE_DUST:
                            player.closeInventory();
                            if (Bukkit.getPlayer(PunishCommand.target.getUniqueId()) != null) {
                                Bukkit.getPlayer(PunishCommand.target.getUniqueId()).sendMessage(StringUtil.join("&cYou were warned!", "&cPunisher: &e" + player.getName(), "&cDate: &e" + Instant.now().toString(), "&cReason: &e'" + PunishCommand.reason + "'"));
                                return;
                            } else {
                                PlayerUtil.help(player, PlayerUtil.Module.PUNISH, String.format("&c'&e%s&c' couldn't be found online.", PunishCommand.target.getName()));
                                return;
                            }
                    }
                }
            }
        }
    }
}
