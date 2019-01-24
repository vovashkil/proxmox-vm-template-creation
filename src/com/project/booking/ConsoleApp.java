package com.project.booking;

import com.opencsv.CSVReader;
import com.project.booking.Constants.DataUtil;
import com.project.booking.Constants.FileUtil;
import com.project.booking.Flight.Flight;
import com.project.booking.Flight.FlightController;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;

class ConsoleApp implements FileUtil, DataUtil {

    void startApp() {

        FlightController flightsDB = new FlightController();

        boolean control = true;

        while (control) {

            printMenuMain();

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

                    System.out.println("Displaying online table...");

                    flightsDB.getAllFlights()
                            .stream()
                            .sorted((a, b) -> (int) (a.getDepartureDateTime() - b.getDepartureDateTime()))
                            .forEach(System.out::println);

                    break;

                case 2:

                    System.out.println("Displaying flight information...");

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

                case 11:

                    fillUpFlightDatabaseAutomatically(flightsDB);

                    break;

                case 12:

                    System.out.println("Displaying entire list of flights...");

                    flightsDB.displayAllFlights();

                    break;

                case 13:

                    System.out.println("Loading a list of flights from file...");

                    flightsDB.readData(FLIGHTS_FILE_PATH);

                    break;

                case 14:

                    System.out.println("Saving the list of flights to file...");

                    flightsDB.saveData(FLIGHTS_FILE_PATH);

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
        System.out.println("11. test. Generate flights db.");
        System.out.println("12. test. Display all flights.");
        System.out.println("13. test. Load flights from file.");
        System.out.println("14. test. Save flights to file.");


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

    private static void fillUpFlightDatabaseAutomatically(FlightController flightsDB) {

        System.out.println("Generating database of flights...");

        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
        long start = dateTime.toInstant(zoneOffset).toEpochMilli();


        try (

                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader); // Reading records one by one in a String array

        ) {

            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {

                flightsDB.saveFlight(

                        new Flight(nextRecord[0],
                                departureDateTimeGenerator(start),
                                54000000,
                                "Kiev Boryspil",
                                nextRecord[2],
                                150

                        ));

            }
        } catch (IOException e) {

            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());

        }


    }

    public static long departureDateTimeGenerator(long start) {

        long result = 0L;

        long end = start + 24 * 60 * 60 * 1000;

        result = start + ((long) (new Random().nextDouble() * (end - start)));

        return result;

    }


}

