package com.project.booking.Passenger;

import java.util.Objects;

public class Passenger {
    private String name;
    private String surname;
    private long birthDate;
    private char sex;

    public Passenger(String name, String surname, long birthDate, char sex) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return birthDate == passenger.birthDate &&
                sex == passenger.sex &&
                Objects.equals(name, passenger.name) &&
                Objects.equals(surname, passenger.surname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, birthDate, sex);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", sex=" + sex +
                '}';
    }
}