package com.project.booking.Controllers;

import com.project.booking.Booking.Customer;

public class Storage {
    private Customer user = null;

    private final CustomerController customers = new CustomerController();
    private final FlightController flights = new FlightController();
    private final BookingController bookings = new BookingController();

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public CustomerController getCustomers() {
        return this.customers;
    }

    public FlightController getFlights() {
        return this.flights;
    }

    public BookingController getBookings() {
        return this.bookings;
    }
}
