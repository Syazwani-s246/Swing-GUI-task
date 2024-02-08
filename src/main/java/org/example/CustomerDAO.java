package org.example;

import java.sql.Connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/swing-customers";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";

    private Connection connection;

    public CustomerDAO() throws SQLException {
        this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String shortName = resultSet.getString("short_name");
                String fullName = resultSet.getString("full_name");
                String address1 = resultSet.getString("address1");
                String address2 = resultSet.getString("address2");
                String address3 = resultSet.getString("address3");
                String city = resultSet.getString("city");
                String postalCode = resultSet.getString("postal_code");
                Customer customer = new Customer(customerId, shortName, fullName, address1, address2, address3, city, postalCode);
                customers.add(customer);
            }
        }
        return customers;
    }

    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (short_name, full_name, address1, address2, address3, city, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getShortName());
            statement.setString(2, customer.getFullName());
            statement.setString(3, customer.getAddress1());
            statement.setString(4, customer.getAddress2());
            statement.setString(5, customer.getAddress3());
            statement.setString(6, customer.getCity());
            statement.setString(7, customer.getPostalCode());
            statement.executeUpdate();
        }
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCustomer(resultSet);
                }
            }
        }
        return null;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET short_name = ?, full_name = ?, address1 = ?, address2 = ?, address3 = ?, city = ?, postal_code = ? WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getShortName());
            statement.setString(2, customer.getFullName());
            statement.setString(3, customer.getAddress1());
            statement.setString(4, customer.getAddress2());
            statement.setString(5, customer.getAddress3());
            statement.setString(6, customer.getCity());
            statement.setString(7, customer.getPostalCode());
            statement.setInt(8, customer.getCustomerId());
            statement.executeUpdate();
        }
    }

    public void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }

    private Customer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        int customerId = resultSet.getInt("customer_id");
        String shortName = resultSet.getString("short_name");
        String fullName = resultSet.getString("full_name");
        String address1 = resultSet.getString("address1");
        String address2 = resultSet.getString("address2");
        String address3 = resultSet.getString("address3");
        String city = resultSet.getString("city");
        String postalCode = resultSet.getString("postal_code");
        return new Customer(customerId, shortName, fullName, address1, address2, address3, city, postalCode);
    }
}
