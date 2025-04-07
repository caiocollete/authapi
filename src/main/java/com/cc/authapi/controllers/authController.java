package com.cc.authapi.controllers;

import com.cc.authapi.application.KeyService;
import com.cc.authapi.application.UserService;
import com.cc.authapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("v1")
public class authController {

    private final UserService userService;
    private final KeyService keyService;

    public authController(UserService userService, KeyService keyService) {
        this.userService = userService;
        this.keyService = keyService;
    }

    @PutMapping("login")
    public ResponseEntity<Object> LoginUser(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("register")
    public ResponseEntity<Object> RegisterUser(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("gen")
    public ResponseEntity<Object> GenerateToken(@RequestParam("time") String time) {
        /* Formatos de tempo (duracao da key)
        * 1d - 1 dias
        * lf - LifeTime
        */
        return keyService.genToken(time);
    }
}
