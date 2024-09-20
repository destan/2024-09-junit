package com.example.demo.user;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerDeleteTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeAll
    void setUp() {
        // Create a new user, get its id
        user = userRepository.save(new User("test@example.com", "testpassword", "USER", "test"));

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Order(1)
    @DisplayName("Delete user operation returns 204")
    void deleteUser_ExistingUser_204() {
        assumeTrue(userRepository.existsById(user.getId()));

        given().delete("/users/" + user.getId()).then().statusCode(204);
    }

    @Test
    @Order(2)
    @DisplayName("Deleted user doesn't exist in db")
    void deletedUserMissingInDb() {
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    @Order(2)
    @DisplayName("Deleted user doesn't exist in db")
    void getUser_deletedUser_404() {
        given().get("/users/" + user.getId()).then().statusCode(404);
    }

}