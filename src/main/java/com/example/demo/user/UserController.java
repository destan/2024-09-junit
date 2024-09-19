package com.example.demo.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }
}
