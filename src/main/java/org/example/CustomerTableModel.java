package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CustomerTableModel extends AbstractTableModel {
    private List<Customer> customers;
    private String[] columnNames = {"ID", "Short Name", "Full Name", "Address 1", "Address 2", "Address 3", "City", "Postal Code"};

    public CustomerTableModel(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public int getRowCount() {
        return customers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = customers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return customer.getCustomerId();
            case 1:
                return customer.getShortName();
            case 2:
                return customer.getFullName();
            case 3:
                return customer.getAddress1();
            case 4:
                return customer.getAddress2();
            case 5:
                return customer.getAddress3();
            case 6:
                return customer.getCity();
            case 7:
                return customer.getPostalCode();
            default:
                return null;
        }
    }
}
