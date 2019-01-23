package com.project.booking.Flight;

import com.project.booking.Constants.DataUtil;
import com.project.booking.Passenger.Passenger;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class Flight implements DataUtil {

    private String flightNumber;
    private String origin;
    private String destination;
    private List<Passenger> passengers;
    private int passengersOnBoard;
    private int maxNumSeats;
    private long departureTime;
    private long estFlightDuration;

    public Flight(String flightNumber, String origin, String destination, int maxNumSeats, long departureTime, long estFlightDuration) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.passengers = new ArrayList();
        this.passengersOnBoard = 0;
        this.maxNumSeats = maxNumSeats;
        this.departureTime = departureTime;
        this.estFlightDuration = estFlightDuration;
    }

    public Flight(String flightNumber, String origin, String destination, int maxNumSeats, String departureTime, String estFlightDuration) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.passengers = new ArrayList();
        this.passengersOnBoard = 0;
        this.maxNumSeats = maxNumSeats;
        this.departureTime = timeAsStringParser(departureTime);
        this.estFlightDuration = timeAsStringParser(estFlightDuration);
    }

    private long timeAsStringParser(String timeAsString) {

        long result;

        try {
            System.out.println("timeAsString = " + timeAsString);

            LocalTime time = LocalTime.parse(timeAsString, DateTimeFormatter.ofPattern(DEPARTURE_TIME_FORMAT));
//            ZoneOffset zoneOffset = ZoneOffset.of(TIME_ZONE);
//            result = time.toInstant(zoneOffset).toEpochMilli();
            result = time.getLong(ChronoField.MILLI_OF_DAY);
            System.out.println("result = " + result);

        } catch (DateTimeParseException e) {
            System.out.println("exception timeAsString = " + timeAsString);
            System.out.println(e.getParsedString());
            System.out.println(e.getCause());
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

    public long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(long departureTime) {
        this.departureTime = departureTime;
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
                LocalTime.ofSecondOfDay(departureTime/1000).format(DateTimeFormatter.ofPattern(DEPARTURE_TIME_FORMAT))
                        + '\'' +
                ", passengersOnBoard=" + passengersOnBoard +
                ", maxNumSeats=" + maxNumSeats +
                '}';
    }


}
