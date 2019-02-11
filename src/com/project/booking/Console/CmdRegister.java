package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

public class CmdRegister extends CommandBase implements Command {
    public CmdRegister(Logger log, Storage storage) {
        super(log, storage);
    }

    @Override
    public String text() {
        return "REGISTER";
    }

    @Override
    public String description() {
        return "Registration";
    }

    @Override
    public void doCommand() {
        System.out.println("Register...");
    }

    @Override
    public boolean isAllowToUnAuth() {
        return true;
    }
}
