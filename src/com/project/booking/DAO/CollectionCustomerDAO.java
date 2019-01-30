package com.project.booking.DAO;

import com.project.booking.Persons.Customer;

import java.io.*;
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
        //logger.info("Saving customers db to file...");
        try {
            //if (new File(filePath).getParentFile().mkdirs())
            //logger.info("Filepath created successfully.");
            FileOutputStream fileOutput = new FileOutputStream(filePath);
            ObjectOutputStream streamOutput = new ObjectOutputStream(fileOutput);

            streamOutput.writeObject(customers);
            streamOutput.close();
            fileOutput.close();
        } catch (IOException e) {
            //          logger.error(e.getMessage());
        }
    }

    @Override
    public void readData(String filePath) {
        //logger.info("Reading customers db from file...");
        List<Customer> listLoaded = null;
        try {

            FileInputStream fileInput = new FileInputStream(filePath);
            ObjectInputStream inputStream = new ObjectInputStream(fileInput);

            listLoaded = (List<Customer>) inputStream.readObject();
            inputStream.close();
            fileInput.close();
            loadData(listLoaded);
        } catch (ClassNotFoundException | IOException e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(List<Customer> customers) {
        //logger.info("Loading flights info db...");
        if (customers != null)
            customers.forEach(this::saveCustomer);
    }
}
