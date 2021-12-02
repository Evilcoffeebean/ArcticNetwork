package dev.arctic.core.api.util;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Map;

/**
 * Created by Zvijer on 16.8.2017..
 */
public class ScoreboardUtil {

    private static Map<Integer, String> scores = Maps.newConcurrentMap();
    private Scoreboard scoreboard;
    private String name;
    private Objective objective;
    private int score = 15;

    public ScoreboardUtil(final String name) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.name = name;
    }

    public void registerObjective() {
        if (objective == null) {
            objective = scoreboard.registerNewObjective((name.length() > 16 ? name.substring(0, 15) : name), "dummy");
            objective.setDisplayName(StringUtil.color(name));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        for (int i : scores.keySet()) {
            objective.getScore(scores.get(i)).setScore(i);
        }
    }

    public void setTitle(String title) {
        if (objective != null) {
            objective.setDisplayName(StringUtil.color(title));
        }
    }

    public void add(String text) {
        scores.put(score--, StringUtil.color(text));
    }

    public void clear() {
        scores.values().forEach(s -> scoreboard.resetScores(s));
        scores.clear();
        score = 15;
    }

    public void build(Player player) {
        player.setScoreboard(scoreboard);
    }
}
