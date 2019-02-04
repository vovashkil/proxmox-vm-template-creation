package com.project.booking.Controllers;

import com.project.booking.Booking.Flight;
import com.project.booking.Services.FlightService;

import java.util.List;

public class FlightController {

    private FlightService flightService = new FlightService();

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

}
