package ru.serik.dao;

import org.springframework.stereotype.Repository;
import ru.serik.models.Person;
import ru.serik.models.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PositionDAOImpl implements PositionDAO {

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

    // Метод, который возвращает все профессии из таблицы positions.
    @Override
    public List<Position> getAllPosition() {
        List<Position> listPosition = new ArrayList<>();
        //Объект, который содержит в себе SQL запрос к БД
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSetForPositions = statement.executeQuery("SELECT * FROM positions");

            while (resultSetForPositions.next()) {
                Position position = new Position();

                position.setIdPosition(resultSetForPositions.getInt("id_position"));
                position.setNamePosition(resultSetForPositions.getString("name_position"));

                listPosition.add(position);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listPosition;
    }

    // Метод, который возвращает конкретную профессию по ID из таблицы positions.
    @Override
    public Position getPositionById(int idPosition) {
        Position position = new Position();
        PreparedStatement preparedStatementForPosition = null;

        try {
            preparedStatementForPosition =
                    connection.prepareStatement("SELECT * FROM positions WHERE id_position=?");
            preparedStatementForPosition.setInt(1, idPosition);

            ResultSet resultSetForPosition = preparedStatementForPosition.executeQuery();

            resultSetForPosition.next();
            position.setIdPosition(resultSetForPosition.getInt("id_position"));
            position.setNamePosition(resultSetForPosition.getString("name_position"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatementForPosition != null) {
                    preparedStatementForPosition.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return position;
    }

    // Метод, который сохраняет профессию в таблице positions.
    @Override
    public void savePosition(Position positionSave) {
        PreparedStatement preparedStatementForPosition = null;

        try {
            preparedStatementForPosition =
                    connection.prepareStatement("INSERT INTO positions(name_position) VALUES (?)");
            preparedStatementForPosition.setString(1, positionSave.getNamePosition());

            preparedStatementForPosition.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Метод, который добавляет в таблицу positions уже отредактированую профессию.
    @Override
    public void updatePosition(Position positionUpdate) {
        PreparedStatement preparedStatementForPosition = null;

        try {
            preparedStatementForPosition =
                    connection.prepareStatement("UPDATE positions SET name_position=? WHERE id_position=?");
            preparedStatementForPosition.setString(1, positionUpdate.getNamePosition());
            preparedStatementForPosition.setInt(2, positionUpdate.getIdPosition());

            preparedStatementForPosition.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(preparedStatementForPosition != null) {
                    preparedStatementForPosition.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // Метод, который удаляет профессию из таблицы positions по её ID.
    @Override
    public void deletePosition(Position positionDelete) throws SQLException {
        PreparedStatement preparedStatementForPosition = null;

        try {
            preparedStatementForPosition = connection.prepareStatement("DELETE FROM positions WHERE id_position=?");
            preparedStatementForPosition.setInt(1, positionDelete.getIdPosition());

            preparedStatementForPosition.executeUpdate();
        } catch (SQLException throwables) {
            throw throwables;
        }finally {
            try {
                if(preparedStatementForPosition != null) {
                    preparedStatementForPosition.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // Метод, который возвращает всех людей с конкретной должностью
    @Override
    public List<Person> showPeopleWithThisPosition(int idPosition) {
        List<Person> listPerson = new ArrayList<>();
        PreparedStatement preparedStatementForPeople = null;
        PreparedStatement preparedStatementForPositions = null;

        try {
            preparedStatementForPeople =
                    connection.prepareStatement("SELECT * FROM people WHERE id_position=?;");
            preparedStatementForPeople.setInt(1, idPosition);

            ResultSet resultSetForPeople = preparedStatementForPeople.executeQuery();

            while (resultSetForPeople.next()) {
                Person person = new Person();
                Position position = new Position();

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

                listPerson.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(preparedStatementForPeople != null) {
                    preparedStatementForPeople.close();
                }
                if(preparedStatementForPositions != null) {
                    preparedStatementForPositions.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listPerson;
    }
}
