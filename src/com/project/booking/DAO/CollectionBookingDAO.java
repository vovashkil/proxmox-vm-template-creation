package com.project.booking.DAO;

import com.project.booking.Booking.Booking;
import com.project.booking.Logger.BookingLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionBookingDAO implements BookingDAO {
    private List<Booking> bookingsList;
    private BookingLogger logger;

    {

        logger = new BookingLogger();

    }

    public CollectionBookingDAO() {

        bookingsList = new ArrayList();

    }

    @Override
    public List<Booking> getAllBookings() {

        logger.info("Getting all bookings list...");

        return bookingsList;

    }

    @Override
    public Booking getBookingByIndex(int index) {

        logger.info("Getting booking by imdex = " + index + "...");

        Booking result = null;

        if (index >= 0 && index < bookingsList.size())
            result = bookingsList.get(index);

        return result;
    }

    @Override
    public void saveBooking(Booking booking) {

        logger.info("Saving information to db, booking = " + booking + "...");

        if (booking != null) {

            if (bookingsList.contains(booking)) {

                bookingsList.set(bookingsList.indexOf(booking), booking);

            } else {

                bookingsList.add(booking);

            }
        }

    }

    @Override
    public boolean deleteBooking(int index) {

        logger.info("Deleting booking by imdex = " + index + "...");

        boolean result = false;

        if (index >= 0 && index < bookingsList.size()) {
            bookingsList.remove(index);
            result = true;
        }

        return result;

    }

    @Override
    public boolean deleteBooking(Booking booking) {

        logger.info("Deleting booking by object = " + booking + "...");

        return bookingsList.remove(booking);

    }

    @Override
    public void saveData(String filePath) {

        logger.info("Saving bookings db to file...");

        try {

            if (new File(filePath).getParentFile().mkdirs())
                logger.info("Filepath created successfully.");
            FileOutputStream fileOutput = new FileOutputStream(filePath);
            ObjectOutputStream streamOutput = new ObjectOutputStream(fileOutput);
            streamOutput.writeObject(bookingsList);
            streamOutput.close();
            fileOutput.close();

        } catch (IOException e) {

            logger.error(e.getMessage());

        }

    }

    @Override
    public void readData(String filePath) {

        logger.info("Reading bookings db from file...");

        List<Booking> listLoaded = null;

        try {

            FileInputStream fileInput = new FileInputStream(filePath);
            ObjectInputStream inputStream = new ObjectInputStream(fileInput);
            listLoaded = (List<Booking>) inputStream.readObject();
            inputStream.close();
            fileInput.close();
            loadData(listLoaded);

        } catch (ClassNotFoundException | IOException e) {

            logger.error(e.getMessage());

        }

    }

    @Override
    public void loadData(List<Booking> bookingsList) {

        logger.info("Loading bookings info db...");

        if (bookingsList != null)

            bookingsList.forEach(this::saveBooking);

    }


}
