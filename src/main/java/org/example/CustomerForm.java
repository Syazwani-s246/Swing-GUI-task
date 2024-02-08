package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CustomerForm extends JFrame {
    private CustomerDAO customerDAO;
    private JTable customerTable;
    private JButton addButton;
//    private JButton modifyButton;
    private JButton deleteButton;

    public CustomerForm(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Customer Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Create buttons
        addButton = new JButton("Add");
//        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new dialog or form for adding a customer
                AddCustomerDialog addDialog = new AddCustomerDialog(CustomerForm.this, customerDAO);
                addDialog.setVisible(true);
            }
        });

//        modifyButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Get the selected customer from the table
//                int selectedRow = customerTable.getSelectedRow();
//                if (selectedRow != -1) {
//                    int customerId = (int) customerTable.getValueAt(selectedRow, 0);
//                    try {
//                        Customer selectedCustomer = customerDAO.getCustomerById(customerId);
//                        if (selectedCustomer != null) {
//                            // Display the address details in the table for modification
//                            customerTable.setValueAt(selectedCustomer.getAddress1(), selectedRow, 3);
//                            customerTable.setValueAt(selectedCustomer.getAddress2(), selectedRow, 4);
//                            customerTable.setValueAt(selectedCustomer.getAddress3(), selectedRow, 5);
//                            customerTable.setValueAt(selectedCustomer.getPostalCode(), selectedRow, 6);
//
//                            JOptionPane.showMessageDialog(CustomerForm.this, "You can now modify the address directly in the table", "Modify Address", JOptionPane.INFORMATION_MESSAGE);
//                        }
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(CustomerForm.this, "Error retrieving customer details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(CustomerForm.this, "Please select a customer to modify", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });



        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle delete button click event
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    int customerId = (int) customerTable.getValueAt(selectedRow, 0);
                    try {
                        customerDAO.deleteCustomer(customerId);
                        refreshCustomerTable();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CustomerForm.this, "Error deleting customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(CustomerForm.this, "Please select a customer to delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create table to display customers
        customerTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(customerTable);

        // Add selection listener to the table
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = customerTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Retrieve the selected customer's address details
                        int customerId = (int) customerTable.getValueAt(selectedRow, 0);
                        try {
                            Customer selectedCustomer = customerDAO.getCustomerById(customerId);
                            if (selectedCustomer != null) {
                                // Display the address details in a separate window
                                displayAddressWindow(selectedCustomer);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(CustomerForm.this, "Error retrieving customer details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Add buttons and table to the frame
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
//        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Initialize table with data
        refreshCustomerTable();
    }

    private void refreshCustomerTable() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            CustomerTableModel model = new CustomerTableModel(customers);
            customerTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAddressWindow(Customer customer) {
        JFrame addressWindow = new JFrame("Customer Address");
        addressWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addressWindow.setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField address1Field = new JTextField(customer.getAddress1());
        JTextField address2Field = new JTextField(customer.getAddress2());
        JTextField address3Field = new JTextField(customer.getAddress3());
        JTextField postalCodeField = new JTextField(customer.getPostalCode());

        panel.add(new JLabel("Address 1:"));
        panel.add(address1Field);
        panel.add(new JLabel("Address 2:"));
        panel.add(address2Field);
        panel.add(new JLabel("Address 3:"));
        panel.add(address3Field);
        panel.add(new JLabel("Postal Code:"));
        panel.add(postalCodeField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(addressWindow, "Are you sure you want to save changes?", "Confirm Save", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {

                    // Validate postal code (allow only numbers)
                    String postalCode = postalCodeField.getText();
                    if (!postalCode.matches("\\d+")) {
                        JOptionPane.showMessageDialog(addressWindow, "Postal code should contain only numbers", "Invalid Postal Code", JOptionPane.ERROR_MESSAGE);
                        return; // Exit without saving if postal code is invalid
                    }

                    try {
                        // Update the customer object with the modified address details
                        customer.setAddress1(address1Field.getText());
                        customer.setAddress2(address2Field.getText());
                        customer.setAddress3(address3Field.getText());
                        customer.setPostalCode(postalCodeField.getText());

                        // Update the customer in the database
                        customerDAO.updateCustomer(customer);
                        JOptionPane.showMessageDialog(addressWindow, "Changes saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        addressWindow.dispose(); // Close the address window

                        // Refresh the table to reflect the changes
                        refreshCustomerTable();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(addressWindow, "Error saving changes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(saveButton);

        addressWindow.add(panel);
        addressWindow.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                CustomerDAO customerDAO = new CustomerDAO();

                // Create and display the customer form
                CustomerForm customerForm = new CustomerForm(customerDAO);
                customerForm.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error connecting to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
