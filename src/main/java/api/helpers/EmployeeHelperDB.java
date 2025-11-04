package api.helpers;

import api.entities.EmployeeRequest;
import api.entities.EmployeeResponse;
import api.helpers.config.EnvHelper;

import java.io.IOException;
import java.sql.*;

public class EmployeeHelperDB {

    private final Connection connection;

    public EmployeeHelperDB() throws SQLException, IOException {
        EnvHelper envHelper = EnvHelper.getInstance();
        connection = DriverManager.getConnection(envHelper.getConnectionString(), envHelper.getDbLogin(), envHelper.getDbPassword());
    }

    public int createEmployee(EmployeeRequest employee) throws SQLException {
        String sql = "INSERT INTO employee(name, surname, city, position) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getSurname());
            ps.setString(3, employee.getCity());
            ps.setString(4, employee.getPosition());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
                return -1;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    return -1;
                }
            }
        }
    }

    public EmployeeResponse getEmployee(int id) throws SQLException {
        String sql = "SELECT id, city, name, position, surname FROM employee WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new EmployeeResponse(
                            rs.getString("city"),
                            rs.getString("name"),
                            rs.getString("position"),
                            rs.getString("surname"),
                            rs.getInt("id"));
                }
                return null;
            }
        }
    }

    public int countEmployeesByNameAndSurname(String name, String surname) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employee WHERE name = ? AND surname = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, surname);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public void deleteTestEmployees() throws SQLException {
        String sql = "DELETE FROM employee WHERE surname IN ('Igorin', 'Ivanova', 'Batuev', 'Sergeev', 'Dmitriev', 'Elena', 'Volkov', 'Dmitry', 'Sergey', 'Anna', 'Alexeev')";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employee WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
