package com.project.internal_request_system.controller;
import com.project.internal_request_system.dto.UserRegistrationDto;
import com.project.internal_request_system.entity.User;
import com.project.internal_request_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto dto) {
        try {
            User createdUser = userService.registerUser(dto);
            return ResponseEntity.ok("User registered successfully! ID: " + createdUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}