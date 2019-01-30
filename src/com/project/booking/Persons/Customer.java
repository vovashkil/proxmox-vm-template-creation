package com.project.booking.Persons;

import com.project.booking.Constants.DataUtil;
import com.project.booking.Constants.Sex;

import java.io.Serializable;

public class Customer extends Person implements DataUtil, Serializable {
    private int cust_ID;
    private String loginName;
    private String password;
    private boolean isVIP = false;

    private static int count = 0;

    {
        this.cust_ID = ++count;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public Customer(String name, String surname, long birthDate, Sex sex, String loginName, String password) {
        super(name, surname, birthDate, sex);
        this.loginName = loginName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", isVIP=" + isVIP +
                "} " + super.toString();
    }
}
