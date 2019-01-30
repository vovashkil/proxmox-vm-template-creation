package com.project.booking.Persons;

import java.util.List;

public class CustomerController {

    private CustomerService customerService = new CustomerService();

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllCustomers() {
        customerService.displayAllCustomers();
    }

    public void saveCustomer(Customer customer) {
        customerService.saveCustomer(customer);
    }

    public void saveData(String filePath) {
        customerService.saveData(filePath);
    }

    public void readData(String filePath) {
        customerService.readData(filePath);
    }

    public void deleteCustomerByIndex(int index) {
        customerService.deleteCusomerByIndex(index);
    }

    public int count() {
        return customerService.count();
    }

    public Customer getCustomerById(int index) {
        return customerService.getCustomerById(index);
    }

    public Customer getCustomerByLogin(String loginName, String password) {
        return customerService.getCustomerByLogin(loginName, password);
    }

}
