package com.project.booking;

public class Flight {

    private String flightNumber;
    private String origin;
    private String destination;
    private Passenger[] passengers;
    private int passengersOnBoard;
    private int numSeats;

    public Flight(String flightNumber, String origin, String destination, int numSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.passengers = new Passenger[numSeats];
        this.passengersOnBoard = 0;
        this.numSeats = numSeats;
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

    public boolean addPassenger(Passenger p) {

        if (passengersOnBoard >= numSeats) {

            return false;

        }

        passengers[passengersOnBoard++] = p;
        return true;

    }

    public void printInfo() {
        System.out.println("Flight Number: " + flightNumber);
        System.out.println("Origin: " + origin);
        System.out.println("Destination: " + destination);
        System.out.println("Number of passengers on board: " + passengersOnBoard);
        if (passengersOnBoard > 0) {
            System.out.println("Passenger List:");
        }

        for (int i = 0; i < passengersOnBoard; i++) {
            System.out.println("\tPassenger " + (i + 1) + ": " + passengers[i].getInfo());
        }
    }


}
