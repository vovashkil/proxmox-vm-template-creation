package com.project.booking.Flight;

import com.project.booking.Constants.DataUtil;
import com.project.booking.Passenger.Passenger;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Flight implements DataUtil {

    private String flightNumber;
    private String origin;
    private String destination;
    private List<Passenger> passengers;
    private int passengersOnBoard;
    private int maxNumSeats;
    private long departureDateTime;
    private long estFlightDuration;

    public Flight(String flightNumber, String origin, String destination, int maxNumSeats, long departureDateTime, long estFlightDuration) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.passengers = new ArrayList();
        this.passengersOnBoard = 0;
        this.maxNumSeats = maxNumSeats;
        this.departureDateTime = departureDateTime;
        this.estFlightDuration = estFlightDuration;
    }

    public Flight(String flightNumber, String origin, String destination, int maxNumSeats, String departureDateTime, String estFlightDuration) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.passengers = new ArrayList();
        this.passengersOnBoard = 0;
        this.maxNumSeats = maxNumSeats;
        this.departureDateTime = dateTimeAsStringParser(departureDateTime);
        this.estFlightDuration = timeAsStringParser(estFlightDuration);
    }

    private long dateTimeAsStringParser(String dateTimeAsString) {

        long result;
        System.out.println(dateTimeAsString);

        try {

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
            result = dateTime.toInstant(zoneOffset).toEpochMilli();

        } catch (DateTimeParseException e) {

            result = Long.MIN_VALUE;

        }

        return result;
    }

    private long timeAsStringParser(String timeAsString) {

        long result;

        try {
            System.out.println("timeAsString = " + timeAsString);
            System.out.println("timeAsString class = " + timeAsString.getClass().getSimpleName());

          result = LocalTime.parse(timeAsString, DateTimeFormatter.ofPattern(TIME_FORMAT)).toNanoOfDay();
//            ZoneOffset zoneOffset = ZoneOffset.of(TIME_ZONE);
//            result = time.toInstant(zoneOffset).toEpochMilli();
            System.out.println("result = " + result);

            System.out.println("result = " + result);

        } catch (DateTimeParseException e) {
            System.out.println("exception timeAsString = " + timeAsString);
            System.out.println(e.getParsedString());
            System.out.println("getCause = " + e.getCause());
//            result = Long.MIN_VALUE;
            result = 5L;

        }

        return result;
    }


    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(long departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public long getEstFlightDuration() {
        return estFlightDuration;
    }

    public void setEstFlightDuration(long estFlightDuration) {
        this.estFlightDuration = estFlightDuration;
    }

    public int getPassengersOnBoard() {
        return passengers.size();
    }

    public boolean addPassenger(Passenger passenger) {

        if (passengers.size() >= maxNumSeats) {

            return false;

        }

        passengers.add(passenger);
        return true;

    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime='" +
//                LocalDateTime.of(departureDateTime).format(DateTimeFormatter.ofPattern(TIME_FORMAT))
        Instant.ofEpochMilli(departureDateTime).atZone(ZoneId.of(TIME_ZONE)).toLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                + '\'' +
                ", passengersOnBoard=" + passengersOnBoard +
                ", maxNumSeats=" + maxNumSeats +
                '}';
    }


}
