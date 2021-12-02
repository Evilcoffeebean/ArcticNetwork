package dev.arctic.core.event.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * Created by Zvijer on 20.8.2017..
 */
public class EntityInteractEvent implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onNPCDelete(final PlayerInteractAtEntityEvent e) {

    }
}
