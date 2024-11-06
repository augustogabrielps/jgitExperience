package com.jgitexperience.JGit.Experience.controller;

import com.jgitexperience.JGit.Experience.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {

        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String sayHello() {

        return helloService.getHelloMessage();
    }
}