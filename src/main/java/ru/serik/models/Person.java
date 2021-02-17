package ru.serik.models;

import java.util.Objects;

public class Person {
    private int idPerson;
    private String surnamePerson;
    private String namePerson;
    private Integer agePerson;
    private String addressPerson;
    private String phonePerson;
    private Position position;
    // Поле для того чтобы сохранять значение ID профессии (через Thymeleaf)
    // из выпадающего списка (к БД вообще не относится).
    private transient Integer idPositionTransient; // Модификатор transient исключает поле из сериализации.

    //Конструктор по умолчанию
    public Person() {
    }

    //Параметризованный конструктор
    public Person(String surnamePerson, String namePerson, Integer agePerson, String addressPerson, String phonePerson) {
        this.surnamePerson = surnamePerson;
        this.namePerson = namePerson;
        this.agePerson = agePerson;
        this.addressPerson = addressPerson;
        this.phonePerson = phonePerson;
    }

    //Getters
    public int getIdPerson() {
        return idPerson;
    }

    public String getSurnamePerson() {
        return surnamePerson;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public Integer getAgePerson() {
        return agePerson;
    }

    public String getAddressPerson() {
        return addressPerson;
    }

    public String getPhonePerson() {
        return phonePerson;
    }

    public Position getPosition() {
        return position;
    }

    public Integer getIdPositionTransient() {
        return idPositionTransient;
    }


    //Setters
    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public void setSurnamePerson(String surnamePerson) {
        this.surnamePerson = surnamePerson;
    }
    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }
    public void setAgePerson(Integer agePerson) {
        this.agePerson = agePerson;
    }
    public void setAddressPerson(String addressPerson) {
        this.addressPerson = addressPerson;
    }
    public void setPhonePerson(String phonePerson) {
        this.phonePerson = phonePerson;
    }
    public void setIdPositionTransient(Integer idPositionTransient) {
        this.idPositionTransient = idPositionTransient;
    }

    //toString
    @Override
    public String toString() {
        return "Person{" +
                "idPerson=" + idPerson +
                ", surnamePerson='" + surnamePerson + '\'' +
                ", namePerson='" + namePerson + '\'' +
                ", agePerson=" + agePerson +
                ", addressPerson='" + addressPerson + '\'' +
                ", phonePerson='" + phonePerson + '\'' +
                ", position=" + position +
                '}';
    }

    //equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return idPerson == person.idPerson &&
                Objects.equals(surnamePerson, person.surnamePerson) &&
                Objects.equals(namePerson, person.namePerson) &&
                Objects.equals(agePerson, person.agePerson) &&
                Objects.equals(addressPerson, person.addressPerson) &&
                Objects.equals(phonePerson, person.phonePerson) &&
                Objects.equals(position, person.position);
    }

}
