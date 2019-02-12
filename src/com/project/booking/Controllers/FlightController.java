package com.project.booking.Controllers;

import com.project.booking.Booking.Flight;
import com.project.booking.Constants.DataUtil;
import com.project.booking.Services.FlightService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.booking.Constants.ComUtil.*;

public class FlightController implements DataUtil {

    private FlightService flightService = new FlightService();
    private int passengersCount = 0;

    public int getPassengersCount() {
        return passengersCount;
    }

    public void setPassengersCount(int passengersCount) {
        this.passengersCount = passengersCount;
    }

    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    public void displayAllFlights() {

        flightService.displayAllFlights();

    }

    public void saveFlight(Flight flight) {

        flightService.saveFlight(flight);

    }

    public void saveData(String filePath) {

        flightService.saveData(filePath);

    }

    public void readData(String filePath) {

        flightService.readData(filePath);

    }

    public void deleteFlightByIndex(int index) {

        flightService.deleteFlightByIndex(index);

    }

    public void deleteFlightByObject(Flight flight) {

        flightService.deleteFlightByObject(flight);

    }

    public int count() {

        return flightService.count();

    }

    public Flight getFlightById(int index) {

        return flightService.getFlightById(index);

    }

    public void printAllSortedCurrent24Hours(String format) {

        flightService.getAllFlights()
                .stream()
                .sorted(Comparator.comparingLong(Flight::getDepartureDateTime))
                .forEach(flight -> printFlight(flight, format)
                );

    }

    public void printFlight(Flight flight, String format) {

        System.out.printf(format,
                flight.getFlightNumber(),
                dateLongToString(flight.getDepartureDateTime(), DATE_FORMAT),
                dateLongToString(flight.getDepartureDateTime(), TIME_FORMAT),
                flight.getDestination(),
                timeOfDayLongToString(flight.getEstFlightDuration())
        );

    }

    public Flight getByFlightNumber(String flightNumber) {

        return flightService.getAllFlights()
                .stream()
                .filter(item -> item.getFlightNumber()
                        .equalsIgnoreCase(flightNumber)).findFirst().orElse(null);

    }

    public int getMaxSeatNumber() {

        return
                flightService.getAllFlights()
                        .stream()
                        .mapToInt(Flight::getMaxNumSeats)
                        .max().orElse(-1);

    }

    public List<Flight> getFlightsMatchedCriteria(String destination, String date, int passengersNumber) {

        return
                flightService.getAllFlights()
                        .stream()
                        .filter(
                                item -> item.getDestination().equalsIgnoreCase(destination) &&
                                        item.getDepartureDateTime() > parseDate(date) &&
                                        ((item.getMaxNumSeats() - item.getPassengersOnBoard()) >= passengersNumber)
                        )
                        .sorted(Comparator.comparingLong(Flight::getDepartureDateTime))
                        .collect(Collectors.toList());


    }

    public void displayFlightInformationWithSeats(Flight flight) {
        final String PRINT_FORMAT = "| %-7s | %-10s | %-5s | %-30s | %8s | %15s |\n";
        final String DASHES = new String(new char[94]).replace("\0", "-");

        System.out.printf("%s\n", "Flight infomation:");
        System.out.printf("%s\n", DASHES);

        System.out.printf(PRINT_FORMAT,
                "Flight", "Date", "Time", "Destination", "Duration", "Available Seats");

        System.out.printf("%s\n", DASHES);

        printFlightWithSeats(flight, PRINT_FORMAT);

        System.out.printf("%s\n", DASHES);
    }

    public void printFlightWithSeats(Flight flight, String format) {
        if (flight != null && format.length() > 0)
            System.out.printf(format,
                    flight.getFlightNumber(),
                    dateLongToString(flight.getDepartureDateTime(), DATE_FORMAT),
                    dateLongToString(flight.getDepartureDateTime(), TIME_FORMAT),
                    flight.getDestination(),
                    timeOfDayLongToString(flight.getEstFlightDuration()),
                    flight.getMaxNumSeats() - flight.getPassengersOnBoard()
            );
    }

    public List<Flight> searchFlightsForBooking() {
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

        this.passengersCount = parseAndValidateInputInteger(
                "Enter number of passengers: ",
                1,
                getMaxSeatNumber());
        return getFlightsMatchedCriteria(destination, date, this.passengersCount);
    }

    public void printMultipleFlightsWithOrderNumbers(List<Flight> flights, String format) {

        if (flights.size() > 0)
            flights.forEach(flight -> {
                System.out.print(flights.indexOf(flight) + +1 + ". ");
                printFlightWithSeats(flight, format);
            });

    }

    public void printListFlightsdResultMenu(List<Flight> flights) {
        System.out.println("Found flights matched criteria...");

        final String PRINT_FORMAT = "| %-7s | %-10s | %-5s | %-30s | %8s | %15s |\n";
        final String DASHES = new String(new char[94]).replace("\0", "-");

        System.out.printf("   %s\n   ", DASHES);
        System.out.printf(
                PRINT_FORMAT,
                "Flight", "Date", "Time", "Destination", "Duration", "Available Seats");
        System.out.printf("   %s\n", DASHES);

        printMultipleFlightsWithOrderNumbers(flights, PRINT_FORMAT);

        System.out.println("0.   Return to the main menu.");
    }
}
