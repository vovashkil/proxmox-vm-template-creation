package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

public class CmdCloseSession extends CommandBase implements Command {
    private Auth a;

    public CmdCloseSession(Logger log, Storage storage, Auth a) {
        super(log, storage);
        this.a = a;
    }

    @Override
    public String text() {
        return "CLOSESESSION";
    }

    @Override
    public String description() {
        return "Close session";
    }

    @Override
    public void doCommand() {
        if (a.isAuth()) {
            System.out.println("Available commands are: EXIT, SHOW, BOOK, HELP");
        } else {
            System.out.println("Available commands are: EXIT, AUTH");
        }
    }

    @Override
    public boolean isAllowToUnAuth() {
        return false;
    }
}
