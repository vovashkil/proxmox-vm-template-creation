package com.project.booking.DAO;

import java.util.List;

public interface DAO<T> {

    void save(T item);
    T get(int index);
    List<T> getAll();
    boolean remove(int index);
    boolean remove(T item);

    void saveData(String filePath);
    void readData(String filePath);
    void loadData(List<T> list);

}
