package com.project.booking.Controllers;

public class Storage {
    private final CustomerController customers = new CustomerController();
    private final FlightController flights = new FlightController();
    private final BookingController bookings = new BookingController();

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
