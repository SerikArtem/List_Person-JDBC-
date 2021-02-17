package ru.serik.service;

import ru.serik.models.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonService {
    List<Person> getAllPerson() throws Exception;                             // Все люди из таблицы people
    Person getPersonById(int idPerson) throws Exception;                      // Конкретный человек по ID из таблицы people
    void savePerson(Person personSave) throws Exception;                      // Сохранить человека в таблицу people
    void updatePerson(int idPerson, Person personUpdate) throws Exception;    // Обновляет человека в таблице people
    void deletePerson(Person personDelete) throws Exception;                  // Удаление человека из таблицы people по ID
}
