package com.project.booking.Customer;

import com.project.booking.Constants.Sex;
import com.project.booking.Persons.Person;

import java.util.Objects;

public class Customer extends Person {
    private int cust_ID;
    private boolean isVIP = false;

    private static int count = 0;

    {
        this.cust_ID = ++count;
    }

    public Customer(String name, String surname, long birthDate, Sex sex) {
        super(name, surname, birthDate, sex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return cust_ID == customer.cust_ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cust_ID);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cust_ID=" + cust_ID +
                ", isVIP=" + isVIP +
                "} " + super.toString();
    }
}
