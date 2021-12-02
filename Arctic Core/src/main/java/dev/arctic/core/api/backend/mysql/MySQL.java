package dev.arctic.core.api.backend.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class MySQL extends Database {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

    public MySQL(String host, int port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;

        openConnection();
    }

    @Override
    public Connection openConnection() {
        try {
            if (checkConnection())
                return this.connection;

            final String URL = String.format("jdbc:mysql://%s:%d/%s", host, port, database);
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, username, password);

            return this.connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
