package dev.arctic.lobby.event;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.ServerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.lobby.Lobby;
import dev.arctic.lobby.gui.CosmeticsGUI;
import dev.arctic.lobby.gui.GamesGUI;
import dev.arctic.lobby.item.LobbyItems;
import dev.arctic.lobby.spawn.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Zvijer on 15.8.2017..
 */
public class LobbyEvents implements Listener {

    private String random(String[] array) {
        Random r = new Random();
        int index = r.nextInt(array.length);

        return array[index];
    }

    private static final String[] commandBlacklist = {
            "pl", "plugins", "PL", "PLUGINS",
            "bukkit", "BUKKIT",
            "?",
            "ver", "VER", "version", "VERSION",
            "help", "HELP",
            "me", "ME",
            "minecraft", "MINECRAFT"
    };

    @EventHandler (priority = EventPriority.HIGH)
    public void onCommandProcess(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().replaceAll("/", "").split(" ")[0];
        for (String s : commandBlacklist) {
            if (cmd.startsWith(s) || cmd.contains(s)) {
                if (!Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId()).getRank().has(Rank.OWNER)) {
                    e.setCancelled(true);
                    PlayerUtil.help(e.getPlayer(), PlayerUtil.Module.CLIENT, "You don't have permission. This requires " + Rank.DEV.getRankColor() + Rank.DEV.getName() + "&6+&7.");
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onQuit(final PlayerQuitEvent e) {
        e.setQuitMessage(StringUtil.color(
                String.format("&7[&c-&7] %s%s", Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId()).getRank().getName(true), e.getPlayer().getName())
        ));
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);
            if (e.getItem() != null && e.getItem().getType() != Material.AIR) {
                switch (e.getItem().getType()) {
                    case CAKE:
                        new CosmeticsGUI(e.getPlayer()).build();
                        break;
                    case COMPASS:
                        new GamesGUI(e.getPlayer()).build();
                        break;
                    case SKULL_ITEM:
                        PlayerUtil.help(e.getPlayer(), PlayerUtil.Module.SERVER, "Feature isn't finished yet.");
                        break;
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onJoin(final PlayerJoinEvent e) {
        e.setJoinMessage(StringUtil.color(
                String.format("&7[&b+&7] %s%s", Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId()).getRank().getName(true), e.getPlayer().getName())
        ));

        try {
            //e.getPlayer().teleport(Spawn.SpawnManager.getSpawn());
            e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.380, 71.00000, 0.176));
        } catch (NullPointerException npe) {
            System.err.println("Spawn> Couldn't fetch main spawn from database!");
        }

        final String[] messages = new String[] {
                "&fWelcome to the &bArctic &fNetwork!",
                "&fDon't hesitate to ask for help :)",
                "&6ALPHA &fRelease",
                "&fOnly one rule - have fun &c<3"
        };

        new BukkitRunnable() {
            int t = 0;
            @Override
            public void run() {
                t++;
                switch (t) {
                    case 1:
                        ServerUtil.sendTitle(e.getPlayer(), "&b&lArctic &f&lNetwork", random(messages));
                        break;
                    case 6:
                        ServerUtil.sendTitle(e.getPlayer(), "&b&lArctic &f&lNetwork", random(messages));
                        break;
                    case 11:
                        ServerUtil.sendTitle(e.getPlayer(), "&b&lArctic &f&lNetwork", random(messages));
                        break;
                }

                if (t >= 11) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Lobby.getLobby(), 0L, 20L);

        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setItem(2, LobbyItems.COSMETICS);
        e.getPlayer().getInventory().setItem(4, LobbyItems.GAMES);
        e.getPlayer().getInventory().setItem(6, LobbyItems.PLAYER_ACCOUNT(e.getPlayer()));
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onDrop(final PlayerDropItemEvent e) {
        if (!Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId()).getRank().has(Rank.ADMIN)) {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onHunger(final FoodLevelChangeEvent e) {
        e.setCancelled(true);
        e.setFoodLevel(20);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onCreatureSpawn(final CreatureSpawnEvent e) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            e.setCancelled(true);
        }
    }

        @EventHandler (priority = EventPriority.HIGH)
    public void entityCombust(final EntityCombustEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onBlockIgnite(final BlockIgniteEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDamage(final EntityDamageEvent e) {
        e.setCancelled(true);
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID && e.getEntity() instanceof Player) {
            e.getEntity().teleport(Spawn.SpawnManager.getSpawn());
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onItemDamage(PlayerItemDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onCraft(CraftItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onItemPickup(PlayerPickupItemEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void portalEvent(PlayerPortalEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onJumpPad(final PlayerMoveEvent e) {
        if (e.getFrom().getBlock().getRelative(BlockFace.SELF).getType() == Material.STONE_PLATE
                || e.getFrom().getBlock().getRelative(BlockFace.SELF).getType() == Material.GOLD_PLATE
                || e.getFrom().getBlock().getRelative(BlockFace.SELF).getType() == Material.IRON_PLATE
                || e.getFrom().getBlock().getRelative(BlockFace.SELF).getType() == Material.WOOD_PLATE) {

            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(3));
            e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.0D, e.getPlayer().getVelocity().getZ()));
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onBlockFade(final BlockFadeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onBurn(final BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onCropStomp(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getBlockFace() == BlockFace.SELF) {
                if (event.getClickedBlock().getType() == Material.SOIL && event.getClickedBlock().getType() == Material.CROPS) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onBreak(final BlockBreakEvent e) {
        if (!Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId()).getRank().has(Rank.ADMIN)) {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlace(final BlockPlaceEvent e) {
        if (!Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId()).getRank().has(Rank.ADMIN)) {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onLeafDecay(final LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onExtinguish(final PlayerInteractEvent e) {
        switch (e.getPlayer().getTargetBlock((HashSet<Byte>) null, 5).getType()) {
            case FIRE:
                e.setCancelled(true);
                break;
            case FURNACE:
                e.setCancelled(true);
                break;
            case BURNING_FURNACE:
                e.setCancelled(true);
                break;
            case CHEST:
                e.setCancelled(true);
                break;
            case ENDER_CHEST:
                e.setCancelled(true);
                break;
            case TRAPPED_CHEST:
                e.setCancelled(true);
                break;
        }
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onItemRotate(final InventoryDragEvent e) {
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onItemRotate(final InventoryClickEvent e) {
        if (e.getClickedInventory() != null && e.getClickedInventory().getName().contains("cosmetics")) e.setCancelled(true);
        if (e.getClickedInventory() != null && e.getClickedInventory().getName().contains("games")) e.setCancelled(true);
        if (e.getClickedInventory() != null && e.getClickedInventory().getName().contains("preferences")) e.setCancelled(true);
        if (!Core.getCore().getAccountCache().getPlayer(e.getWhoClicked().getUniqueId()).getRank().has(Rank.ADMIN))
            e.setCancelled(true);
    }
}
