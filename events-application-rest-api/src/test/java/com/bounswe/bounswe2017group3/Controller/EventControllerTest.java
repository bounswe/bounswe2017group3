package com.bounswe.bounswe2017group3.Controller;

import com.bounswe.bounswe2017group3.Application;
import com.bounswe.bounswe2017group3.Model.Event;
import com.bounswe.bounswe2017group3.Repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request
    .MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result
    .MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EventControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EventRepository repo;

    @InjectMocks
    private EventController eventController;

    Integer value = 19000101;
    int year = value / 10000;
    int month = (value % 10000) / 100;
    int day = value % 100;
    Date date = new GregorianCalendar(year, month, day).getTime();

    // Mocked test events
    private final Event e1 = new Event("Chess Tournament", "Chess Tournament in Bursa",
                                      "Bursa", date, false);
    private final Event e2 = new Event("Tennis Tournament", "Tennis Tournament in Ankara",
                                      "Ankara", date, true);
    private final List<Event> events = Arrays.asList(e1, e2);


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(eventController)
            .build();

        // Mock the method calls
        Mockito.when(repo.findAll()).thenReturn(events);
        Mockito.when(repo.findByName("Tennis Tournament")).thenReturn(e2);
    }

    /**
     * Tests whether <code>GET /event</code> endpoint returns all the events in a
     * proper format or not
     *
     * @throws Exception Throws an exception when the /event route doesn't exist.
     */
    @Test
    public void event() throws Exception {
        mockMvc.perform(get("/event"))
               .andExpect(status().isOk())
               .andExpect(
                   content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].name", is("Chess Tournament")))
               .andExpect(jsonPath("$[0].location", is("Bursa")))
               .andExpect(jsonPath("$[0].privacy", is(false)))
               .andExpect(jsonPath("$[1].name", is("Tennis Tournament")))
               .andExpect(jsonPath("$[1].location", is("Ankara")))
               .andExpect(jsonPath("$[1].privacy", is(true)));
                       
        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);
    }

    /**
     * Tests whether <code>GET /event?name</code> endpoint returns the
     * events with the given eventname in a proper format or not.
     *
     * @throws Exception Throws an exception when the /event?name route
     *                   doesn't exist.
     */
    @Test
    public void eventByEventname() throws Exception {
        mockMvc.perform(get("/event?name={eventname}", "Tennis Tournament"))
               .andExpect(status().isOk())
               .andExpect(
                   content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.name", is("Tennis Tournament")))
               .andExpect(jsonPath("$.location", is("Ankara")))
               .andExpect(jsonPath("$.privacy", is(true)));
               
        verify(repo, times(1)).findByName("Tennis Tournament");
        verifyNoMoreInteractions(repo);
    }

    /**
     * Returns the given object in its JSON string format.
     *
     * @param obj An object.
     * @return String representation of the <code>obj</code>.
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}