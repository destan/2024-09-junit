package com.example.demo.user;

import com.example.demo.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Persists a given user to database.
     *
     * @param user filled user object
     * @return persisted user object
     */
    User persist(User user) {

        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        user.setPassword(hashPassword(user.getPassword()));

        return userRepository.save(user);
    }

    private String hashPassword(String password) {
        return "hashedPassword:" + password;
        //return "";
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
