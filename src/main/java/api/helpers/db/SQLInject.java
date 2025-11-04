package api.helpers.db;

import api.helpers.config.EnvHelper;

import java.sql.*;
import java.util.Scanner;

public class SQLInject {

    public static void main(String[] args) {
        EnvHelper env = EnvHelper.getInstance();
        try (Connection connection = DriverManager.getConnection(
                env.getConnectionString(),
                env.getDbLogin(),
                env.getDbPassword())) {

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter city: ");
                String city = scanner.nextLine();

                String query = "SELECT name, surname FROM employee WHERE city = ?";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, city);

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            System.out.println("Name: " + rs.getString("name"));
                            System.out.println("Surname: " + rs.getString("surname"));
                            System.out.println("-----------------------");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
