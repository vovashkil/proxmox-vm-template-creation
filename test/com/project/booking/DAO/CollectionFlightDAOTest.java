package com.project.booking.DAO;

import com.project.booking.Flight.Flight;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CollectionFlightDAOTest {

    public void getAllFlightsReturnsEmptyListWhenNoFlights() {

        CollectionFlightDAO flightsCollection = new CollectionFlightDAO();

        List<Flight> result = flightsCollection.getAllFlights();

        Assert.assertEquals(result.size(), 0);

    }

    @Test
    public void getAllFlightsReturnsNonEmptyListWhenFlightsAdded() {

        CollectionFlightDAO flightsCollection = new CollectionFlightDAO();

        flightsCollection.saveFlight(new Flight("flight1","orig1", "dest1", 200, 0, 0));
        flightsCollection.saveFlight(new Flight("flight2","orig1", "dest2", 200, 0, 0));
        flightsCollection.saveFlight(new Flight("flight3","orig1", "dest3", 200, 0, 0));

        List<Flight> result = flightsCollection.getAllFlights();

        Assert.assertEquals(result.size(), 3);

    }

    @Test
    public void getAllFlights() {
    }

    @Test
    public void getFlightByIndex() {
    }

    @Test
    public void saveFlight() {
    }

    @Test
    public void deleteFlight() {
    }

    @Test
    public void deleteFlight1() {
    }

    @Test
    public void saveData() {
    }

    @Test
    public void readData() {
    }

    @Test
    public void loadData() {
    }
}