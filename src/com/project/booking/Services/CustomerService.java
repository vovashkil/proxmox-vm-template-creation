package com.project.booking.Services;

import com.project.booking.Booking.Customer;
import com.project.booking.DAO.CollectionCustomerDAO;
import com.project.booking.DAO.PersonDAO;

import java.util.List;

public class CustomerService {

    private PersonDAO<Customer> customerDao = new CollectionCustomerDAO();

    public PersonDAO getCustomerDAO() {
        return customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAll();
    }

    public void displayAllCustomers() {
        customerDao.getAll()
                .forEach(System.out::println);
    }

    public void saveCustomer(Customer customer) {
        if (customer.getLoginName() != "" && customer.getPassword() != "") {
            if (customerDao.getAll()
                    .stream()
                    .filter(e -> (e.getLoginName() == customer.getLoginName()))
                    .count() == 0) {
                customerDao.save(customer);
            } else {
                new IllegalArgumentException();
            }
        }
    }

    public void saveData(String filePath) {
        customerDao.saveData(filePath);
    }

    public void readData(String filePath) {
        customerDao.readData(filePath);
    }

    public void deleteCusomerByIndex(int index) {
        customerDao.remove(index);
    }

    public int count() {
        return customerDao.getAll()
                .size();
    }

    public Customer getCustomerById(int index) {
        if (index >= 0 && index < customerDao.getAll().size()) {
            return customerDao.getAll().get(index);
        } else {
            return null;
        }
    }

    public Customer getCustomerByLogin(String loginName, String password) {
        return customerDao.getAll()
                .stream()
                .filter(customer -> (customer.getLoginName().equalsIgnoreCase(loginName) && customer.getPassword().equals(password)))
                .findAny().orElse(null);
    }
}
