package com.project.booking.DAO;

import com.project.booking.Flight.Flight;

import java.util.List;

public interface FlightDAO {

    List<Flight> getAllFlights();
    Flight getFlightByIndex(int index);
    void saveFlight(Flight flight);
    boolean deleteFlight(int index);
    boolean deleteFlight(Flight flight);
    void saveData(String filePath);
    void readData(String filePath);
    void loadData(List<Flight> flightList);

}
