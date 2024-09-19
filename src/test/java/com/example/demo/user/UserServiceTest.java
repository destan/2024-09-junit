package com.example.demo.user;

import com.example.demo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService = new UserService();

    @Test
    @DisplayName("Persisted user should have an id")
    void nonNullId() {
        User user = new User("deneme@example.com", "123", "user", "deneme");
        User persistedUser = userService.persist(user);
        assertNotNull(persistedUser.getId());
    }

    @Test
    @DisplayName("Persisted user fields should remain the same")
    void fieldsRemainSame() {
        User user = new User("deneme@example.com", "123", "user", "deneme");
        User persistedUser = userService.persist(user);

        //assertEquals("deneme@example.com", persistedUser.getEmail());
        //assertEquals("user2", persistedUser.getRole());
        //assertEquals("deneme2", persistedUser.getUsername());

        //Executable e1 = () -> assertEquals("deneme@example.com", persistedUser.getEmail());
        //Executable e2 = () -> assertEquals("user2", persistedUser.getRole());
        //Executable e3 = () -> assertEquals("deneme2", persistedUser.getUsername());
        //
        //assertAll("fields stay the same", e1, e2, e3);

        assertAll("fields stay the same",
                    () -> assertEquals("deneme@example.com", persistedUser.getEmail()),
                    () -> assertEquals("user2", persistedUser.getRole()),
                    () -> assertEquals("deneme2", persistedUser.getUsername())
                );
    }

    @Test
    @DisplayName("Persisted user's password should be hashed")
    void passwordHashing() {
        User user = new User("deneme@example.com", "123", "user", "deneme");
        User persistedUser = userService.persist(user);

        assertNotEquals("123", persistedUser.getPassword());
    }

    @Test
    @DisplayName("persist method throws when user is null")
    void nullHandling() {
        var e = assertThrows(IllegalArgumentException.class, () -> userService.persist(null));
        assertEquals("user is null", e.getMessage());
    }
  
}