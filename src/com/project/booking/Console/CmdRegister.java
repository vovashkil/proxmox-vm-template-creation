package com.project.booking.Console;

import com.project.booking.Booking.Customer;
import com.project.booking.Constants.PersonType;
import com.project.booking.Controllers.Storage;

import java.util.logging.Logger;

public class CmdRegister extends CommandBase implements Command {
    private Auth a;

    public CmdRegister(Logger log, Storage storage, Auth a) {
        super(log, storage);
        this.a = a;
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
        log.info(String.format("%s executing", this.text()));
        Customer user = (Customer) storage.getCustomers().createPerson(PersonType.CUSTOMER);
        storage.getCustomers().saveCustomer(user);
        if (user != null) {
            storage.setUser(user);
            System.out.printf("%s %s, Welcome to booking!!!\n", user.getSurname(), user.getName());
            a.setAuth(true);
            log.info(String.format("Registering OK for %s %s...", user.getSurname(), user.getName()));
        } else {
            System.out.println("Customer is not registered!");
            a.setAuth(false);
            log.info(String.format("Customer is not registered %s %s...", user.getSurname(), user.getName()));
        }
    }

    @Override
    public boolean isAllowToUnAuth() {
        return (!a.isAuth());
    }
}
