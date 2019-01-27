package com.project.booking;

import com.opencsv.CSVReader;
import com.project.booking.Booking.BookingController;
import com.project.booking.Constants.DataUtil;
import com.project.booking.Constants.FileUtil;
import com.project.booking.Constants.Sex;
import com.project.booking.Customer.Customer;
import com.project.booking.Customer.CustomerController;
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

    private final CustomerController customersDB;
    LocalDateTime currDateTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    private ZoneOffset offset = currDateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
    private long currentDateTime = currDateTime.toInstant(offset).toEpochMilli();

    public ConsoleApp() {
        this.customersDB = new CustomerController();
        customersDB.readData(CUSTOMERS_FILE_PATH);
    }

    public long getCurrentDateTime() {
        return currentDateTime;
    }

    public ZoneOffset getZoneOffset() {
        return offset;
    }


    void startApp() {

        FlightController flightsDB = new FlightController();
        BookingController bookingsDB = new BookingController();

        flightsDB.readData(FLIGHTS_FILE_PATH);
//        bookingsDB.readData(BOOKINGS_FILE_PATH);

        boolean control = true;

        while (control) {

            printMenuMain();

            Scanner input = new Scanner(System.in);
            System.out.print("Please enter your choice [1-8]: ");
            int choice;

            try {

                choice = input.nextInt();

            } catch (InputMismatchException e) {

                choice = -1;

            }

            switch (choice) {
                case 1:

                    System.out.println("Displaying online table...");

                    displayingOnlineTable(flightsDB);

                    break;

                case 2:

                    System.out.println("Displaying flight information...");

                    String flightNumber = parseAndValidateInputString(
                            "Enter Flight number: ",
                            "^[A-Za-z][A-Za-z][0-9]+",
                            "Flight number",
                            "PS0779"
                    );

                    if (flightsDB.getAllFlights().stream().map(Flight::getFlightNumber).anyMatch(flightNumber::equalsIgnoreCase))

                        flightsDB.getAllFlights()
                                .stream().filter(item -> item.getFlightNumber()
                                .equalsIgnoreCase(flightNumber))
                                .forEach(ConsoleApp::displayingFlightInformation);

                    else
                        System.out.println("Sorry, there is no flight " + flightNumber + " in the db.");

                    break;

                case 3:

                    System.out.println("Flight search and booking...");

                    String destination = parseAndValidateInputString(
                            "Enter Destination: ",
                            "^[A-Z][a-z]+",
                            "Destination",
                            "Frankfurt"
                    );

                    System.out.println(destination);

                    break;

                case 4:

                    break;

                case 5:

                    break;

                case 6:

                    break;

                case 7:

                    System.out.println("Creating new list of flights from schedule...");

                    flightDbFromScheduleFile(flightsDB);

                    break;

                case 8:

                    control = false;

                    customersDB.saveData(CUSTOMERS_FILE_PATH);
                    flightsDB.saveData(FLIGHTS_FILE_PATH);
                    bookingsDB.saveData(BOOKINGS_FILE_PATH);

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

        System.out.println("1. Online table.");
        System.out.println("2. Flight information.");
        System.out.println("3. Flights search and booking.");
        System.out.println("4. Booking cancelling.");
        System.out.println("5. My flights.");
        System.out.println("6. Close session.");
        System.out.println("7. Resetting/Re-creating flights db from schedule file.");
        System.out.println("8. Exit.");
        System.out.println("11. test. Generate flights db.");
        System.out.println("12. test. Display all flights.");
        System.out.println("13. test. Load flights from file.");
        System.out.println("14. test. Save flights to file.");


    }

    public Customer loginCustomer() {
        Customer result = null;
        String loginName;
        String password;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            System.out.println("Enter \"login\", \"register\", or \"exit\"");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "LOGIN":
                    System.out.print("Enter LoginName: ");
                    loginName = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();
                    result = customersDB.getCustomerByLogin(loginName, password);
                    if (result != null) {
                        System.out.printf("%s %s, Welcome to booking!!!\n", result.getSurname(), result.getName());

                    }
/*
  JOptionPane.showMessageDialog(null,"Invalid User Name or Password","Error",JOptionPane.ERROR_MESSAGE);

if (username.equals(Username) && password.equals(Password)) {

        System.out.println("Access Granted! Welcome!");
    }

    else if (username.equals(Username)) {
        System.out.println("Invalid Password!");
    } else if (password.equals(Password)) {
        System.out.println("Invalid Username!");
    } else {
        System.out.println("Invalid Username & Password!");
    }

    if (x == rec.getString("users_name")) {
                if (y == rec.getString("users_password")) {
                    System.out.println("Logged in!");
                } else {
                    System.out.println("Password did not match username!");
                }
            } else {
                    [color=red]System.out.println("Username did not match the database");[/color]
            }
* */
                    return result;
                case "REGISTER":
                    System.out.println("Enter your personal data, please... ");
                    System.out.print("LoginName: ");
                    loginName = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    password = scanner.nextLine().trim();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Surname: ");
                    String surname = scanner.nextLine();
                    System.out.print("BirthDate (dd/MM/yyyy): ");
                    Long birthdate = LocalDate.parse(scanner.nextLine(), formatter).atStartOfDay(ZoneId.of(TIME_ZONE)).toEpochSecond();
                    System.out.print("Sex (M - male, F - female): ");
                    String sex = scanner.nextLine();
                    //result = new Customer(name, surname, birthdate, Sex.valueOf(sex), loginName, password);
                    result = new Customer(name, surname, birthdate, Sex.FEMALE, loginName, password);
                    customersDB.saveCustomer(result);
                    System.out.println(result.toString());
                    return result;
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

    private static void displayingOnlineTable(FlightController flightsDB) {

        final String PRINT_FORMAT = "| %-7s | %-10s | %-5s | %-30s | %8s |\n";
        final String DASHES = new String(new char[76]).replace("\0", "-");

        System.out.printf("%-64s\n", "Online Table Airport: Kiev Boryspil, "
                + LocalDateTime.now(ZoneId.of(TIME_ZONE))
                .format(DateTimeFormatter
                        .ofPattern(DATE_TIME_FORMAT)));

        System.out.printf("%s\n", DASHES);

        System.out.printf(PRINT_FORMAT,
                "Flight", "Date", "Time", "Destination", "Duration"
        );

        System.out.printf("%s\n", DASHES);

        flightsDB.getAllFlights()
                .stream()
                .sorted((a, b) -> (int) (a.getDepartureDateTime() - b.getDepartureDateTime()))
                .forEach(flight -> System.out.printf(PRINT_FORMAT,
                        flight.getFlightNumber(),
                        Instant.ofEpochMilli(flight.getDepartureDateTime())
                                .atZone(ZoneId.of(TIME_ZONE))
                                .toLocalDateTime()
                                .format(DateTimeFormatter
                                        .ofPattern(DATE_FORMAT)),
                        Instant.ofEpochMilli(flight.getDepartureDateTime())
                                .atZone(ZoneId.of(TIME_ZONE))
                                .toLocalDateTime()
                                .format(DateTimeFormatter
                                        .ofPattern(TIME_FORMAT)),
                        flight.getDestination(),
                        LocalTime.ofNanoOfDay(flight.getEstFlightDuration())
                                .format(DateTimeFormatter.ofPattern(TIME_FORMAT))

                ));

        System.out.printf("%s\n", DASHES);

    }

    private static void displayingFlightInformation(Flight flight) {

        final String PRINT_FORMAT = "| %-7s | %-10s | %-5s | %-30s | %8s | %15s |\n";
        final String DASHES = new String(new char[94]).replace("\0", "-");

        System.out.printf("%s\n", "Flight infomation:");
        System.out.printf("%s\n", DASHES);

        System.out.printf(PRINT_FORMAT,
                "Flight", "Date", "Time", "Destination", "Duration", "Available Seats"
        );

        System.out.printf("%s\n", DASHES);

        System.out.printf(PRINT_FORMAT,
                flight.getFlightNumber(),
                Instant.ofEpochMilli(flight.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(DATE_FORMAT)),
                Instant.ofEpochMilli(flight.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(TIME_FORMAT)),
                flight.getDestination(),
                LocalTime.ofNanoOfDay(flight.getEstFlightDuration())
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
                flight.getMaxNumSeats() - flight.getPassengersOnBoard()

        );

        System.out.printf("%s\n", DASHES);

    }

    private static void flightDbFromScheduleFile(FlightController flightsDB) {

        System.out.println("Resetting/Generating new database of flights...");

        LocalTime currentTime = LocalTime.now(ZoneId.of(TIME_ZONE));
        LocalDate currentDate = LocalDate.now(ZoneId.of(TIME_ZONE));

        try (

                Reader reader = Files.newBufferedReader(Paths.get(KBP_SCHEDULE_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader, ',', '\'', 1);

        ) {

            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {

                long flightDepartureTimeLong = parseTime(nextRecord[1]);
                long flightDurationTimeLong = parseTime(nextRecord[7]);
                LocalDate flightDepartureDate = currentDate;

                if (flightDepartureTimeLong <= currentTime.toNanoOfDay())
                    flightDepartureDate = currentDate.plusDays(1);

                long departureDateTimeLong = dateTimeToLong(
                        LocalDateTime.of(
                                flightDepartureDate,
                                LocalTime.ofNanoOfDay(flightDepartureTimeLong)
                        )
                );

                flightsDB.saveFlight(

                        new Flight(nextRecord[0],
                                departureDateTimeLong,
                                flightDurationTimeLong,
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

    public static long parseTime(String str) {

        LocalTime time = LocalTime.now(ZoneId.of(TIME_ZONE));

        str = str.replaceAll("[^0-9,:]", "");

        try {

            time = LocalTime.parse(str, DateTimeFormatter.ofPattern(TIME_FORMAT));

        } catch (DateTimeParseException e) {

            System.out.println(e.getStackTrace());

        }

        return time.toNanoOfDay();

    }

    private int parseAndValidateInputInteger(String message, int startRange, int endRange) {

        int result = 0;
        boolean control = true;

        System.out.print(message);

        while (control) {
            Scanner input = new Scanner(System.in);

            try {

                result = input.nextInt();

            } catch (InputMismatchException e) {

                System.out.print("You entered incorrect type. ");
                result = -1;

            }

            if (result >= startRange && result <= endRange) control = false;
            else System.out.print("Enter correct number between " +
                    startRange + " and " + endRange + " : ");

        }
        return result;
    }

    private String parseAndValidateInputString(String message, String pattern, String name, String example) {

        String result = "";
        boolean control = true;

        System.out.print(message);

        Scanner input = new Scanner(System.in);

        while (control) {

            result = input.nextLine().trim();

            if (result.trim().matches(pattern)) control = false;
            else System.out.print("Enter correct " + name + " \'" + pattern + "\' (e.g. " + example + "): ");

        }
        return result;
    }

    static long dateTimeToLong(LocalDateTime dateTime) {

        ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
        return dateTime.toInstant(zoneOffset).toEpochMilli();

    }


}

