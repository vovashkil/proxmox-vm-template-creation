package com.project.booking.DAO;

import com.project.booking.Booking.Booking;

import java.util.List;

public interface BookingDAO {

    List<Booking> getAllBookings();
    Booking getBookingByIndex(int index);
    void saveBooking(Booking booking);
    boolean deleteBooking(int index);
    boolean deleteBooking(Booking booking);
    void saveData(String filePath);
    void readData(String filePath);
    void loadData(List<Booking> bookingList);

}
