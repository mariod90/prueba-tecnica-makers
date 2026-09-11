package com.makers.gestionprestamos.controller;

import com.makers.gestionprestamos.entity.Lending;
import com.makers.gestionprestamos.service.impl.UserService;
import com.makers.gestionprestamos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserControlller {

    @Autowired
    UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MASTER','ADMIN','USER')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}/lendings")
    @PreAuthorize("hasAnyRole('MASTER','ADMIN','USER')")
    public ResponseEntity<List<Lending>> getLendingsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getLendingsByUserId(id));
    }
}
