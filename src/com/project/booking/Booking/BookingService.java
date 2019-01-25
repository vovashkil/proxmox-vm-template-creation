package com.project.booking.Booking;

import com.project.booking.DAO.CollectionBookingDAO;
import com.project.booking.DAO.BookingDAO;

import java.util.List;

public class BookingService {

    private BookingDAO bookingDao = new CollectionBookingDAO();

    public BookingDAO getBookingDao() {
        return bookingDao;
    }

    public List<Booking> getAllBookings() {
        return bookingDao.getAllBookings();
    }

    public void displayAllBookings() {

        bookingDao.getAllBookings().forEach(System.out::println);

    }

    public void saveBooking(Booking booking) {

        bookingDao.saveBooking(booking);

    }

    public void saveData(String filePath) {

        bookingDao.saveData(filePath);

    }

    public void readData(String filePath) {

        bookingDao.readData(filePath);

    }

    public void deleteBookingByIndex(int index) {

        bookingDao.deleteBooking(index);

    }

    public int count() {

        return bookingDao.getAllBookings().size();

    }

    public Booking getBookingById(int index) {

        if (index >= 0 && index < bookingDao.getAllBookings().size()) {

            return bookingDao.getAllBookings().get(index);

        } else {

            return null;

        }

    }



}
