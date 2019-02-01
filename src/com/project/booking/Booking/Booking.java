package com.project.booking.Booking;

import com.project.booking.Constants.DataUtil;
import com.project.booking.Persons.Customer;
import com.project.booking.Flight.Flight;
import com.project.booking.Persons.Passenger;
import com.project.booking.Persons.Person;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Booking implements DataUtil, Serializable {

    private long number;
    private final LocalDateTime dateTime = LocalDateTime.now();
    private Customer customer;
    private List<Person> passengers = new ArrayList();
    private Flight flight;

    public Booking(Flight flight) {

        this.number = setBookingNumber();
        this.flight = flight;
    }

    public Booking(Flight flight, final Customer customer, List<Person> passengers) {
        this.number = setBookingNumber();
        this.flight = flight;
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

    public List<Person> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Person> passengers) {
        this.passengers = passengers;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public boolean addPassenger(Person passenger) {

        if (!passengers.contains(passenger) &&
                passenger != null) {

            passengers.add(passenger);
            return true;

        } else

            return false;

    }

    public boolean deletePassenger(Person passenger) {

        if (!passengers.contains(passenger)) return false;

        passengers.remove(passenger);
        return true;

    }

    public boolean deletePassenger(int index) {

        if (index >= 0 && index < passengers.size()) {

            if (!passengers.contains(passengers.get(index))) return false;

            passengers.remove(passengers.get(index));
            return true;

        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return number == booking.number;
    }

    @Override
    public int hashCode() {

        int result = 11;
        int coef = 31;

        result = coef * result + (int) number;

        return (int) number;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "number=" + number +
                ", dateTime='" + dateTime.format(DateTimeFormatter
                                .ofPattern(DATE_TIME_FORMAT)) + '\'' +
                ", customer=" + customer +
                ",\n\t flight=" + flight +
                ",\n\t\t passengers=" + passengers +
                '}';
    }
}

