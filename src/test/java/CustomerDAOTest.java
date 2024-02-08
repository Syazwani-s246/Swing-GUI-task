import org.example.Customer;
import org.example.CustomerDAO;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {
    private static CustomerDAO customerDAO;

    @BeforeAll
    static void setUp() throws SQLException {
        customerDAO = new CustomerDAO();
    }

    @Test
    void testConnection() {
        try {
            // Fetch all customers from the database
            List<Customer> customers = customerDAO.getAllCustomers();

            // If the retrieval was successful, it means the connection is established
            assertNotNull(customers, "Connection should be established");
            System.out.println("Connection passed !!");
        } catch (SQLException e) {
            fail("Connection failed: " + e.getMessage());
        }
    }



    @Test
    void testAddCustomer() throws SQLException {
        // Create a new customer
        Customer newCustomer = new Customer(1, "John", "John Doe", "123 Main St", "Apt 1", "New York", "NY", "10001");

        // Add the customer to the database
        customerDAO.addCustomer(newCustomer);

        System.out.println("Yeay! Customer was added ! Test passed !");

        // Verify that the customer was added correctly by retrieving it from the database
        Customer retrievedCustomer = customerDAO.getCustomerById(newCustomer.getCustomerId());
        assertNotNull(retrievedCustomer, "Customer should exist in the database");
        assertEquals(newCustomer.getShortName(), retrievedCustomer.getShortName(), "Short name should match");
        assertEquals(newCustomer.getFullName(), retrievedCustomer.getFullName(), "Full name should match");
        assertEquals(newCustomer.getAddress1(), retrievedCustomer.getAddress1(), "Address 1 should match");
        assertEquals(newCustomer.getAddress2(), retrievedCustomer.getAddress2(), "Address 2 should match");
        assertEquals(newCustomer.getAddress3(), retrievedCustomer.getAddress3(), "Address 3 should match");
        assertEquals(newCustomer.getCity(), retrievedCustomer.getCity(), "City should match");
        assertEquals(newCustomer.getPostalCode(), retrievedCustomer.getPostalCode(), "Postal code should match");
    }
}
