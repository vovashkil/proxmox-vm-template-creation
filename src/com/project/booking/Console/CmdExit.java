package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

import static com.project.booking.Constants.FileUtil.*;

public class CmdExit extends CommandBase implements Command{
    public CmdExit(Logger log, Storage storage) {
        super(log, storage);
    }

    @Override
    public String text() {
        return "EXIT";
    }

    @Override
    public String description() {
        return "Exit";
    }

    @Override
    public void doCommand() {
        log.info(String.format("%s executing", this.text()));
        storage.getCustomers().saveData(CUSTOMERS_FILE_PATH);
        storage.getFlights().saveData(FLIGHTS_FILE_PATH);
        storage.getBookings().saveData(BOOKINGS_FILE_PATH);
        log.info(String.format("Save customers data to file: %s...", CUSTOMERS_FILE_PATH));
        log.info(String.format("Save flights data to file: %s...", FLIGHTS_FILE_PATH));
        log.info(String.format("Save bookings data to file: %s...", BOOKINGS_FILE_PATH));
    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public boolean isAllowToUnAuth() {
        return true;
    }
}
