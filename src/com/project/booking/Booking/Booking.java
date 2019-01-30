package com.project.booking.Booking;

import com.project.booking.Constants.DataUtil;
import com.project.booking.Persons.Customer;
import com.project.booking.Flight.Flight;
import com.project.booking.Persons.Passenger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Booking implements DataUtil {

    private long number;
    private final LocalDateTime dateTime = LocalDateTime.now();
    private Customer customer;
    private List<Passenger> passengers = new ArrayList();
    private Flight flight;

    public Booking() {
        this.number = setBookingNumber();
    }

    public Booking(final Customer customer, List<Passenger> passengers) {
        this.number = setBookingNumber();
        this.setCustomer(customer);
        this.setPassengers(passengers);
    }

    public long getBookingNumber() {
        return this.number;
    }

    private long setBookingNumber() {

        return dateTime.getYear() * 10000000000L +
                dateTime.getMonth().getValue() * 100000000 +
                dateTime.getDayOfMonth() * 1000000 +
                dateTime.getHour() * 10000 +
                dateTime.getMinute() * 100 +
                dateTime.getSecond();

    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "number=" + number +
                ", dateTime='" + dateTime.format(DateTimeFormatter
                                .ofPattern(DATE_TIME_FORMAT)) + '\'' +
                ", customer=" + customer +
                ", flight=" + flight +
                ", passengers=" + passengers +
                '}';
    }
}

