package com.project.booking.Persons;

import com.project.booking.Constants.Sex;

public abstract class Person {
    private String name;
    private String surname;
    private long birthDate;
    private Sex sex;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public Person(String name, String surname, long birthDate, Sex sex) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", sex=" + sex +
                '}';
    }
}
