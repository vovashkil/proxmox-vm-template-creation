package com.project.booking.DAO;

import com.project.booking.Booking.Customer;
import com.project.booking.Logger.AppLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CollectionCustomerDAO implements PersonDAO<Customer> {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private List<Customer> customers;

    public CollectionCustomerDAO() {
        this.customers = new ArrayList();
        try {
            AppLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAll() {
        LOGGER.info("Get All Customers");
        return this.customers;
    }

    @Override
    public void save(Customer customer) {
        if (customer == null) {
            LOGGER.severe("Error Save Null Customer!");
            throw new NullPointerException("Customer is Null!");
        }

        if (this.customers.contains(customer)) {
            LOGGER.info("Update Data Customer: " + customer);
            this.customers.set(customers.indexOf(customer), customer);
        } else {
            LOGGER.info("Add New Customer: " + customer);
            this.customers.add(customer);
        }
    }

    @Override
    public boolean remove(int index) {
        if (this.customers == null || index < 0 || index >= this.customers.size()) {
            LOGGER.warning("Delete Customer with incorrect index: " + index);
            return false;
        } else {
            LOGGER.info("Delete Customer with index: " + index);
            return !(this.customers.remove(index) == null);
        }
    }

    @Override
    public boolean remove(Customer customer) {
        if (this.customers == null || customer == null || !this.customers.contains(customer)) {
            LOGGER.warning("Delete Customer which not exists in list: " + customer);
            return false;
        } else {
            LOGGER.info("Delete Customer: " + customer);
            return this.customers.remove(customer);
        }
    }

    @Override
    public void saveData(String filePath) {
        LOGGER.info("Saving customers data to file...");
        try
                (
                        FileOutputStream fileOutput = new FileOutputStream(filePath);
                        ObjectOutputStream streamOutput = new ObjectOutputStream(fileOutput)
                ) {
            if (new File(filePath).getParentFile().mkdirs()) {
                LOGGER.info("Filepath: " + filePath + " created successfully.");
            }
            streamOutput.writeObject(customers);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void readData(String filePath) {
        LOGGER.info("Reading customers data from file: " + filePath + "...");
        List<Customer> listLoaded = null;
        try
                (
                        FileInputStream fileInput = new FileInputStream(filePath);
                        ObjectInputStream inputStream = new ObjectInputStream(fileInput)
                ) {
            listLoaded = (List<Customer>) inputStream.readObject();
            loadData(listLoaded);
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(List<Customer> customers) {
        LOGGER.info("Loading customers info data...");
        if (customers != null)
            customers.forEach(this::save);
    }

    @Override
    public Customer getByIndex(int index) {
        LOGGER.info("Get Customer by Index: " + index);
        if (this.customers == null || index < 0 || index >= this.customers.size()) {
            LOGGER.info("Invalidate Customer index: " + index);
            return null;
        } else {
            return this.customers.get(index);
        }
    }
}
