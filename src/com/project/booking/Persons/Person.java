package com.project.booking.Persons;

import com.project.booking.Constants.Sex;

import java.io.Serializable;
import java.util.Objects;

public abstract class Person implements Serializable {
    private String name;
    private String surname;
    private Long birthDate;
    private Sex sex;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public Person(String name, String surname, Long birthDate, Sex sex) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getBirthDate() == person.getBirthDate() &&
                getName().equals(person.getName()) &&
                getSurname().equals(person.getSurname()) &&
                getSex() == person.getSex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getBirthDate(), getSex());
    }

    @Override
    public String toString() {
        return "Persons{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", sex=" + sex +
                '}';
    }
}
