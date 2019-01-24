package com.project.booking.Persons;

import com.project.booking.Customer.Customer;

import java.util.Objects;

public class User {
    private int user_ID;
    private String loginName;
    private String password;
    private Customer customer;

    private static int count = 0;

    {
        this.user_ID = ++count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return loginName.equals(user.loginName) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginName, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_ID=" + user_ID +
                ", loginName='" + loginName + '\'' +
                ", customer=" + customer +
                '}';
    }
}
