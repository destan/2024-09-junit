package com.example.demo.user;

import com.example.demo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailVerifierApi emailVerifierApi;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, emailVerifierApi);
    }

    @Test
    @DisplayName("Persisted user should have an id")
    void nonNullId() {
        User user = new User("deneme@example.com", "123", "user", "deneme");

        when(userRepository.save(user)).thenReturn(new User(1L, "deneme@example.com", "123", "user", "deneme"));

        assertNull(user.getId());
        User persistedUser = userService.persist(user);
        assertNotNull(persistedUser.getId());
    }

    @Test
    @DisplayName("Persisted user fields should remain the same")
    void fieldsRemainSame() {
        String mail = "deneme@example.com";
        String role = "user";
        String username = "deneme";
        User user = new User(mail, "123", role, username);

        when(userRepository.save(user)).thenReturn(user);

        User persistedUser = userService.persist(user);

        //assertEquals("deneme@example.com", persistedUser.getEmail());
        //assertEquals("user2", persistedUser.getRole());
        //assertEquals("deneme2", persistedUser.getUsername());

        //Executable e1 = () -> assertEquals("deneme@example.com", persistedUser.getEmail());
        //Executable e2 = () -> assertEquals("user2", persistedUser.getRole());
        //Executable e3 = () -> assertEquals("deneme2", persistedUser.getUsername());
        //
        //assertAll("fields stay the same", e1, e2, e3);

        assertAll("fields stay the same", () -> assertEquals(mail, persistedUser.getEmail()), () -> assertEquals(role, persistedUser.getRole()), () -> assertEquals(username, persistedUser.getUsername()));
    }

    @Test
    @DisplayName("Persisted user's password should be hashed")
    void passwordHashing() {
        String password = "123";
        User user = new User("deneme@example.com", password, "user", "deneme");

        when(userRepository.save(user)).thenReturn(user);

        User persistedUser = userService.persist(user);

        assertNotNull(persistedUser.getPassword());
        assertFalse(persistedUser.getPassword().isBlank());
        assertNotEquals(password, persistedUser.getPassword());
    }

    @Test
    @DisplayName("Hashing function should hash as expected")
    void correctHashing() {
        String password = "123";
        String expectedHashedPassword = "hashedPassword:123";

        User user = new User("deneme@example.com", password, "user", "deneme");

        when(userRepository.save(user)).thenReturn(user);

        User persistedUser = userService.persist(user);

        assertEquals(expectedHashedPassword, persistedUser.getPassword());
    }

    @Test
    @DisplayName("persist method throws when user is null")
    void nullHandling() {
        var e = assertThrows(IllegalArgumentException.class, () -> userService.persist(null));
        assertEquals("user is null", e.getMessage());
    }

}
