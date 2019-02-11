package com.project.booking;

import com.project.booking.Logger.AppLogger;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();
        try {
            AppLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        for (; ; ) {
            if (app.loginCustomer()) {
                break;
            }
        }
        app.startApp2();
        //app.startApp();
    }
}


