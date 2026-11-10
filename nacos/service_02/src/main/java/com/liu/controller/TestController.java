package com.liu.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("testApi")
    public String testApi(){
        return "ces2";
    }
}
