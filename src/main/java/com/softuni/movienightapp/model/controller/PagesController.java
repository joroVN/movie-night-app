package com.softuni.movienightapp.model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/users/admins")
    public String admins() {
        return "admins";
    }

    @GetMapping("/users/moderators")
    public String moderators() {
        return "moderators";
    }


}
