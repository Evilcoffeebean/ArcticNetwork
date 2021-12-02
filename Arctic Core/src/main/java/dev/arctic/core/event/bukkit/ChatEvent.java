package dev.arctic.core.event.bukkit;

import com.google.common.collect.Lists;
import dev.arctic.core.Core;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.PlayerUtil;
import dev.arctic.core.api.util.StringUtil;
import dev.arctic.core.object.ArcticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.UUID;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class ChatEvent implements Listener {

    private static final String[] blacklist = {"fuck", "shit", "cunt", "dick", "nigger", "whore", "wanker", "faggot", "bitch"};
    private List<UUID> cooldown = Lists.newArrayList();

    @EventHandler
    public void onSpeak(final AsyncPlayerChatEvent e) {
        for (String word : blacklist) {
            if (e.getMessage().startsWith(word) || e.getMessage().contains(word)) {
                if (!Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId()).isStaff()) {
                    e.setCancelled(true);
                    PlayerUtil.help(e.getPlayer(), PlayerUtil.Module.CHAT, "You aren't allowed to use profanity in chat. \nCircumventing the chat filter is bannable! \nPlease read our &e/rules&7!");
                }
            }
        }

        if (!cooldown.contains(e.getPlayer().getUniqueId())) {
            final ArcticPlayer obj = Core.getCore().getAccountCache().getPlayer(e.getPlayer().getUniqueId());
            final String FORMAT = "%s%s%s &f%s";
            e.setFormat(StringUtil.color(String.format(FORMAT, StringUtil.level((int) obj.getNetLevel(), true), obj.getRank().getName(true), obj.getName(), e.getMessage())));
            if (!obj.isStaff())
                cooldown.add(e.getPlayer().getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getCore(), () -> {
                cooldown.remove(e.getPlayer().getUniqueId());
            }, 20L);
        } else {
            e.setCancelled(true);
            PlayerUtil.help(e.getPlayer(), PlayerUtil.Module.SERVER, "Please wait before speaking.");
        }
    }
}
