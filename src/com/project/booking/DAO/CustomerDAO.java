package com.project.booking.DAO;

import com.project.booking.Customer.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getAllCustomers();
    void saveCustomer(Customer customer);
    boolean deleteCustomer(int index);
    boolean deleteCustomer(Customer customer);
    void saveData(String filePath);
    void readData(String filePath);
    void loadData(List<Customer> customers);
}
