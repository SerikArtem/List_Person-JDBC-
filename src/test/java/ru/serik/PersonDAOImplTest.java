package ru.serik;

import org.junit.Assert;
import org.junit.Test;
import ru.serik.dao.PersonDAOImpl;
import ru.serik.dao.PositionDAOImpl;
import ru.serik.models.Person;
import ru.serik.models.Position;

import java.util.List;
import java.util.Random;


public class PersonDAOImplTest {
    private final PersonDAOImpl personDAOImpl = new PersonDAOImpl();
    private final PositionDAOImpl positionDAOImpl = new PositionDAOImpl();

    @Test
    public void methodGetAllPersonShouldReturnNotNullObject() throws Exception {
        Assert.assertNotNull(personDAOImpl.getAllPerson());
    }

    @Test
    public void testCRUDPeople() throws Exception {
        // Фотримуем тестовый объект Person и рандомно выбираем для него профессию
        Person testObjectPerson = new Person("surnameTest", "nameTest", 21,
                                        "addressTest", "+79991234567");
        Random random = new Random();
        List<Position> listPositions = positionDAOImpl.getAllPosition();
        Position position = listPositions.get(listPositions.get(random.nextInt(listPositions.size() -1)).getIdPosition());
        testObjectPerson.setPosition(position); // Присвоение профессии человеку

        // 1) Добавление тестового объекта в таблицу people
        personDAOImpl.savePerson(testObjectPerson);

        // 2) Запрашиваем только что добавленный элемент в таблицы и сравниваем с исходным
        Person objectPersonFromDB = personDAOImpl.getPersonById(testObjectPerson.getIdPerson());
        compareObjectsPerson(objectPersonFromDB, testObjectPerson);

        // 3) Обновляем добавденный элемент в таблице people
        personDAOImpl.updatePerson(testObjectPerson.getIdPerson(), testObjectPerson);

        // 4) Снова запрашиваем обновлённый элемент и сравниваем его с исходным
        objectPersonFromDB = personDAOImpl.getPersonById(testObjectPerson.getIdPerson());
        compareObjectsPerson(objectPersonFromDB, testObjectPerson);

        // 5) Удаляем добавленный элемент
        personDAOImpl.deletePerson(testObjectPerson);
        objectPersonFromDB = personDAOImpl.getPersonById(testObjectPerson.getIdPerson());

        // 6) Снова заправшиваем его по ID из таблицы people и проверяем на null
        Assert.assertNull(objectPersonFromDB);
    }

    private void compareObjectsPerson(Person objectPersonFromDB, Person testObjectPerson) {
        Assert.assertNotNull("Object 'objectPersonFromDB' is NULL!", objectPersonFromDB);
        Assert.assertTrue("Objects 'objectPersonFromDB' and 'testObjectPerson' are not equal!", objectPersonFromDB.equals(testObjectPerson));
    }
}
