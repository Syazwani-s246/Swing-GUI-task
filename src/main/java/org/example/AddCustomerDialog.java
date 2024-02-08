package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddCustomerDialog extends JDialog {
    private CustomerDAO customerDAO;
    private JTextField shortNameField;
    private JTextField fullNameField;
    private JTextField address1Field;
    private JTextField address2Field;
    private JTextField address3Field;
    private JTextField cityField;
    private JTextField postalCodeField;

    public AddCustomerDialog(Frame parent, CustomerDAO customerDAO) {
        super(parent, "Add Customer", true);
        this.customerDAO = customerDAO;

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Short Name:"));
        shortNameField = new JTextField();
        panel.add(shortNameField);
        panel.add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        panel.add(fullNameField);
        panel.add(new JLabel("Address 1:"));
        address1Field = new JTextField();
        panel.add(address1Field);
        panel.add(new JLabel("Address 2:"));
        address2Field = new JTextField();
        panel.add(address2Field);
        panel.add(new JLabel("Address 3:"));
        address3Field = new JTextField();
        panel.add(address3Field);
        panel.add(new JLabel("City:"));
        cityField = new JTextField();
        panel.add(cityField);
        panel.add(new JLabel("Postal Code:"));
        postalCodeField = new JTextField();
        panel.add(postalCodeField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values from fields
                String shortName = shortNameField.getText();
                String fullName = fullNameField.getText();
                String address1 = address1Field.getText();
                String address2 = address2Field.getText();
                String address3 = address3Field.getText();
                String city = cityField.getText();
                String postalCode = postalCodeField.getText();

                try {
                    // Create a new Customer object
                    Customer newCustomer = new Customer(0, shortName, fullName, address1, address2, address3, city, postalCode);

                    // Add the new customer to the database
                    customerDAO.addCustomer(newCustomer);

                    JOptionPane.showMessageDialog(AddCustomerDialog.this, "Customer added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the dialog

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddCustomerDialog.this, "Error adding customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog without adding the customer
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}