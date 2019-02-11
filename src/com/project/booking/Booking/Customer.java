package com.project.booking.Booking;

import com.project.booking.Constants.DataUtil;
import com.project.booking.Constants.RoleType;
import com.project.booking.Constants.Sex;

import java.io.Serializable;

import static com.project.booking.Constants.ComUtil.dateLongToString;

public class Customer extends Person implements DataUtil, Serializable {
    private int cust_ID;
    private String loginName;
    private String password;
    private boolean isVIP = false;
    private RoleType role = RoleType.GUEST;

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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Customer(String name, String surname, long birthDate, Sex sex, String loginName, String password) {
        super(name, surname, birthDate, sex);
        this.loginName = loginName;
        this.password = password;
        this.role = RoleType.USER;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "role=" + getRole() + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + super.getName() + '\'' +
                ", surname='" + super.getSurname() + '\'' +
                ", birthDate=" + dateLongToString(super.getBirthDate(), DataUtil.DATE_FORMAT) +
                ", sex=" + super.getSex() +
                ", isVIP=" + isVIP +
                "}";
    }
}
