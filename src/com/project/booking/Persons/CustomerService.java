package com.project.booking.Persons;

import com.project.booking.DAO.CollectionCustomerDAO;
import com.project.booking.DAO.CustomerDAO;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomerService {

    private CustomerDAO customerDao = new CollectionCustomerDAO();

    public CustomerDAO getCustomerDAO() {
        return customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public void displayAllCustomers() {
        customerDao.getAllCustomers().forEach(System.out::println);
    }

    public void saveCustomer(Customer customer) {
        if (customer.getLoginName() != "" && customer.getPassword() != "") {
            if (customerDao.getAllCustomers()
                    .stream()
                    .filter(e -> (e.getLoginName() == customer.getLoginName()))
                    .count() == 0) {
                customerDao.saveCustomer(customer);
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
        customerDao.deleteCustomer(index);
    }

    public int count() {
        return customerDao.getAllCustomers().size();
    }

    public Customer getCustomerById(int index) {
        if (index >= 0 && index < customerDao.getAllCustomers().size()) {
            return customerDao.getAllCustomers().get(index);
        } else {
            return null;
        }
    }

    public Customer getCustomerByLogin(String loginName, String password) {
        return customerDao.getAllCustomers()
                .stream()
                .filter(customer -> (customer.getLoginName().equalsIgnoreCase(loginName) && customer.getPassword().equals(password)))
                .findAny().orElse(null);
    }
}
