package com.project.booking.Flight;

import org.junit.Assert;
import org.junit.Test;

public class FlightServiceTest {

    public void getAllFlightsReturnsEmptyListWhenNoFlights() {

        FlightService flightsService = new FlightService();

        int result = flightsService.getAllFlights().size();

        Assert.assertEquals(result, 0);
    }

    @Test
    public void getAllFlightsReturnsNonEmptyListWhenFlightsAdded() {

        FlightService flightsService = new FlightService();


        flightsService.saveFlight(new Flight("flight1","orig1", "dest1", 200, 0, 0));
        flightsService.saveFlight(new Flight("flight2","orig1", "dest2", 200, 0, 0));
        flightsService.saveFlight(new Flight("flight3","orig1", "dest3", 200, 0, 0));

        int result = flightsService.getAllFlights().size();

        Assert.assertEquals(result, 3);

    }

    @Test
    public void getFlightDao() {
    }

    @Test
    public void getAllFlights() {
    }

    @Test
    public void displayAllFlights() {
    }

    @Test
    public void saveData() {
    }

    @Test
    public void readData() {
    }

    @Test
    public void deleteFlightByIndex() {
    }

    @Test
    public void count() {
    }

    @Test
    public void getFlightById() {
    }
}