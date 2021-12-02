package dev.arctic.core.object;

import dev.arctic.core.Core;
import dev.arctic.core.api.backend.mysql.util.QueryBuilder;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.ServerUtil;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class ArcticPlayer {

    private static final QueryBuilder queryBuilder = Core.getCore().getDatabaseManager().getQueryBuilder();
    private UUID uuid;
    private String name;
    private Rank rank;
    private int flakes;
    private double netLevel;

    public ArcticPlayer(final UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(uuid).getName();
        if (!queryBuilder.exists("accounts", new String[] {"uuid", uuid.toString()})) {
            //if user doesn't exist
            this.rank = Rank.MEMBER;
            this.flakes = 0;
            this.netLevel = 0.0;
            queryBuilder.insert("accounts (uuid, name, rank, flakes)", uuid.toString(), name, rank, flakes);
            queryBuilder.insert("network_level (uuid, level)", uuid.toString(), netLevel);
            System.out.println("[MySQL] Updating database center: PlayerData, Network Level");
        }

        load();
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public Rank getRank() {
        return Rank.valueOf(queryBuilder.fetch("rank", "accounts", new String[] {"uuid", uuid.toString()}));
    }

    public void updateRank(final Rank rank) {
        ServerUtil.callAsync(() -> {
            this.rank = rank;
            queryBuilder.update("accounts", "rank", rank, new String[] {"uuid", uuid.toString()});
        });

        System.out.println("[MySQL] Updating database center: RANKS");
    }

    public int getFlakes() {
        return queryBuilder.fetch("flakes", "accounts", new String[] {"uuid", uuid.toString()});
    }

    public void updateFlakes(int amount, boolean increment) {
        ServerUtil.callAsync(() -> {
            this.flakes = increment ? flakes += amount : amount;
            queryBuilder.update("accounts", "flakes", flakes, new String[] {"uuid", uuid.toString()});
        });

        System.out.println("[MySQL] Updating database center: FLAKES");
    }

    public double getNetLevel() {
        return queryBuilder.fetch("level", "network_level", new String[] {"uuid", uuid.toString()});
    }

    public void updateNetLevel(double amount, boolean increment) {
        ServerUtil.callAsync(() -> {
            this.netLevel = increment ? netLevel += amount : amount;
            queryBuilder.update("network_level", "level", netLevel, new String[] {"uuid", uuid.toString()});
        });

        System.out.println("[MySQL] Updating database center: NETWORK LEVEL");
    }

    public boolean isStaff() {
        return getRank().has(Rank.HELPER);
    }

    public boolean isSeniorStaff() {
        return isStaff() && getRank().has(Rank.SNR_MOD);
    }

    private void load() {
        ServerUtil.callAsync(() -> {
            this.rank = Rank.valueOf(queryBuilder.fetch("rank", "accounts", new String[] {"uuid", uuid.toString()}));
            this.flakes = queryBuilder.fetch("flakes", "accounts", new String[] {"uuid", uuid.toString()});
            this.netLevel = queryBuilder.fetch("level", "network_level", new String[] {"uuid", uuid.toString()});
        });

        System.out.println("[MySQL] Updating database center: PlayerData");
    }
}
