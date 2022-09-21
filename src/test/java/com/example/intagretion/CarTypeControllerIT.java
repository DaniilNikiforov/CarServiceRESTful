package com.example.intagretion;


import com.example.entities.CarType;
import com.example.entities.Color;
import com.example.services.CarTypeService;
import com.example.services.ColorService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarTypeControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarTypeService carTypeService;

    private CarType currentCarType;
    private CarType carType;
    private Gson gson = new Gson();

    @Before
    public void init() {
        carType = new CarType();
        carType.setName("Jeep");
        currentCarType = new CarType();
        currentCarType.setName(carType.getName());
        currentCarType.setId(1L);
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void postRequestTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/car_type")
                .content(gson.toJson(carType))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void deleteRequestTest() throws Exception {
        CarType savedCarType = carTypeService.save(carType);
        mvc.perform(MockMvcRequestBuilders
                .delete("/car_type/{id}", savedCarType.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void getAllRequestTest() throws Exception {
        carTypeService.save(carType);
        mvc.perform(MockMvcRequestBuilders
                .get("/car_types")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }


}
