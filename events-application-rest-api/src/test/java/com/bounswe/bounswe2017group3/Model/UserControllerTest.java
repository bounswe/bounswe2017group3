package com.bounswe.bounswe2017group3.Model;
import com.bounswe.bounswe2017group3.Application;
import com.bounswe.bounswe2017group3.Model.User;
import com.bounswe.bounswe2017group3.Controller.UserController;
import com.bounswe.bounswe2017group3.Repository.UserRepository;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.when;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import java.util.Arrays;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
 
    @Autowired   
    UserRepository repository;
    User usr1;
    User usr2;
    User usr3;
    @Value("${local.server.port}")   
    int port;
 
    @Before
    public void setUp() {
        
        usr1 = new User("gokce","Gökçe Uludoğan", "gokce@gmail.com","123123");
        usr2 = new User("samed","Samed Düzçay","smddzcy@gmail.com","123456");
        usr3 = new User("esra","Esra Aydemir","esra-aydemir@gmail.com","222333");        
        repository.deleteAll();
        repository.save(Arrays.asList(usr1,usr2,usr3));
        RestAssuredMockMvc.standaloneSetup(new UserController(repository));
        RestAssured.port = port;
    }
 
    @Test
    public void canGetByUsername() {
        String username = usr1.getUsername();
        given().
                param("username","gokce").
        when().
                get("/user").
        then().
                statusCode(HttpStatus.SC_OK).
                body("fullname", Matchers.is("Gökçe Uludoğan")).
                body("email", Matchers.is("gokce@gmail.com"));
    }
    
    @Test
    public void canGetAll() {
        when().
                get("/user").
        then().
                statusCode(HttpStatus.SC_OK).
                body("username", Matchers.hasItems("gokce", "samed", "esra"));
    }
    
    
    @Test
    public void canDeleteById() {
        long usr3Id = usr3.getId();
        when()
                .delete("/user/{id}", usr3Id).
        then().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }
}