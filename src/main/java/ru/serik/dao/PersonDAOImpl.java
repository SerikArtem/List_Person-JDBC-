package ru.serik.dao;

import org.springframework.stereotype.Repository;
import ru.serik.models.Person;
import ru.serik.models.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

            // @Component - помечает класс в качестве кандидата для создания Spring бина.
@Repository // Производная от @Component, только применяется для пометки класса, работающего с БД.
public class PersonDAOImpl implements PersonDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/people_db_jdbc";
    private static final String USERNAME = "artem";
    private static final String PASSWORD = "159sql753";

    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Метод, который будет возвращать всех людей из таблицы people.
    @Override
    public List<Person> getAllPerson() {
        List<Person> listPeople = new ArrayList<>();
        //Объект, который содержит в себе SQL запрос к БД
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSetForPeople = statement.executeQuery("SELECT * FROM people");

            while (resultSetForPeople.next()) {
                Person person = new Person();
                Position position = new Position();

                person.setIdPerson(resultSetForPeople.getInt("id_person"));
                person.setSurnamePerson(resultSetForPeople.getString("surname_person"));
                person.setNamePerson(resultSetForPeople.getString("name_person"));
                person.setAgePerson(resultSetForPeople.getInt("age_person"));
                person.setAddressPerson(resultSetForPeople.getString("address_person"));
                person.setPhonePerson(resultSetForPeople.getString("phone_person"));

                ResultSet resultSetForPosition = statement.executeQuery
                        ("SELECT * FROM positions WHERE id_position = " + resultSetForPeople.getInt("id_position"));
                resultSetForPosition.next();
                position.setIdPosition(resultSetForPosition.getInt("id_position"));
                position.setNamePosition(resultSetForPosition.getString("name_position"));

                person.setPosition(position);
                listPeople.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listPeople;
    }

    // Метод, который будет возвращать конкретного человека по ID из таблицы people.
    @Override
    public Person getPersonById(int idPerson) {
        Person person = new Person();
        Position position = new Position();
        PreparedStatement preparedStatementForPeople = null;
        PreparedStatement preparedStatementForPositions = null;

        try {
            preparedStatementForPeople = connection.prepareStatement("SELECT * FROM people WHERE id_person=?");
            preparedStatementForPeople.setInt(1, idPerson);
            ResultSet resultSetForPeople = preparedStatementForPeople.executeQuery();
            resultSetForPeople.next();
            person.setIdPerson(resultSetForPeople.getInt("id_person"));
            person.setSurnamePerson(resultSetForPeople.getString("surname_person"));
            person.setNamePerson(resultSetForPeople.getString("name_person"));
            person.setAgePerson(resultSetForPeople.getInt("age_person"));
            person.setAddressPerson(resultSetForPeople.getString("address_person"));
            person.setPhonePerson(resultSetForPeople.getString("phone_person"));

            preparedStatementForPositions = connection.prepareStatement("SELECT * FROM positions WHERE id_position=?");
            preparedStatementForPositions.setInt(1, resultSetForPeople.getInt("id_position"));
            ResultSet resultSetForPosition = preparedStatementForPositions.executeQuery();
            resultSetForPosition.next();
            position.setIdPosition(resultSetForPosition.getInt("id_position"));
            position.setNamePosition(resultSetForPosition.getString("name_position"));
            person.setPosition(position);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatementForPeople != null) {
                    preparedStatementForPeople.close();
                }
                if(preparedStatementForPositions != null) {
                    preparedStatementForPositions.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return person;
    }

    // Метод, который будет сохранять человека в таблице people.
    @Override
    public void savePerson(Person personSave) {
        PreparedStatement preparedStatementForPeople = null;

        try {
            preparedStatementForPeople = connection.prepareStatement
                    ("INSERT INTO people(surname_person, name_person, age_person, ID_position, address_person, phone_person) VALUES(?,?,?,?,?,?)");

            preparedStatementForPeople.setString(1, personSave.getSurnamePerson());
            preparedStatementForPeople.setString(2, personSave.getNamePerson());
            preparedStatementForPeople.setInt(3, personSave.getAgePerson());
            preparedStatementForPeople.setInt(4, personSave.getPosition().getIdPosition());
            preparedStatementForPeople.setString(5, personSave.getAddressPerson());
            preparedStatementForPeople.setString(6, personSave.getPhonePerson());

            preparedStatementForPeople.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatementForPeople != null) {
                    preparedStatementForPeople.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // Метод, который будет добавлять в таблицу people уже отредактированного человека.
    @Override
    public void updatePerson(int idPerson, Person personUpdate) {
        PreparedStatement preparedStatementForPeople = null;

        try {
            preparedStatementForPeople = connection.prepareStatement
                    ("UPDATE people SET surname_person=?, name_person=?, age_person=?, ID_position=?, address_person=?, phone_person=? WHERE id_person=?");
            preparedStatementForPeople.setString(1, personUpdate.getSurnamePerson());
            preparedStatementForPeople.setString(2, personUpdate.getNamePerson());
            preparedStatementForPeople.setInt(3, personUpdate.getAgePerson());
            preparedStatementForPeople.setInt(4, personUpdate.getPosition().getIdPosition());
            preparedStatementForPeople.setString(5, personUpdate.getAddressPerson());
            preparedStatementForPeople.setString(6, personUpdate.getPhonePerson());
            preparedStatementForPeople.setInt(7, idPerson);

            preparedStatementForPeople.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(preparedStatementForPeople != null) {
                    preparedStatementForPeople.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // Метод, который будет удалять человека из таблицы people по его ID.
    @Override
    public void deletePerson(Person personDelete) {
        PreparedStatement preparedStatementForPeople = null;

        try {
            preparedStatementForPeople = connection.prepareStatement("DELETE FROM people WHERE id_person=?");
            preparedStatementForPeople.setInt(1, personDelete.getIdPerson());

            preparedStatementForPeople.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
