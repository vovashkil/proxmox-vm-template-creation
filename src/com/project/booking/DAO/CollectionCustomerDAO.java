package com.project.booking.DAO;

import com.project.booking.Customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class CollectionCustomerDAO implements CustomerDAO {
    private List<Customer> customers;

    public CollectionCustomerDAO() {
        this.customers = new ArrayList();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        if (customer == null) throw new NullPointerException("Customer is Null!");

        if (this.customers.contains(customer)) {
            this.customers.set(customers.indexOf(customer), customer);
        } else {
            this.customers.add(customer);
        }
    }

    @Override
    public boolean deleteCustomer(int index) {
        if (this.customers == null || index < 0 || index >= this.customers.size()) {
            return false;
        } else
            return this.customers.remove(this.customers.get(index));
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        if (this.customers == null || customer == null || !this.customers.contains(customer)) {
            return false;
        } else {
            return this.customers.remove(customer);
        }
    }

    @Override
    public void saveData(String filePath) {

    }

    @Override
    public void readData(String filePath) {

    }

    @Override
    public void loadData(String filePath) {

    }
}
