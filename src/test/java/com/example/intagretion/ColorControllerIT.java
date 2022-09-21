package com.example.intagretion;


import com.example.entities.Color;
import com.example.services.ColorService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ColorControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ColorService colorService;

    private Color color;
    private Color mockedColor;
    private Gson gson = new Gson();

    @Before
    public void init() {
        mockedColor = new Color();
        mockedColor.setName("Blue");
        color = new Color();
        color.setName(mockedColor.getName());
        color.setId(1L);
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void postRequestTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/color")
                .content(gson.toJson(mockedColor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // методи для перевірки респонса
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockedColor.getName()));
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void deleteRequestTest() throws Exception {
        Color savedColor = colorService.save(mockedColor);
        mvc.perform(MockMvcRequestBuilders
                .delete("/color/{id}", savedColor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void getAllRequestTest() throws Exception {
        colorService.save(mockedColor);
        mvc.perform(MockMvcRequestBuilders
                .get("/colors")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }
}
