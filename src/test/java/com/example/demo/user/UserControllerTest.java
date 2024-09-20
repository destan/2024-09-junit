package com.example.demo.user;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.User;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

/**
 * Test scenario:
 * 1. Get user with 1 id => 404
 * 2. Create new user and get its newId
 * 3. Get user with newId => 200
 */
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest extends AbstractIntegrationTest {

    public static final String USERNAME = "joe";
    public static final String ROLE = "USER";
    public static final String EMAIl = "joe@example.com";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private Long newUserId;

    private Long nonExistingUserId;

    @BeforeAll
    void createNonExistingUserId() {
        User saved = userRepository.save(new User("test@example.com", "testpassword", "USER", "test"));
        nonExistingUserId = saved.getId();
        userRepository.deleteById(nonExistingUserId);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Order(1)
    @DisplayName("Get user gives 404 for non-existent users")
    void getUser_NonExistentUser_Gives404() {

        assumeFalse(userRepository.existsById(nonExistingUserId));

        String url = "http://localhost:" + port + "/users/" + nonExistingUserId;

        System.out.println("Test 1 " + Thread.currentThread().getName() + "  " + this.hashCode());

        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Order(1)
    @DisplayName("Get user gives 404 for non-existent users")
    void getUser_NonExistentUser_Gives404_WithRestAssured() {
        String path = "/users/" + nonExistingUserId;

        System.out.println("Thread getUser_NonExistentUser_Gives404_WithRestAssured " + Thread.currentThread().getName() + "  " + this.hashCode());

        given().get(path).then().statusCode(404);
    }

    @Test
    @Order(2)
    @DisplayName("Create user saves the user with id to the db")
    void create_NewUser_201() {
        String path = "/users";

        Response response = given()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body("""
                        {
                            "username": "%s",
                            "password": "123",
                            "role": "%s",
                            "email": "%s"
                          }
                        """.formatted(USERNAME, ROLE, EMAIl))
                .post(path);

        response.then().statusCode(201);
        JsonPath jsonPath = response.body().jsonPath();

        newUserId = jsonPath.getLong("id");
        assertNotNull(newUserId);
        assertEquals(USERNAME, jsonPath.getString("username"));
        assertEquals(ROLE, jsonPath.getString("role"));
        assertEquals(EMAIl, jsonPath.getString("email"));

        String password = jsonPath.getString("password");
        assertFalse(password.isEmpty());
        assertNotEquals("123", password);
    }

    @Test
    @Order(3)
    @DisplayName("Get user with newly created user's id should give the new user")
    void getUser_NewlyCreatedUser_GiveNewlyCreatedUser() {
        String path = "/users/" + newUserId;

        Response response = given().get(path);
        response.then().statusCode(200);

        JsonPath jsonPath = response.body().jsonPath();

        assertEquals(USERNAME, jsonPath.getString("username"));
        assertEquals(ROLE, jsonPath.getString("role"));
        assertEquals(EMAIl, jsonPath.getString("email"));
    }
}