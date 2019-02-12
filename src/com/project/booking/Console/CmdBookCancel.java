package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class CmdBookCancel extends CommandBase implements Command {
    private Auth a;

    public CmdBookCancel(Logger log, Storage storage, Auth a) {
        super(log, storage);
        this.a = a;
    }

    @Override
    public String text() {
        return "CANCEL";
    }

    @Override
    public String description() {
        return "Booking cancelling";
    }

    @Override
    public void doCommand() {
        log.info(String.format("%s executing", this.text()));
        System.out.println("Booking cancelling...");

        Scanner input = new Scanner(System.in);

        boolean controlBookingCancel = true;

        if (storage.getBookings().isEmptyBookings()) {
            System.out.println("There is no booking made in Data.");
            controlBookingCancel = false;
        }

        while (controlBookingCancel) {
            storage.getBookings().printCancelBookingMenu(storage.getBookings().getAllBookings());

            System.out.print("Please enter booking ID to cancel or 0 to return: ");

            long choiceCancelBooking;

            try {
                choiceCancelBooking = input.nextLong();
            } catch (InputMismatchException e) {
                choiceCancelBooking = -1;
            }

            if (choiceCancelBooking == 0) {
                controlBookingCancel = false;
            } else if (choiceCancelBooking == -1) {
                System.out.println(
                        "Your choice is wrong. Please enter booking ID to cancel or 0 to return: ");
            } else if (storage.getBookings().bookingNumberIsPresent(choiceCancelBooking)) {
                System.out.println("Deleteing booking...");

                storage.getBookings().cancelBooking(choiceCancelBooking);
                controlBookingCancel = false;

            } else {
                System.out.println(
                        "There is no booking with ID=\'" + choiceCancelBooking + "\' in db. Please enter booking ID to cancel or 0 to return: ");
            }
        }
    }

    @Override
    public boolean isAllowToUnAuth() {
        return (a.isAuth());
    }
}
