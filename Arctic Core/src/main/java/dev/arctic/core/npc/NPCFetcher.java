package dev.arctic.core.npc;

import com.google.common.collect.Lists;
import dev.arctic.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Zvijer on 28.8.2017..
 */
public class NPCFetcher implements Callable<List<NPC>> {

    @Override
    public List<NPC> call() {
        final List<NPC> list = Lists.newArrayList();

        final ResultSet resultSet = Core.getCore().getDatabaseManager().getQueryBuilder().getDatabase().query("SELECT * FROM npc");
        try {
            while (resultSet.next()) {
                list.add(new NPC(
                        resultSet.getString("name"),
                        EntityType.valueOf(resultSet.getString("type")),
                        new Location(
                                Bukkit.getWorld(resultSet.getString("world")),
                                resultSet.getDouble("x"),
                                resultSet.getDouble("y"),
                                resultSet.getDouble("z"),
                                resultSet.getFloat("yaw"),
                                resultSet.getFloat("pitch")
                        )
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
