package com.example.demo.user;

import com.example.demo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) //FIXME
@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    @DisplayName("Get user gives 404 for non existent user")
    void getUser_nonExistentUser_404() {

        when(userService.get(99L)).thenReturn(Optional.empty());

        var e = assertThrows(ResponseStatusException.class, () -> userController.getUser(99L));
        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }

    @Test
    @DisplayName("Get user gives 200 for existent user")
    void getUser_existentUser_200() {

        when(userService.get(any())).thenReturn(Optional.of(new User()));

        ResponseEntity<User> responseEntity = userController.getUser(new Random().nextLong());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}