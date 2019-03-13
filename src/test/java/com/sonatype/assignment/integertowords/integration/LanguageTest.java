package com.sonatype.assignment.integertowords.integration;

import com.sonatype.assignment.integertowords.IntegerToWordsApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegerToWordsApplication.class })
@WebAppConfiguration
public class LanguageTest {


    @Autowired
    protected ConfigurableWebApplicationContext context;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    public void givenInteger_whenGetWords_thenReturnsWords()
            throws Exception {

        mockMvc.perform(get("/api/integertoword/5001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Five thousand and one"))
                .andReturn();

        mockMvc.perform(get("/api/integertoword/5020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Five thousand and twenty"))
                .andReturn();

        mockMvc.perform(get("/api/integertoword/2147483647"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven"))
                .andReturn();

        mockMvc.perform(get("/api/integertoword/-2147483647"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Negative two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven"))
                .andReturn();

        mockMvc.perform(get("/api/integertoword/5237"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Five thousand two hundred and thirty seven"))
                .andReturn();

        mockMvc.perform(get("/api/integertoword/85"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Eighty five"))
                .andReturn();

        mockMvc.perform(get("/api/integertoword/13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Thirteen"))
                .andReturn();

        mockMvc.perform(get("/api/integertoword/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordInEnglish").value("Zero"))
                .andReturn();

    }



}