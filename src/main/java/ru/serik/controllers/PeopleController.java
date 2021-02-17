package ru.serik.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.serik.models.Person;
import ru.serik.models.Position;
import ru.serik.service.PersonServiceImpl;
import ru.serik.service.PositionServiceImpl;

import java.util.ArrayList;
import java.util.List;

            //@Component - помечает класс в качестве кандидата для создания Spring бина.
@Controller // Производная от @Component, только применяется для пометки контроллера.
@RequestMapping("/people")
public class PeopleController {
    private final PersonServiceImpl personServiceImpl;
    private final PositionServiceImpl positionServiceImpl;
    public static final Logger LOGGER = Logger.getLogger(PeopleController.class);

    @Autowired
    public PeopleController(PersonServiceImpl personServiceImpl, PositionServiceImpl positionServiceImpl) {
        this.personServiceImpl = personServiceImpl;
        this.positionServiceImpl = positionServiceImpl;
    }

//--------------------- READ ---------------------//

    //Этот метод возвращает всех людей из таблицы people.
    @GetMapping()
    public String getAllPerson(Model model) {
        //Получим всех людей из DAO и передадим на отображение в представление.
        try {
            List<Person> peopleList = personServiceImpl.getAllPerson();
            model.addAttribute("people", peopleList);
            LOGGER.info("Object 'peopleList' successfully created and added to the model.");
        } catch (Exception e) {
            LOGGER.error("Error creating the 'peopleList' object.", e);
        }
        return "people/list";
    }

    //Этот метод возвращает одного человека из таблицы people по его ID.
    @GetMapping("/{idPerson}")
    public String getPersonById(@PathVariable("idPerson") int idPerson, Model model) {
        //Получим одного человека по его id из DAO и передадим на отображение в представление.
        try {
            Person person = personServiceImpl.getPersonById(idPerson);
            model.addAttribute("person", person);
            Position position = person.getPosition();
            model.addAttribute("namePosition", position.getNamePosition());
            LOGGER.info("Objects 'person' and 'position' successfully created and added to the models.");
        } catch (Exception e) {
            LOGGER.error("Error creating the 'person' object.", e);
        }
        return "people/show";
    }

//--------------------- CREATE ---------------------//

    //Это метод выводит на экран форму для создания нового человека.
    @GetMapping("/new")
    public String formSavePerson(Model model) {
        try {
            model.addAttribute("person", new Person());
            model.addAttribute("listObjectsPositions", positionServiceImpl.getAllPosition());
            LOGGER.info("Models 'person' and 'listObjectsPositions' successfully created.");
        } catch (Exception e) {
            LOGGER.error("Error created 'person' and 'listObjectsPositions' models.", e);
        }
        return "people/new";
    }

    //Этот метод сохраняет нового человека (из формы) в таблицу people.
    @PostMapping()
    public String savePerson(@ModelAttribute("person") Person person) {
        //Добавляем, в почти уже готовый person, его position и отправляем на сохранение в таблицу people.
        try {
            Position position = positionServiceImpl.getPositionById(person.getIdPositionTransient());
            person.setPosition(position); // Добавление профессии человеку.
            personServiceImpl.savePerson(person);
            LOGGER.info("The new person was successfully saved in the DB.");
        } catch (Exception e) {
            LOGGER.error("Error creating or saving new people in the DB.", e);
        }

        return "redirect:/people";
    }

//--------------------- UPDATE ---------------------//

    //Это метод выводит форму для редактирования данных конкретного человека по его ID.
    @GetMapping("/{idPerson}/edit")
    public String formUpdatePerson(@PathVariable("idPerson") int idPerson, Model model) {
        try {
            Person personUpdate = personServiceImpl.getPersonById(idPerson);
            model.addAttribute("personUpdate", personUpdate);
            LOGGER.info("Object 'personUpdate' successfully created and added to the model.");
            //Определяем какая профессия (position) у человека (people) и переносим эту профессию на первое место списка,
            // для того чтобы эта профессия сразу отображалась в сплывающем списке select формы редактирования человека.
            List<Position> listObjectsPosition = positionServiceImpl.getAllPosition();
            //Position position = listObjectsPosition.remove(personUpdate.getPosition().getIdPosition() - 1);
            //listObjectsPosition.add(0, position);
            model.addAttribute("listObjectsPositions", listObjectsPosition);
            LOGGER.info("Object 'listObjectsPosition' successfully created and added to the model.");
        } catch (Exception e) {
            LOGGER.error("Error creating the form for updating a person.");
        }
        return "people/edit";
    }

    //Этот метод сохраняет данные конкретного человека в таблицу people, которые были отредактированы.
    @PatchMapping("/{idPerson}")
    public String updatePerson(@ModelAttribute("personUpdate") Person personUpdate, @PathVariable("idPerson") int idPerson) {
        //Добавляем, в почти уже готовый person, его position и отправляем на обновление в таблицу people.
        try {
            Position position = positionServiceImpl.getPositionById(personUpdate.getIdPositionTransient());
            personUpdate.setPosition(position); // Добавляем профессию человеку.
            personServiceImpl.updatePerson(idPerson, personUpdate);
            LOGGER.info("The person was successfully updated and saved in the DB.");
        } catch (Exception e) {
            LOGGER.error("Error updating the person.");
        }
        return "redirect:/people";
    }

//--------------------- DELETE ---------------------//

    //Этот метод удаляет конкретного человека по ID из таблицы people.
    @DeleteMapping("{idPerson}")
    public String deletePerson(@ModelAttribute("person") Person person) {
        try {
            personServiceImpl.deletePerson(person);
            LOGGER.info("The person was successfully deleted from the DB.");
        } catch (Exception e) {
            LOGGER.error("Error deleting the person.");
        }
        return "redirect:/people";
    }
}
