package dev.arctic.core.api.backend.mysql;

import dev.arctic.core.api.backend.mysql.util.QueryBuilder;
import lombok.Getter;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class DatabaseManager {

    private @Getter QueryBuilder queryBuilder;
    private static final String ACCOUNTS = "accounts";
    private static final String NEWS = "news";
    private static final String SPAWN = "spawn";
    private static final String NPC = "npc";
    private static final String NET_LEVEL = "network_level";
    private static final String PUNISHMENTS = "punishments";
    private static final String PREV_PUNISHMENTS = "previous_punishments";

    public DatabaseManager(final MySQL database) {
        this.queryBuilder = new QueryBuilder(database);
        this.queryBuilder.createTable(ACCOUNTS, "id INT NOT NULL AUTO_INCREMENT", "uuid MEDIUMTEXT", "name TINYTEXT", "rank TINYTEXT", "flakes INT", "PRIMARY KEY (id)");
        this.queryBuilder.createTable(NEWS, "id INT NOT NULL AUTO_INCREMENT", "name MEDIUMTEXT", "PRIMARY KEY (id)");
        this.queryBuilder.createTable(SPAWN, "x DOUBLE", "y DOUBLE", "z DOUBLE", "yaw FLOAT", "pitch FLOAT", "world TINYTEXT");
        this.queryBuilder.createTable(NPC, "id INT NOT NULL AUTO_INCREMENT", "name TINYTEXT", "type TINYTEXT", "x DOUBLE", "y DOUBLE", "z DOUBLE", "yaw FLOAT", "pitch FLOAT", "world TINYTEXT", "PRIMARY KEY (id)");
        this.queryBuilder.createTable(NET_LEVEL, "id INT NOT NULL AUTO_INCREMENT", "uuid MEDIUMTEXT", "level DOUBLE", "PRIMARY KEY (id)");
        this.queryBuilder.createTable(PUNISHMENTS, "id INT NOT NULL AUTO_INCREMENT", "uuid VARCHAR(40)", "ip VARCHAR(40)", "name TINYTEXT", "punisher TINYTEXT", "type TINYTEXT", "date TINYTEXT", "duration LONG", "reason MEDIUMTEXT", "PRIMARY KEY (id)");
        this.queryBuilder.createTable(PREV_PUNISHMENTS, "id INT NOT NULL AUTO_INCREMENT", "uuid VARCHAR(40)", "punisher TINYTEXT", "date TINYTEXT", "type TINYTEXT", "duration LONG", "reason MEDIUMTEXT", "PRIMARY KEY (id)");
    }
}
