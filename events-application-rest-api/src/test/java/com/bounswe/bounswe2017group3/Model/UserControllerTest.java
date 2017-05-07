package com.bounswe.bounswe2017group3.Model;
import com.bounswe.bounswe2017group3.Application;
import com.bounswe.bounswe2017group3.Model.User;
import com.bounswe.bounswe2017group3.Controller.UserController;
import com.bounswe.bounswe2017group3.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserControllerTest {
    MockMvc mockMvc;
    @Mock
    UserRepository repo;
    @InjectMocks
    UserController userController;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }
    /**
     * Tests whether GET /user endpoint works or not
     * @throws Exception 
     */
    @Test
    public void test_get_all_success() throws Exception {
        List<User> users = Arrays.asList(
                new User("gokce","Gökçe Uludoğan", "gokce@gmail.com","123123"),
                new User("samed","Samed Düzçay","smddzcy@gmail.com","123456"));
        Mockito.when(repo.findAll()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect( jsonPath("$[0].username", is("gokce")))
                .andExpect(jsonPath("$[0].fullname",is("Gökçe Uludoğan")))
                .andExpect(jsonPath("$[0].email",is("gokce@gmail.com")))                      
                .andExpect( jsonPath("$[1].username", is("samed")))
                .andExpect(jsonPath("$[1].fullname",is("Samed Düzçay")))
                .andExpect(jsonPath("$[1].email",is("smddzcy@gmail.com")));
        
        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}