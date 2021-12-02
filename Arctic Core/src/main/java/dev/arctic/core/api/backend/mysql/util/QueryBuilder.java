package dev.arctic.core.api.backend.mysql.util;

import dev.arctic.core.api.backend.mysql.MySQL;
import dev.arctic.core.api.backend.mysql.object.Table;
import dev.arctic.core.api.util.ServerUtil;
import lombok.Getter;
import org.bukkit.Server;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class QueryBuilder {

    private @Getter final MySQL database;

    public QueryBuilder(MySQL database) {
        this.database = database;
    }

    private <T> String separate(boolean b, final T... parameters) {
        final StringBuilder response = new StringBuilder();
        response.append("(");
        for (int i = 0; i < parameters.length; i++) {
            response.append(b ? "'" : "").append(parameters[i]).append(i < parameters.length - 1 ? (b ? "', " : ", ") : (b ? "');" : ");"));
        }

        return response.toString();
    }

    public void createTable(final String name, String... parameters) {
        this.database.update(String.format("CREATE TABLE IF NOT EXISTS %s %s", name, separate(false, parameters)));
        System.out.println(String.format("CREATE TABLE IF NOT EXISTS %s %s", name, separate(false, parameters)));
    }

    public <T> void insert(Table table, T... data) {
        insert(table.getName(), data);
    }

    public <T> void insert(final String table, T... data) {
        this.database.update(String.format("INSERT INTO %s VALUES %s", table, separate(true, data)));
        System.out.println(String.format("INSERT INTO %s VALUES %s", table, separate(true, data)));
    }

    public void delete(final Table table, String[] condition) {
        delete(table.getName(), condition);
    }

    public void delete(final String table, String[] condition) {
        this.database.update(String.format("DELETE FROM %s WHERE %s='%s';", table, condition[0], condition[1]));
        System.out.println(String.format("DELETE FROM %s WHERE %s='%s';", table, condition[0], condition[1]));
    }

    public <T> void update(final Table table, String key, T value, String[] condition) {
        update(table.getName(), key, value, condition);
    }

    public <T> void update(final String table, String key, T value, String[] condition) {
        this.database.update(String.format("UPDATE %s SET %s='%s' WHERE %s='%s';", table, key, value, condition[0], condition[1]));
        System.out.println(String.format("UPDATE %s SET %s='%s' WHERE %s='%s';", table, key, value, condition[0], condition[1]));
    }

    public boolean exists(final Table table, String[] condition) {
        return exists(table.getName(), condition);
    }

    public boolean exists(final String table, String[] condition) {
        final ResultSet query = condition != null ? database.query(String.format("SELECT * FROM %s WHERE %s='%s';", table, condition[0], condition[1])) : database.query(String.format("SELECT * FROM %s;", table));
        try {
            return query.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T> T fetchAll(final String table, String[] condition) {
        return fetch("*", table, condition);
    }

    public <T> T fetchMulti(final String key, String table, String[] condition) {
        T result = null;
        ResultSet resultSet = this.database.query(
                condition != null
                        ? String.format("SELECT %s FROM %s WHERE %s='%s' AND %s='%s';", key, table, condition[0], condition[1], condition[2], condition[3])
                        : String.format("SELECT %s FROM %s", key, table));
        try {
            result = resultSet.next() ? (T) resultSet.getObject(key) : null;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> T fetch(final String key, String table, String[] condition) {
        T result = null;
        ResultSet resultSet = this.database.query(
                condition != null
                        ? String.format("SELECT %s FROM %s WHERE %s='%s';", key, table, condition[0], condition[1])
                        : String.format("SELECT %s FROM %s", key, table));
        try {
            result = resultSet.next() ? (T) resultSet.getObject(key) : null;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
        }
        return result;
    }
}
