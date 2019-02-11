package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

public class CmdLogin extends CommandBase implements Command {

    public CmdLogin(Logger log, Storage storage) {
        super(log, storage);
    }

    @Override
    public String text() {
        return "LOGIN";
    }

    @Override
    public String description() {
        return "Login";
    }

    @Override
    public void doCommand() {
        System.out.println("Login...");
    }

    @Override
    public boolean isAllowToUnAuth() {
        return true;
    }
}
