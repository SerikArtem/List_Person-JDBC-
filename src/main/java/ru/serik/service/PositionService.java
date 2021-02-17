package ru.serik.service;

import ru.serik.models.Person;
import ru.serik.models.Position;

import java.sql.SQLException;
import java.util.List;

public interface PositionService {
    List<Position> getAllPosition() throws Exception;                         // Все люди из таблицы positions
    Position getPositionById(int idPosition) throws Exception;                // Конкретный человек по ID из таблицы positions
    void savePosition(Position positionSave) throws Exception;                // Сохранить человека в таблицу positions
    void updatePosition(Position positionUpdate) throws Exception;            // Обновляет человека в таблице positions
    void deletePosition(Position positionDelete) throws Exception;            // Удаление человека из таблицы positions по ID
    List<Person> showPeopleWithThisPosition(int idPosition) throws Exception; // Возвращает всех людей с конкретной должностью
}
