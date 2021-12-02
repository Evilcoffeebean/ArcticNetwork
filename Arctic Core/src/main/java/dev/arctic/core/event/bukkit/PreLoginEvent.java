package dev.arctic.core.event.bukkit;

import dev.arctic.core.Core;
import dev.arctic.core.api.util.BarUtil;
import dev.arctic.core.api.util.ServerUtil;
import dev.arctic.core.event.custom.AccountLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class PreLoginEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onAccountLoad(final AccountLoadEvent e) {
        ServerUtil.setTablist(Bukkit.getPlayer(e.getUUID()),
                "&f&l❄     &b&lArctic &f&lNetwork     ❄",
                "&fPay us a visit at &bwww.arcticnetwork.com");
        String[] tabInfo = new String[] {
                Core.getCore().getAccountCache().getPlayer(e.getUUID()).getRank().getName(true),
                e.getName()
        };
        Bukkit.getPlayer(e.getUUID()).setPlayerListName(tabInfo[0] + tabInfo[1]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLoginAttempt(final PlayerJoinEvent e) {
        ServerUtil.callEvent(new AccountLoadEvent(e.getPlayer()));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(final PlayerQuitEvent e) {
        Core.getCore().getAccountCache().invalidate(e.getPlayer().getUniqueId());
        BarUtil.remove(e.getPlayer());
    }
}
