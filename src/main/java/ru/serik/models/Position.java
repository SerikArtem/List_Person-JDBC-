package ru.serik.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private int idPosition;
    private String namePosition;
    private List<Person> people;

    //Конструктор по умолчанию
    public Position() {
    }

    //Параметризованный конструктор
    public Position(String namePosition) {
        this.namePosition = namePosition;
        people = new ArrayList<>();
    }

    //Getters

    public String getNamePosition() {
        return namePosition;
    }

    public List<Person> getPeople() {
        return people;
    }

    public int getIdPosition() {
        return idPosition;
    }

    //Setters
    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }
    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }
    public void setPeople(List<Person> people) {
        this.people = people;
    }


    //toString
    @Override
    public String toString() {
        return "Position{" +
                "idPosition=" + idPosition +
                ", namePosition='" + namePosition + '\'' +
                '}';
    }

    //equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return idPosition == position.idPosition &&
                Objects.equals(namePosition, position.namePosition);
    }

    //Additional methods
    public void addPersonInList(Person person) {
        people.add(person);
    }

}
