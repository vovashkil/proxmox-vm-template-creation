package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

import static com.project.booking.Constants.FileUtil.FLIGHTS_FILE_PATH;

public class CmdFlightsLoad extends CommandBase implements Command {
    public CmdFlightsLoad(Logger log, Storage storage) {
        super(log, storage);
    }

    @Override
    public String text() {
        return "LOADFLIGHTS";
    }

    @Override
    public String description() {
        return "test. Load flights from file";
    }

    @Override
    public void doCommand() {
        log.info(String.format("%s executing", this.text()));
        System.out.println("Loading a list of flights from file...");
        storage.getFlights().readData(FLIGHTS_FILE_PATH);
    }

    @Override
    public boolean isAllowToUnAuth() {
        return true;
    }
}
