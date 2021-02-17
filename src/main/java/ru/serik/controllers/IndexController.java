package ru.serik.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    public static final Logger LOGGER = Logger.getLogger(IndexController.class);

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }
}
