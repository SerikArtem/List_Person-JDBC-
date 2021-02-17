package ru.serik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serik.dao.PersonDAO;
import ru.serik.models.Person;

import java.sql.SQLException;
import java.util.List;

            // @Component - помечает класс в качестве кандидата для создания Spring бина.
@Service    // Производная от @Component, только применяется для пометки сервиса.
public class PersonServiceImpl implements PersonService {
    private final PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl (PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public List<Person> getAllPerson() throws Exception {
        return personDAO.getAllPerson();
    }


    @Override
    public Person getPersonById(int idPerson) throws Exception {
        return personDAO.getPersonById(idPerson);
    }

    @Override
    public void savePerson(Person personSave) throws Exception {
        personDAO.savePerson(personSave);
    }

    @Override
    public void updatePerson(int idPerson, Person personUpdate) throws Exception {
        personDAO.updatePerson(idPerson, personUpdate);
    }

    @Override
    public void deletePerson(Person personDelete) throws Exception {
        personDAO.deletePerson(personDelete);
    }
}
