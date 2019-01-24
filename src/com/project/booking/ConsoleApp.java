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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

                    System.out.println("Displaying entire list of flights...");

                    flightsDB.displayAllFlights();

                    break;

                case 12:

                    fillUpFlightDatabaseAutomatically(flightsDB);

                    break;


                case 13:



                    break;


                case 14:

                    

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

    private void fillUpFlightDatabaseAutomatically(FlightController flightsDB) {
        System.out.println("Generating database of flights...");

        List<Flight> flights = new ArrayList();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            // Reading Records One by One in a String array
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {

                long result;
                String dateAsString = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
                System.out.println("dateAsString = " + dateAsString);
                String tmp = nextRecord[1];
                System.out.println("nextRecord[1] = " + tmp);
                String tmpDateTime = dateAsString + "T" + tmp;
                System.out.println("dataTime = " + tmpDateTime);

                int H = 0;
                int m = 0;
                String[] strings = tmp.split(":");

                try {
                    H = Integer.parseInt(strings[0]);
                    m = Integer.parseInt(strings[1]);
                } catch (NumberFormatException e) {
                    H = 0;
                    m = 0;
                }

                StringBuffer strBuffer = new StringBuffer(dateAsString);
                strBuffer.append(" ");
                strBuffer.append(H + ":" + m);
                tmpDateTime = strBuffer.toString();

                CharSequence cs = new String(tmpDateTime);

                System.out.println("final dataTime = " + tmpDateTime);
                System.out.println("exception at " + tmpDateTime.substring(11));

                try {

//                    dateAsString = Instant.ofEpochMilli(result).atZone(ZoneId.of(TIME_ZONE)).toLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));

                    LocalDateTime dateTime = LocalDateTime.parse(cs, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
                    ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
                    result = dateTime.toInstant(zoneOffset).toEpochMilli();


                } catch (DateTimeParseException e) {

                    System.out.println("exception" + e.getMessage());
                    System.out.println("ErrorIndex" + e.getErrorIndex());
                    System.out.println("parsed" + e.getParsedString());

                    result = Long.MIN_VALUE;

                }


                flights.add(new Flight(nextRecord[0],
                        "Kiev Boryspil",
                        nextRecord[2],
                        100,
                        result,
                        54000000
                ));


            }
        } catch (IOException e) {

            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());

        }


        flights.stream().forEach(System.out::println);


//        LocalDate date1 = LocalDate.of(2018, 12, 13);
//
//        long dateAslong = date1.atStartOfDay(ZoneId.of(TimeZone)).
//
//        System.out.println(date1);


        long result;

        LocalTime time = LocalTime.parse("01:30", DateTimeFormatter.ofPattern(TIME_FORMAT));
        System.out.println("result = " + time);

        time = LocalTime.parse("10:30", DateTimeFormatter.ofPattern(TIME_FORMAT));
        System.out.println("result = " + time);

        time = LocalTime.parse("15:30", DateTimeFormatter.ofPattern(TIME_FORMAT));
        System.out.println("result = " + time);

        //        ZoneOffset zoneOffset = ZoneOffset.of;
//        result = time.toInstant(zoneOffset).toEpochMilli();


        String dateTimeAsString = "23/01/2019" + " " + "21:23";


        try {

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
            result = dateTime.toInstant(zoneOffset).toEpochMilli();

        } catch (DateTimeParseException e) {

            result = Long.MIN_VALUE;

        }

        System.out.println("result = " + result);
        System.out.println(Instant.ofEpochMilli(result).atZone(ZoneId.of(TIME_ZONE)).toLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));


    }


}

