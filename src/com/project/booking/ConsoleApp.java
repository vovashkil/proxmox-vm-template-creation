package com.project.booking;

import com.opencsv.CSVReader;
import com.project.booking.Booking.Booking;
import com.project.booking.Booking.BookingController;
import com.project.booking.Constants.DataUtil;
import com.project.booking.Constants.FileUtil;
import com.project.booking.Constants.PersonType;
import com.project.booking.Constants.Sex;
import com.project.booking.Persons.Customer;
import com.project.booking.Persons.CustomerController;
import com.project.booking.Flight.Flight;
import com.project.booking.Flight.FlightController;
import com.project.booking.Persons.Passenger;
import com.project.booking.Persons.Person;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class ConsoleApp implements FileUtil, DataUtil {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final CustomerController customersDB;
    private static Customer customerApp;

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
        bookingsDB.readData(BOOKINGS_FILE_PATH);

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

                    searchAndBooking(flightsDB, bookingsDB);

                    break;

                case 4:

                    System.out.println("Booking cancelling...");

                    cancelBooking(bookingsDB);

                    break;

                case 5:

                    break;

                case 6:
                    for (; ; ) {
                        if (loginCustomer()) {
                            break;
                        }
                    }
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

    private static void printSearchResultsMenu(List<Flight> flightList) {

        System.out.println("Found flights matched criteria...");

        final String PRINT_FORMAT = "| %-7s | %-10s | %-5s | %-30s | %8s | %15s |\n";
        final String DASHES = new String(new char[94]).replace("\0", "-");

        System.out.printf("   %s\n   ", DASHES);
        System.out.printf(
                PRINT_FORMAT,
                "Flight", "Date", "Time", "Destination", "Duration", "Available Seats"
        );
        System.out.printf("   %s\n", DASHES);


        flightList
                .forEach(flight -> {

                    System.out.print(flightList.indexOf(flight) + +1 + ". ");
                    ConsoleApp.printFlight(flight, PRINT_FORMAT);

                });

        System.out.println("0.   Return to the main menu.");

    }

    private static void searchAndBooking(FlightController flightsDB, BookingController bookingsDB) {

        String destination = parseAndValidateInputString(
                "Enter Destination: ",
                "^[A-Z][A-Za-z ]+",
                "Destination",
                "Frankfurt"
        );

        String date = parseAndValidateInputString(
                "Enter Date: ",
                "^[0-9][0-9]/[0-9][0-9]/[2][0][1-2][0-9]",
                "Date",
                "25/11/2019"
        );

        int number = parseAndValidateInputInteger(
                "Enter number of passengers: ",
                1,
                flightsDB.getAllFlights()
                        .stream()
                        .mapToInt(Flight::getMaxNumSeats)
                        .max().orElse(-1)
        );

        List<Flight> searchResult = flightsDB.getAllFlights()
                .stream()
                .filter(
                        item -> item.getDestination().equalsIgnoreCase(destination) &&
                                item.getDepartureDateTime() > parseDate(date) &&
                                (item.getMaxNumSeats() - item.getPassengersOnBoard()) >= number
                )
                .sorted(Comparator.comparingLong(Flight::getDepartureDateTime))
                .collect(Collectors.toList());

        if (searchResult.isEmpty()) {

            System.out.println("Sorry, no flight matching the criteria found. Repeat yor search.");
            return;

        }

        boolean control = true;

        while (control) {

            printSearchResultsMenu(searchResult);

            Scanner input = new Scanner(System.in);
            System.out.print("Please enter flight order number [1-" +
                    searchResult.size() + "] to book or 0 to return: ");

            int choice;

            try {

                choice = input.nextInt();

            } catch (InputMismatchException e) {

                choice = -1;

            }

            if (choice >= 1 && choice < searchResult.size()) {

                displayingFlightInformation(searchResult.get(choice - 1));
//                List<Passenger> passengerList = enteringPassengersData(number);
//                System.out.println(passengerList);
                Booking  booking = createBooking(searchResult.get(choice - 1), number);

                System.out.println(booking);
                System.out.println("Your booking is :" + booking);

                bookingsDB.saveBooking(booking);

                control = false;

            } else if (choice == 0) {

                control = false;

            } else

                System.out.println(
                        "Your choice is wrong. Please enter the flight order number [1-" +
                                searchResult.size() + "] to book or 0 to return.");

        }

    }

    public void closeSession() {
        customerApp = null;
        loginCustomer();
    }


    private static void cancelBooking(BookingController bookingsDB) {

        boolean control = true;

        while (control) {

            printCancelBookingMenu(bookingsDB);

            Scanner input = new Scanner(System.in);
            System.out.print("Please enter booking ID to cancel or 0 to return: ");

            long choice;

            try {

                choice = input.nextLong();

            } catch (InputMismatchException e) {

                choice = -1;

            }

            if (bookingsDB.getAllBookings().stream().mapToLong(Booking::getBookingNumber).findAny().isPresent()) {

                final long choiceFinal = choice;
                System.out.println("deleteing booking");

                Booking object = bookingsDB.getAllBookings()
                        .stream().filter(x->x.getBookingNumber() == choiceFinal)
                        .findFirst().orElseGet(null);
                if (object != null) bookingsDB.getAllBookings().remove(object);

                control = false;

            } else if (choice == 0) {

                control = false;

            } else

                System.out.println(
                        "Your choice is wrong. Please enter booking ID to cancel or 0 to return: ");

        }

    }

    private static void printCancelBookingMenu(BookingController bookingsDB) {

        bookingsDB.getAllBookings().stream().forEach(System.out::println);
        System.out.println("0.   Return to the main menu.");

    }

    public boolean loginCustomer() {

        LOGGER.setLevel(Level.INFO);
        LOGGER.info("Try login for booking ticket");

        boolean result = false;
        customerApp = null;
        String loginName;
        String password;


        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            System.out.println("Enter \"login\", \"register\", or \"exit\"");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "LOGIN":
                    System.out.print("LoginName: ");
                    loginName = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();
                    customerApp = customersDB.getCustomerByLogin(loginName, password);
                    if (customerApp != null) {
                        System.out.printf("%s %s, Welcome to booking!!!\n", customerApp.getSurname(), customerApp.getName());
                        result = true;
                    } else {
                        System.out.println("Invalid Username & Password!");
                    }
                    return result;
                case "REGISTER":
                    customerApp = (Customer) createPerson(PersonType.CUSTOMER);
                    customersDB.saveCustomer(customerApp);
                    if (customerApp != null) {
                        System.out.printf("%s %s, Welcome to booking!!!\n", customerApp.getSurname(), customerApp.getName());
                        result = true;
                    } else {
                        System.out.println("Customer is not registered!");
                    }
                    return result;
                case "EXIT":
                    System.exit(0);
                    break;
                default:
                    System.out.printf("Invalid option (%s), choose login or register!%n", input);
            }
        }
    }

    private static Person createPerson(PersonType personType) {
        Person result;

        System.out.println("Enter personal data, please... ");

        String name = parseAndValidateInputString(
                "Name: ",
                "^[A-Z][A-Za-z ]+",
                "Name",
                "Vasia"
        );
        String surname = parseAndValidateInputString(
                "Surname: ",
                "^[A-Z][A-Za-z ]+",
                "Surname",
                "Sidorov"
        );
        long birthdate =
                parseDate(
                        parseAndValidateInputString(
                                "BirthDate (dd/MM/yyyy): ",
                                "^[0-9][0-9]/[0-9][0-9]/[12][09][0-9][0-9]",
                                "Date",
                                "21/07/1990"
                        ));
        Sex sex = Sex.valueOf(parseAndValidateInputString(
                "Sex (MALE OR FEMALE):  ",
                "MALE|FEMALE",
                "Sex",
                "MALE"
        ));

        if (personType == PersonType.CUSTOMER) {
            String loginName = parseAndValidateInputString(
                    "LoginName (your e-mail): ",
                    "^(.+)@(.+)$",
                    "LoginName",
                    "Ivanov@gmail.com"
            );
            String password = parseAndValidateInputString(
                    "Password: ",
                    "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",
                    "Password",
                    "aaZZa44@"
            );
            /*
            (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{8,} at least 8 characters
            */
            result = new Customer(name, surname, birthdate, sex, loginName, password);
        } else {
            String passNumber = parseAndValidateInputString(
                    "Passport Number: ",
                    "^[A-Z][A-Z][0-9]+",
                    "Passport Number",
                    "AK876543"
            );
            result = new Passenger(name, surname, birthdate, sex, passNumber);
        }
        return result;
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
                .sorted(Comparator.comparingLong(Flight::getDepartureDateTime))
                .forEach(flight -> System.out.printf(PRINT_FORMAT,
                        flight.getFlightNumber(),
                        dateLongToString(flight.getDepartureDateTime(), DATE_FORMAT),
                        dateLongToString(flight.getDepartureDateTime(), TIME_FORMAT),
                        flight.getDestination(),
                        timeOfDayLongToString(flight.getEstFlightDuration()))

                );

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

        printFlight(flight, PRINT_FORMAT);

        System.out.printf("%s\n", DASHES);

    }

    private static void printFlight(Flight flight, String format) {
        System.out.printf(format,
                flight.getFlightNumber(),
                dateLongToString(flight.getDepartureDateTime(), DATE_FORMAT),
                dateLongToString(flight.getDepartureDateTime(), TIME_FORMAT),
                flight.getDestination(),
                timeOfDayLongToString(flight.getEstFlightDuration()),
                flight.getMaxNumSeats() - flight.getPassengersOnBoard()
        );
    }

    private static List<Passenger> enteringPassengersData(int number) {

        ArrayList<Passenger> passengersList = new ArrayList<>();
        Passenger passenger;

        for (int i = 0; i < number; i++) {
            System.out.println("Enter passenger #" + (+i + +1) + "'s (of " + number + ") personal data, please... ");
            passenger = (Passenger) createPerson(PersonType.PASSENGER);
            passengersList.add(passenger);
            System.out.println(passengersList);
        }
        return passengersList;
    }

    private static Booking createBooking(Flight flight, int passagersNumber) {

        Booking result = null;

        if (flight != null && passagersNumber > 0) {

            result = new Booking(flight);

            for (int i = 0; i < passagersNumber; i++) {
                System.out.println("Enter passenger #" + (+i + +1) + "'s (of " + passagersNumber + ") personal data, please... ");

                result.addPassenger(createPerson(PersonType.PASSENGER));

            }
        }

        // add passengers from booking to flight

        return result;

    }

    private static long parseTime(String str) {

        LocalTime time = LocalTime.now(ZoneId.of(TIME_ZONE));

        str = str.replaceAll("[^0-9,:]", "");

        try {

            time = LocalTime.parse(str, DateTimeFormatter.ofPattern(TIME_FORMAT));

        } catch (DateTimeParseException e) {

            System.out.println(e.getStackTrace());

        }

        return time.toNanoOfDay();

    }

    private static long parseDate(String str) {

        LocalDate date = LocalDate.now(ZoneId.of(TIME_ZONE));
        ZoneOffset zoneOffset = date.atStartOfDay(ZoneId.of(TIME_ZONE)).getOffset();

        str = str.replaceAll("[^0-9,/]", "");

        try {

            date = LocalDate.parse(str, DateTimeFormatter.ofPattern(DATE_FORMAT));

        } catch (DateTimeParseException e) {

            System.out.println(e.getStackTrace());

        }

//        return date.atStartOfDay(ZoneId.of(TIME_ZONE)).toInstant().toEpochMilli();
        return date.atStartOfDay().toInstant(zoneOffset).toEpochMilli();

    }

    private static int parseAndValidateInputInteger(String message, int startRange, int endRange) {

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

    private static String parseAndValidateInputString(String message, String pattern, String name, String example) {

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

    private static long dateTimeToLong(LocalDateTime dateTime) {

        ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
        return dateTime.toInstant(zoneOffset).toEpochMilli();

    }

    private static String dateLongToString(Long dateTime, String format) {

        return Instant.ofEpochMilli(dateTime)
                .atZone(ZoneId.of(TIME_ZONE))
                .toLocalDateTime()
                .format(DateTimeFormatter
                        .ofPattern(format));

    }

    private static String timeOfDayLongToString(Long time) {

        return LocalTime.ofNanoOfDay(time)
                .format(DateTimeFormatter.ofPattern(TIME_FORMAT));

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

}

