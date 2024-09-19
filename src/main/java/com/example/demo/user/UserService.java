package com.example.demo.user;

import com.example.demo.User;
import org.springframework.stereotype.Service;

@Service
class UserService {

    User persist(User user) {

        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }

        //TODO some db operation
        user.setId(99L);
        user.setPassword(hashPassword(user.getPassword()));
        return user;
    }

    private String hashPassword(String password) {
        return "hashedPassword:" + password;
    }

}
