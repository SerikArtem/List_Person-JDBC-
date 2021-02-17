package ru.serik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serik.dao.PositionDAO;
import ru.serik.models.Person;
import ru.serik.models.Position;

import java.sql.SQLException;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private final PositionDAO positionDAO;

    @Autowired
    public PositionServiceImpl(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    @Override
    public List<Position> getAllPosition() throws Exception {
        return positionDAO.getAllPosition();
    }

    @Override
    public Position getPositionById(int idPosition) throws Exception {
        return positionDAO.getPositionById(idPosition);
    }

    @Override
    public void savePosition(Position positionSave) throws Exception {
        positionDAO.savePosition(positionSave);
    }

    @Override
    public void updatePosition(Position positionUpdate) throws Exception {
        positionDAO.updatePosition(positionUpdate);
    }

    @Override
    public void deletePosition(Position positionDelete) throws Exception {
        positionDAO.deletePosition(positionDelete);
    }

    @Override
    public List<Person> showPeopleWithThisPosition(int idPosition) throws Exception {
        return positionDAO.showPeopleWithThisPosition(idPosition);
    }
}
