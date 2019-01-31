package com.project.booking.Flight;

import com.project.booking.DAO.CollectionFlightDAO;
import com.project.booking.DAO.DAO;

import java.util.List;

public class FlightService {

    private DAO<Flight> flightDao = new CollectionFlightDAO();

    public DAO<Flight> getFlightDao() {
        return flightDao;
    }

    public List<Flight> getAllFlights() {
        return flightDao.getAll();
    }

    public void displayAllFlights() {

        flightDao.getAll().forEach(System.out::println);

    }

    public void saveFlight(Flight flight) {

        flightDao.save(flight);

    }

    public void saveData(String filePath) {

        flightDao.saveData(filePath);

    }

    public void readData(String filePath) {

        flightDao.readData(filePath);

    }

    public void deleteFlightByIndex(int index) {

        flightDao.remove(index);

    }

    public void deleteFlightByObject(Flight flight) {

        flightDao.remove(flight);

    }

    public int count() {

        return flightDao.getAll().size();

    }

    public Flight getFlightById(int index) {

        if (index >= 0 && index < flightDao.getAll().size()) {

            return flightDao.getAll().get(index);

        } else {

            return null;

        }

    }


}
