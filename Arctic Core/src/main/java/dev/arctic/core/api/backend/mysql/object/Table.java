package dev.arctic.core.api.backend.mysql.object;

import lombok.Getter;

/**
 * Created by Zvijer on 25.8.2017..
 */
public class Table {

    private final StringBuilder queryBuilder = new StringBuilder();
    private @Getter transient String name;

    private <T> String separate(boolean b, final T... parameters) {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        for (int i = 0; i < parameters.length; i++) {
            buffer.append(b ? "'" : "").append(parameters[i]).append(i < parameters.length - 1 ? (b ? "', " : ", ") : (b ? "');" : ");"));
        }

        return buffer.toString();
    }

    public Table(final String name) {
        this.name = name;
        queryBuilder.append("CREATE TABLE IF NOT EXISTS ").append(name);
    }

    public <T> Table withParams(final T... params) {
        queryBuilder.append(separate(false, params));
        return this;
    }

    public String getQuery() {
        return this.queryBuilder.toString();
    }
}
