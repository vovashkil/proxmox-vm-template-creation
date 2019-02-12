package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

import static com.project.booking.Constants.FileUtil.FLIGHTS_FILE_PATH;

public class CmdFlightsSave extends CommandBase implements Command {
    public CmdFlightsSave(Logger log, Storage storage) {
        super(log, storage);
    }

    @Override
    public String text() {
        return "SAVEFLIGHTS";
    }

    @Override
    public String description() {
        return "test. Save flights to file";
    }

    @Override
    public void doCommand() {
        log.info(String.format("%s executing", this.text()));
        System.out.println("Saving the list of flights to file...");
        storage.getFlights().saveData(FLIGHTS_FILE_PATH);
    }

    @Override
    public boolean isAllowToUnAuth() {
        return true;
    }
}
