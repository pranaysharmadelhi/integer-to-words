package com.sonatype.assignment.integertowords.unit.controllers;

import com.sonatype.assignment.integertowords.IntegerToWordsApplication;
import com.sonatype.assignment.integertowords.controllers.LanguageController;
import com.sonatype.assignment.integertowords.services.language.LanguageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegerToWordsApplication.class })
@WebAppConfiguration
public class LanguageControllerTest {

    @InjectMocks
    LanguageController controller;

    @Mock
    LanguageService languageServiceMock;

    private String myWord = "some word";

    @Autowired
    protected ConfigurableWebApplicationContext context;

    protected MockMvc mockMvc;


    @Before
    public void setUp() {
        initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();



        when(languageServiceMock.getWordForInteger(any())).thenReturn(myWord);
    }

    @Test
    public void givenValidInteger_whenGetWord_thenStatus200()
            throws Exception {


        mockMvc.perform(get("/api/integertoword/2147483647")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wordInEnglish").value(myWord))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/integertoword/-2147483647")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wordInEnglish").value(myWord))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/integertoword/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wordInEnglish").value(myWord))
                .andExpect(status().isOk());

    }

    @Test
    public void givenMinInteger_whenGetWord_thenStatus400()
            throws Exception {


        mockMvc.perform(get("/api/integertoword/2147483648")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value("Invalid number or out of range"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void givenMaxInteger_whenGetWord_thenStatus400()
            throws Exception {


        mockMvc.perform(get("/api/integertoword/-2147483649")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value("Invalid number or out of range"))
                .andExpect(status().isBadRequest());
    }
    }
