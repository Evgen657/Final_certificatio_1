package api.helpers.db;

import api.helpers.config.EnvHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseDemo {
    public static void main(String[] args) {
        EnvHelper env = EnvHelper.getInstance();
        String connectionString = env.getConnectionString();
        String username = env.getDbLogin();
        String password = env.getDbPassword();

        String query = "SELECT name, surname FROM employee LIMIT 10";

        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             var statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Surname: " + rs.getString("surname"));
                System.out.println("-----------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
