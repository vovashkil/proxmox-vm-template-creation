package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

public class CmdFlightsAll extends CommandBase implements Command{
    public CmdFlightsAll(Logger log, Storage storage) {
        super(log, storage);
    }

    @Override
    public String text() {
        return "ALLFLIGHTS";
    }

    @Override
    public String description() {
        return "test. Display all flights";
    }

    @Override
    public void doCommand() {
        log.info(String.format("%s executing", this.text()));
        System.out.println("Displaying entire list of flights...");
        storage.getFlights().displayAllFlights();
    }

    @Override
    public boolean isAllowToUnAuth() {
        return true;
    }
}
