package com.cc.authapi.controllers;

import com.cc.authapi.application.KeyService;
import com.cc.authapi.application.UserService;
import com.cc.authapi.domain.ApiResponse;
import com.cc.authapi.domain.Key;
import com.cc.authapi.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        ApiResponse<User> response = userService.login(user);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("register")
    public ResponseEntity<Object> RegisterUser(@RequestBody User user) {
        ApiResponse<User> response = userService.register(user);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/gen")
    public ResponseEntity<?> generate(@RequestParam String time) {
        ApiResponse<Key> response = keyService.generateKey(time);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("users")
    public ResponseEntity<Object> Users() {
        ApiResponse<List<User>> response = userService.getUsers();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}
