package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

public class CmdBook extends CommandBase implements Command {
    private Auth a;

    public CmdBook(Logger log, Storage storage, Auth a) {
        super(log, storage);
        this.a = a;
    }

    @Override
    public String text() {
        return "BOOK";
    }

    @Override
    public String description() {
        return "Flights search and booking";
    }


    //        System.out.println("1. Online table.");
//        System.out.println("2. Flight information.");
//        System.out.println("3. Flights search and booking.");
//        System.out.println("4. Booking cancelling.");
//        System.out.println("5. My flights.");
//        System.out.println("6. Close session.");
//        System.out.println("7. Resetting/Re-creating flights db from schedule file.");
//        System.out.println("8. Exit.");
//        System.out.println("12. test. Display all flights.");
//        System.out.println("13. test. Load flights from file.");
//        System.out.println("14. test. Save flights to file.");

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
