package com.project.booking.Console;

import com.project.booking.Booking.Customer;
import com.project.booking.Controllers.Storage;

import java.util.Scanner;
import java.util.logging.Logger;

public class CmdLogin extends CommandBase implements Command {
    private Auth a;

    public CmdLogin(Logger log, Storage storage, Auth a) {
        super(log, storage);
        this.a = a;
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
        log.info(String.format("%s executing", this.text()));

        Scanner scanner = new Scanner(System.in);

        System.out.print("LoginName: ");
        String loginName = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Customer customerApp = storage.getCustomers().getCustomerByLogin(loginName, password);

        if (customerApp != null) {
            storage.setUser(customerApp);
            System.out.printf("%s %s, Welcome to booking!!!\n", customerApp.getSurname(), customerApp.getName());
            a.setAuth(true);
            log.info(String.format("Login OK for %s...", loginName));
        } else {
            storage.setUser(storage.getCustomers().getCustomerGuest());
            System.out.println("Invalid Username & Password!");
            a.setAuth(false);
            log.warning(String.format("Invalid Username & Password for %s...", loginName));
        }
    }

    @Override
    public boolean isAllowToUnAuth() {
        return (!a.isAuth());
    }
}
