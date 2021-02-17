package ru.serik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.serik.models.Person;
import ru.serik.models.Position;
import ru.serik.service.PositionServiceImpl;

import java.sql.SQLException;
import java.util.List;


@Controller
@RequestMapping("/positions")
public class PositionsController {
    private final PositionServiceImpl positionServiceImpl;

    @Autowired
    public PositionsController(PositionServiceImpl positionServiceImpl) {
        this.positionServiceImpl = positionServiceImpl;
    }

//--------------------- READ ---------------------//

    //Этот метод возвращает все должности людей из таблицы positions.
    @GetMapping()
    public String getAllPositionController(Model model) throws SQLException, Exception {
        //Получим все профессии из DAO и передадим на отображение в представление.
        model.addAttribute("positions", positionServiceImpl.getAllPosition());
        return "positions/list";
    }

    //Этот метод возвращает одну должность из таблицы positions по её ID.
    @GetMapping("/{idPosition}")
    public String getPositionByIdController(@PathVariable("idPosition") int idPosition, Model model) throws SQLException, Exception {
        //Получим одну должность по её ID из DAO и передадим на отображение в представление.
        Position position = positionServiceImpl.getPositionById(idPosition);
        model.addAttribute("positionByID", position);
        return "positions/show";
    }

//--------------------- CREATE ---------------------//

    //Это метод выводит на экран форму для создания новой должности.
    @GetMapping("/new")
    public String formSavePositionController(Model model) throws SQLException, Exception {
        model.addAttribute("position", new Position());
        return "positions/new";
    }

    //Этот метод сохраняет новую должность (из формы) в таблицу positions.
    @PostMapping()
    public String savePositionController(@ModelAttribute("position") Position position) throws SQLException, Exception {
        positionServiceImpl.savePosition(position);
        return "redirect:/positions";
    }

//--------------------- UPDATE ---------------------//

    //Это метод выводит форму для редактирования данных конкретной должности по её ID.
    @GetMapping("/{idPosition}/edit")
    public String formUpdatePersonController(@PathVariable("idPosition") int idPosition, Model model) throws SQLException, Exception {
        model.addAttribute("positionUpdate", positionServiceImpl.getPositionById(idPosition));
        return "positions/edit";
    }

    //Этот метод сохраняет данные конкретной должности в таблицу positions, которая была отредактированна.
    @PatchMapping("/{idPosition}")
    public String updatePerson(@ModelAttribute("positionUpdate") Position positionUpdate) throws SQLException, Exception {
        positionServiceImpl.updatePosition(positionUpdate);
        return "redirect:/positions";
    }

//--------------------- DELETE ---------------------//

    //Этот метод удаляет конкретную должность по её ID из таблицы positions.
    @DeleteMapping("{idPosition}")
    public String deletePerson(@ModelAttribute("positionByID") Position position) {
        try {
            positionServiceImpl.deletePosition(position);
        }catch (SQLException e) {
            return "redirect:/error";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/positions";
    }

//--------------------- Additional methods ---------------------//

    // Этот метод показывает всех людей с конкретной должностью.
    @GetMapping("/{idPosition}/list-people")
    public String showPeopleWithThisPositionController(@PathVariable("idPosition") int idPosition, Model model) throws Exception {
        Position position = positionServiceImpl.getPositionById(idPosition);
        List<Person> listPersonFromPosition = positionServiceImpl.showPeopleWithThisPosition(idPosition);
        if(listPersonFromPosition.isEmpty()) {
            model.addAttribute("flagFromShowPeopleWithThisPosition", "isEmpty");
        } else {
            model.addAttribute("flagFromShowPeopleWithThisPosition", "notIsEmpty");
        }
        model.addAttribute("positionForListPeople", position);
        model.addAttribute("listPersonFromPosition", listPersonFromPosition);
        return "positions/listPeople";
    }
}
