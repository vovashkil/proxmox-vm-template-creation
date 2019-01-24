package com.project.booking.Flight;

import com.project.booking.DAO.CollectionFlightDAO;
import com.project.booking.DAO.FlightDAO;

import java.util.List;

public class FlightService {

    private FlightDAO flightDao = new CollectionFlightDAO();

    public FlightDAO getFlightDao() {
        return flightDao;
    }

    public List<Flight> getAllFlights() {
        return flightDao.getAllFlights();
    }

    public void displayAllFlights() {

        flightDao.getAllFlights().forEach(System.out::println);

    }

    public void saveData(String filePath) {

        flightDao.saveData(filePath);

    }

    public void readData(String filePath) {

        flightDao.readData(filePath);

    }

    public void deleteFlightByIndex(int index) {

        flightDao.deleteFlight(index);

    }

    public int count() {

        return flightDao.getAllFlights().size();

    }

    public Flight getFlightById(int index) {

        if (index >= 0 && index < flightDao.getAllFlights().size()) {

            return flightDao.getAllFlights().get(index);

        } else {

            return null;

        }

    }


}
