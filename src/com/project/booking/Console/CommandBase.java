package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

public class CommandBase {
    protected final Logger log;
    protected final Storage storage;

    public CommandBase(Logger log, Storage storage) {
        this.log = log;
        this.storage = storage;
    }
}
