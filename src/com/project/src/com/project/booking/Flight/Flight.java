package com.project.booking.Flight;

import com.project.booking.Passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

public class Flight {

    private String flightNumber;
    private String origin;
    private String destination;
    private List<Passenger> passengers;
    private int passengersOnBoard;
    private int maxNumSeats;

    public Flight(String flightNumber, String origin, String destination, int maxNumSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.passengers = new ArrayList();
        this.passengersOnBoard = 0;
        this.maxNumSeats = maxNumSeats;
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
                ", passengers=" + passengers +
                ", passengersOnBoard=" + passengersOnBoard +
                ", maxNumSeats=" + maxNumSeats +
                '}';
    }


}
