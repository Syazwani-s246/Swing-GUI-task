# Customer Management System

This is a Java Swing application for managing customer details. It provides functionalities to add, modify, and delete customer records stored in a MySQL database.

## Customer

The `Customer` class represents a customer entity with attributes such as customer ID, short name, full name, address details, city, and postal code. It includes constructors, getters, setters, and a `toString` method for debugging.

## CustomerDAO

The `CustomerDAO` class is responsible for handling database operations related to customers, including CRUD (Create, Read, Update, Delete) operations. It establishes a connection to the MySQL database and provides methods to interact with the `Customer` table.

## CustomerForm

The `CustomerForm` class implements the graphical user interface (GUI) for the customer management system using Swing. It allows users to add, view, modify, and delete customer records. The form displays a table of customers and provides buttons for performing CRUD operations.

## AddCustomerDialog

The `AddCustomerDialog` class is a dialog box for adding a new customer record. It contains input fields for entering customer details such as short name, full name, address, city, and postal code.

## CustomerTableModel

The `CustomerTableModel` class extends `AbstractTableModel` and serves as a custom model for the JTable in the `CustomerForm`. It maps customer data to the table columns and rows.

## CustomerDAOTest

The `CustomerDAOTest` class is a JUnit test suite for testing the functionality of the `CustomerDAO` class. It includes test cases for database connection, adding a customer, and other CRUD operations.

## Dependencies

- Java Swing for the GUI
- MySQL database for data storage
- JUnit for unit testing

## Running the Application

To run the application, execute the `main` method in the `CustomerForm` class. Ensure that the MySQL database is running and accessible with the specified credentials.

## Testing

Execute the JUnit test cases in the `CustomerDAOTest` class to verify the correctness of database operations.

