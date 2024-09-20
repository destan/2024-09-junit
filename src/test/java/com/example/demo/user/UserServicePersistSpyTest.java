package com.example.demo.user;

import com.example.demo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class UserServicePersistSpyTest {

    @Spy
    private EmailVerifierApi emailVerifierApi;

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private final User user = new User("deneme@example.com", "123", "user", "deneme");

    private final String nonHashedPassword = user.getPassword();

    @BeforeEach
    void setUp() {
        userService = spy(new UserService(userRepository, emailVerifierApi));
    }

    @Test
    @DisplayName("Persist method calls verify email api")
    void persist_newUser_callsVerifyEmail() {
        userService.persist(user);

        verify(emailVerifierApi, times(1)).checkEmail(user.getEmail());
    }

    @Test
    @DisplayName("Persis method calls hash password")
    void persist_newUser_callsHashPassword() {
        userService.persist(user);

        verify(userService, times(1)).hashPassword(nonHashedPassword);
    }
}