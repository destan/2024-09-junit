package com.example.demo.user;

import com.example.demo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("users")
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.get(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(user);
    }

    @PostMapping
    ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.persist(user));
    }

    @DeleteMapping("{id}")
    ResponseEntity<User> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
