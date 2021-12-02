package dev.arctic.core.api.backend.mysql;

import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Zvijer on 27.7.2017..
 */
public abstract class Database {

    protected @Getter Connection connection;

    public Database() {
        this.connection = null;
    }

    public abstract Connection openConnection() throws SQLException, ClassNotFoundException;

    public boolean checkConnection() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean closeConnection() {
        try {
            if (this.connection == null)
                return false;

            this.connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet query(final String path) {
        ResultSet result = null;
        try {
            result = this.connection.createStatement().executeQuery(path);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int update(final String path) {
        int result = -1;
        try {
            result = this.connection.createStatement().executeUpdate(path);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
