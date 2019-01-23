package com.project.booking;

import java.util.InputMismatchException;
import java.util.Scanner;

class ConsoleApp {

    void startApp() {

        boolean control = true;

        printMenuMain();

        while (control) {

            Scanner input = new Scanner(System.in);
            System.out.print("Please enter your choice [1-6]: ");
            int choice;

            try {

                choice = input.nextInt();

            } catch (InputMismatchException e) {

                choice = -1;

            }

            switch (choice) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:
                    break;

                case 6:
                    control = false;
                    break;

                default:
                    System.out.println("Your choice is wrong. Please repeat your choice.");


            }

        }

    }

    private static void printMenuMain() {

        System.out.println("1. Departures.");
        System.out.println("2. Flight information.");
        System.out.println("3. Flights search and booking.");
        System.out.println("4. Booking cancelling.");
        System.out.println("5. My flights.");
        System.out.println("6. Exit.");

    }

    public void loginUser() {
        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            System.out.println("Enter \"login\", \"register\", or \"exit\"");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "LOGIN":
                    // get login details
                    return;
                case "REGISTER":
                    // get register details
                    return;
                case "EXIT":
                    // exit the loop
                    System.exit(0);
                    break;
                default:
                    // invalid input, tell them to try again
                    System.out.printf("Invalid option (%s), choose login or register!%n", input);
            }
        }
    }
}
