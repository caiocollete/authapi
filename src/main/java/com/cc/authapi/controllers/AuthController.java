package com.cc.authapi.controllers;

import com.cc.authapi.application.KeyService;
import com.cc.authapi.application.UserService;
import com.cc.authapi.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class AuthController {

    private final UserService userService;
    private final KeyService keyService;

    public AuthController(UserService userService, KeyService keyService) {
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
        return keyService.genToken(time);
    }

    @GetMapping("users")
    public ResponseEntity<Object> Users() {
        return userService.getUsers();
    }
}
