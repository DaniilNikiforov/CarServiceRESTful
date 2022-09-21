package com.example.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/")
    public String getMainPage() {
        return "all ok";
    }
}