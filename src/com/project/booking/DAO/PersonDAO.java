package com.project.booking.DAO;

import com.project.booking.Booking.Person;
import java.util.List;

public interface PersonDAO<T extends Person> {
    List<T> getAll();
    T getByIndex(int index);
    void save(T person);
    boolean remove(int index);
    boolean remove(T person);
    void saveData(String filePath);
    void readData(String filePath);
    void loadData(List<T> persons);
}
