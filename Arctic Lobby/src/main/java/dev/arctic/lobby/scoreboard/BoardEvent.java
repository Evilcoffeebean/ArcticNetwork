package dev.arctic.lobby.scoreboard;

import com.google.common.collect.Maps;
import dev.arctic.core.Core;
import dev.arctic.core.api.util.ScoreboardUtil;
import dev.arctic.core.object.ArcticPlayer;
import dev.arctic.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Zvijer on 16.8.2017..
 */
public class BoardEvent implements Listener {

    private HashMap<UUID, ScoreboardUtil> boards = Maps.newHashMap();

    public BoardEvent() {
        //update();
    }

    private void update() {
        Bukkit.getScheduler().runTaskTimer(Lobby.getLobby(), () -> {
            for (UUID uuid : boards.keySet()) {
                ScoreboardUtil board = boards.get(uuid);
                board.clear();

                ArcticPlayer arcticPlayer = Core.getCore().getAccountCache().getPlayer(uuid);

                board.add("&9&lServer");
                board.add("&fLobby");
                board.add(" ");
                board.add("&9&lPlayer");
                board.add("&f" + Bukkit.getPlayer(uuid).getName());
                board.add("  ");
                board.add("&9&lRank");
                board.add("&f" + arcticPlayer.getRank().getName());
                board.add("   ");
                board.add("&9&lFlakes");
                board.add("&f" + arcticPlayer.getFlakes());
                board.add("    ");
                board.add("&9&lWebsite");
                board.add("&bwww.arcticnetwork.com");

                board.registerObjective();
                board.build(Bukkit.getPlayer(uuid));
            }
        }, 10L, 40L);
    }

    private void enableScoreboard(Player player) {
        ScoreboardUtil board = new ScoreboardUtil("&b&lArctic &f&lNetwork");
        boards.put(player.getUniqueId(), board);

        ArcticPlayer arcticPlayer = Core.getCore().getAccountCache().getPlayer(player.getUniqueId());

        board.add("&9&lServer");
        board.add("&fLobby");
        board.add(" ");
        board.add("&9&lPlayer");
        board.add("&f" + player.getName());
        board.add("  ");
        board.add("&9&lRank");
        board.add("&f" + arcticPlayer.getRank().getName());
        board.add("   ");
        board.add("&9&lFlakes");
        board.add("&f" + arcticPlayer.getFlakes());
        board.add("    ");
        board.add("&9&lWebsite");
        board.add("&bwww.arcticnetwork.com");

        board.registerObjective();
        board.build(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        enableScoreboard(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        boards.remove(e.getPlayer().getUniqueId());
    }
}
