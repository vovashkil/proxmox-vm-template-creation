package com.project.booking;

import com.opencsv.CSVReader;
import com.project.booking.Booking.Booking;
import com.project.booking.Controllers.BookingController;
import com.project.booking.Constants.DataUtil;
import com.project.booking.Constants.FileUtil;
import com.project.booking.Constants.PersonType;
import com.project.booking.Constants.Sex;
import com.project.booking.Booking.Customer;
import com.project.booking.Controllers.CustomerController;
import com.project.booking.Booking.Flight;
import com.project.booking.Controllers.FlightController;
import com.project.booking.Booking.Passenger;
import com.project.booking.Booking.Person;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.project.booking.Constants.ComUtil.*;

class ConsoleApp implements FileUtil, DataUtil {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final CustomerController customersController;
    private final FlightController flightsController;
    private final BookingController bookingsController;

    private static Customer customerApp;

    public ConsoleApp() {
        this.customersController = new CustomerController();
        this.flightsController = new FlightController();
        this.bookingsController = new BookingController();

        customersController.readData(CUSTOMERS_FILE_PATH);
    }

    void startApp() {
        flightDbFromScheduleFile(flightsController);
        flightsController.readData(FLIGHTS_FILE_PATH);
        bookingsController.readData(BOOKINGS_FILE_PATH);

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
                    displayingOnlineTable(flightsController);
                    break;
                case 2:
                    System.out.println("Displaying flight information...");
                    String flightNumber = parseAndValidateInputString(
                            "Enter Flight number: ",
                            "^[A-Za-z][A-Za-z][0-9]+",
                            "Flight number",
                            "PS0779");
                    if (flightsController.getAllFlights().stream().map(Flight::getFlightNumber).anyMatch(flightNumber::equalsIgnoreCase))
                        flightsController.getAllFlights()
                                .stream().filter(item -> item.getFlightNumber()
                                .equalsIgnoreCase(flightNumber))
                                .forEach(ConsoleApp::displayingFlightInformation);
                    else
                        System.out.println("Sorry, there is no flight " + flightNumber + " in the db.");
                    break;
                case 3:
                    System.out.println("Flight search and booking...");
                    searchAndBooking(flightsController, bookingsController);
                    break;
                case 4:
                    System.out.println("Booking cancelling...");
                    cancelBooking(bookingsController);
                    break;
                case 5:
                    System.out.println("Searching flights...");
                    System.out.println("Enter personal data, please... ");
                    String name = parseAndValidateInputString(
                            "Name: ",
                            "^[A-Z][A-Za-z ]+",
                            "Name",
                            "Vasia");
                    String surname = parseAndValidateInputString(
                            "Surname: ",
                            "^[A-Z][A-Za-z ]+",
                            "Surname",
                            "Sidorov");
                    bookingsController.getAllBookings().stream()
                            .filter(x ->
                                    bookingContainsPassengerWithName(x, name)
                                            && bookingContainsPassengerWithSurname(x, surname)
                                            || x.getCustomer().getName().equalsIgnoreCase(name)
                                            && x.getCustomer().getSurname().equalsIgnoreCase(surname))
                            .forEach(ConsoleApp::displayBookingInfo);
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
                    flightDbFromScheduleFile(flightsController);
                    break;
                case 8:
                    control = false;

                    customersController.saveData(CUSTOMERS_FILE_PATH);
                    flightsController.saveData(FLIGHTS_FILE_PATH);
                    bookingsController.saveData(BOOKINGS_FILE_PATH);
                    break;
                case 12:
                    System.out.println("Displaying entire list of flights...");
                    flightsController.displayAllFlights();
                    break;
                case 13:
                    System.out.println("Loading a list of flights from file...");
                    flightsController.readData(FLIGHTS_FILE_PATH);
                    break;
                case 14:
                    System.out.println("Saving the list of flights to file...");
                    flightsController.saveData(FLIGHTS_FILE_PATH);
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
                "Flight", "Date", "Time", "Destination", "Duration", "Available Seats");
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
                "Frankfurt");

        String date = parseAndValidateInputString(
                "Enter Date: ",
                "^[0-9][0-9]/[0-9][0-9]/[2][0][1-2][0-9]",
                "Date",
                "25/11/2019");

        int number = parseAndValidateInputInteger(
                "Enter number of passengers: ",
                1,
                flightsDB.getAllFlights()
                        .stream()
                        .mapToInt(Flight::getMaxNumSeats)
                        .max().orElse(-1));

        List<Flight> searchResult = flightsDB.getAllFlights()
                .stream()
                .filter(
                        item -> item.getDestination().equalsIgnoreCase(destination) &&
                                item.getDepartureDateTime() > parseDate(date) &&
                                ((item.getMaxNumSeats() - item.getPassengersOnBoard()) >= number)
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

            if (choice >= 1 && choice <= searchResult.size()) {
                displayingFlightInformation(searchResult.get(choice - 1));
                Booking booking = createBooking(searchResult.get(choice - 1), number);

                displayBookingInfo(booking);
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

    private static void displayBookingInfo(Booking booking) {
        final String PRINT_FORMAT = "| %-15s | %-18s | %-20s | %-19s | %6s |\n";
        final String PRINT_PASSENGER_FORMAT = "| %-7s | %-30s | %-6s | %-15s | %-20s |\n";
        final String DASHES = new String(new char[94]).replace("\0", "-");

        System.out.printf("%-94s\n", "Current Booking Information on time: "
                + LocalDateTime.now(ZoneId.of(TIME_ZONE))
                .format(DateTimeFormatter
                        .ofPattern(DATE_TIME_FORMAT)));
        System.out.printf("%s\n", DASHES);
        System.out.printf(PRINT_FORMAT, "BookingNumber", "Date and Time", "Customer Info", "E-mail", "Count");
        System.out.printf("%s\n", DASHES);
        System.out.printf(PRINT_FORMAT,
                booking.getBookingNumber(),
                booking.getDateTime().format(DateTimeFormatter
                        .ofPattern(DATE_TIME_FORMAT)),
                booking.getCustomer().getName().concat(" ").concat(booking.getCustomer().getSurname()),
                booking.getCustomer().getLoginName(),
                booking.getPassengers().size());
        System.out.printf("%s\n", DASHES);
        displayingFlightInformation(booking.getFlight());
        System.out.printf(PRINT_PASSENGER_FORMAT, "Number", "Passenger Info", "Sex", "Date Of Birth", "Passport Number");
        System.out.printf("%s\n", DASHES);

        booking.getPassengers()
                .stream()
                .forEach(person -> {
                    System.out.printf(PRINT_PASSENGER_FORMAT, booking.getPassengers().indexOf(person) + 1 + " of " + booking.getPassengers().size(),
                            person.getName().concat(" ").concat(person.getSurname()),
                            person.getSex(),
                            dateLongToString(person.getBirthDate(), DataUtil.DATE_FORMAT),
                            ((Passenger) person).getPassportNumber());
                    System.out.printf("%s\n", DASHES);
                });
    }

    private static void displayingOnlineTable(FlightController flightsDB) {
        final String PRINT_FORMAT = "| %-7s | %-10s | %-5s | %-30s | %8s |\n";
        final String DASHES = new String(new char[76]).replace("\0", "-");

        System.out.printf("%-64s\n", "Online Table Airport: Kiev Boryspil, "
                + LocalDateTime.now(ZoneId.of(TIME_ZONE))
                .format(DateTimeFormatter
                        .ofPattern(DATE_TIME_FORMAT)));

        System.out.printf("%s\n", DASHES);
        System.out.printf(PRINT_FORMAT, "Flight", "Date", "Time", "Destination", "Duration");
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

    private static Booking createBooking(Flight flight, int passagersNumber) {
        Booking result = null;

        if (flight != null && passagersNumber > 0) {
            result = new Booking(flight);
            for (int i = 0; i < passagersNumber; i++) {
                System.out.println("Enter passenger #" + (+i + +1) + "'s (of " + passagersNumber + ") personal data, please... ");
                result.addPassenger(createPerson(PersonType.PASSENGER));
            }
            result.getPassengers().forEach(flight::addPassenger);
            result.setCustomer(customerApp);
        }

        return result;
    }

    private static void cancelBooking(BookingController bookingsDB) {
        boolean control = true;

        if (bookingsDB.getAllBookings().size() == 0) {
            System.out.println("There is no booking made in the DB.");
            control = false;
        }

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

            final long choiceFinal = choice;

            if (choice == 0) {
                control = false;
            } else if (choice == -1) {
                System.out.println(
                        "Your choice is wrong. Please enter booking ID to cancel or 0 to return: ");
            } else if (!bookingsDB.getAllBookings().stream()
                    .filter(x -> x.getBookingNumber() == choiceFinal)
                    .collect(Collectors.toList()).isEmpty()) {
                System.out.println("deleteing booking");
                Booking booking = bookingsDB.getAllBookings().stream().
                        filter(item -> item.getBookingNumber() == choiceFinal)
                        .findFirst().get();
                booking.getPassengers().forEach(booking.getFlight()::deletePassenger);
                bookingsDB.deleteBookingByObject(booking);

                control = false;
            } else {
                System.out.println(
                        "There is no booking with ID=\'" + choice + "\' in gb. Please enter booking ID to cancel or 0 to return: ");
            }
        }
    }

    private static void printCancelBookingMenu(BookingController bookingsDB) {
        if (bookingsDB.getAllBookings().size() > 0)
            bookingsDB.getAllBookings().stream().forEach(System.out::println);
        else
            System.out.println("There is no booking made in the DB.");
        System.out.println("0.   Return to the main menu.");
    }

    private static void printBookings(List<Booking> bookingsList) {
        final String PRINT_FORMAT = "| %-7s | %-10s | %-5s | %-30s | %8s | %15s |\n";
        final String DASHES = new String(new char[94]).replace("\0", "-");

        System.out.printf("   %s\n   ", DASHES);
        System.out.printf(
                PRINT_FORMAT,
                "Flight", "Date", "Time", "Destination", "Duration", "Available Seats");
        System.out.printf("   %s\n", DASHES);

        bookingsList
                .forEach(booking -> {
                    System.out.print(bookingsList.indexOf(booking) + +1 + ". ");
                    printBooking(booking, PRINT_FORMAT);
                });
        System.out.println("0.   Return to the main menu.");
    }

    private static void printBooking(Booking booking, String format) {
        System.out.printf(format,
                booking.getBookingNumber(),
                booking.getDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                booking.getDateTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT))
        );
        printFlight(booking.getFlight(), "%s");
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
                    customerApp = customersController.getCustomerByLogin(loginName, password);
                    if (customerApp != null) {
                        System.out.printf("%s %s, Welcome to booking!!!\n", customerApp.getSurname(), customerApp.getName());
                        result = true;
                    } else {
                        System.out.println("Invalid Username & Password!");
                    }
                    return result;
                case "REGISTER":
                    customerApp = (Customer) createPerson(PersonType.CUSTOMER);
                    customersController.saveCustomer(customerApp);
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

    public void closeSession() {
        customerApp = null;
        loginCustomer();
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
                "Sex (Male or Female):  ",
                "(?i)Male|(?i)Female",
                "Sex",
                "Male").toUpperCase());

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
        flightsDB.saveData(FLIGHTS_FILE_PATH);
    }

    private static boolean bookingContainsPassengerWithName(Booking booking, String name) {
        return
                !booking.getPassengers().stream()
                        .filter(x -> x.getName().equalsIgnoreCase(name))
                        .collect(Collectors.toList()).isEmpty();
    }

    private static boolean bookingContainsPassengerWithSurname(Booking booking, String surname) {
        return
                !booking.getPassengers().stream()
                        .filter(x -> x.getSurname().equalsIgnoreCase(surname))
                        .collect(Collectors.toList()).isEmpty();
    }
}

