package dev.arctic.core.event.custom;

import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.object.ArcticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class AccountLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private ArcticPlayer obj;

    public AccountLoadEvent(final Player player) {
        this(player.getUniqueId());
    }

    public AccountLoadEvent(final UUID uuid) {
        this.obj = Core.getCore().getAccountCache().getPlayer(uuid);
        Bukkit.getPlayer(obj.getUuid()).setPlayerListName(obj.getRank().getName(true) + obj.getName());
    }

    public Rank getRank() {
        return obj.getRank();
    }

    public UUID getUUID() {
        return obj.getUuid();
    }

    public String getName() {
        return obj.getName();
    }
}
