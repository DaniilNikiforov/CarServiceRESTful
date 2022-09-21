package com.example.intagretion;


import com.example.controllers.CarController;
import com.example.dto.CarDto;
import com.example.entities.*;
import com.example.repository.RoleRepository;
import com.example.services.CarService;
import com.example.services.CarTypeService;
import com.example.services.ColorService;
import com.example.services.UserService;
import com.google.gson.Gson;
import java.util.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class CarControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private RoleRepository roleRepository;

    private User userForRegister;
    private CarDto carDto;
    private Gson gson = new Gson();
    private static int count;

    private static User user;
    private static Color color;
    private static CarType carType;

    @Before
    public void init() {
        if (count == 0) {
            setUpModels();
            count++;
        }
        carDto = new CarDto();
        carDto.setModel("BMW M5");
        carDto.setWeight(1350);
        carDto.setColorId(color.getId());
        carDto.setCarTypeId(carType.getId());
        carDto.setUserId(user.getId());
    }

    private void setUpModels() {
        Optional<Role> roleOptional = roleRepository.findByName("USER");
        if (!roleOptional.isPresent()) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }

        userForRegister = new User();
        userForRegister.setUsername("steven");
        userForRegister.setPassword("123");
        this.user = userService.saveNewUser(userForRegister);

        Color color = new Color();
        color.setName("Black");
        this.color = colorService.save(color);

        CarType carType = new CarType();
        carType.setName("Sport");
        this.carType = carTypeService.save(carType);
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "USER")
    public void postRequestTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/car")
                .content(gson.toJson(carDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "USER")
    public void deleteRequestTest() throws Exception {
        Car savedCar = carService.save(carDto);
        mvc.perform(MockMvcRequestBuilders
                .delete("/car/{id}", savedCar.getId()))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", password = "123", roles = "USER")
    public void getByIdRequestTest() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("123");
        User savedUser = userService.saveNewUser(user);
        carDto.setUserId(savedUser.getId());
        carService.save(carDto);
        mvc.perform(MockMvcRequestBuilders
                .get("/cars/user/{userId}", user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "USER")
    public void putRequestTest() throws Exception {
        Car savedCar = carService.save(carDto);
        carDto.setId(savedCar.getId());
        carDto.setModel("Tesla Model X");
        mvc.perform(MockMvcRequestBuilders
                .put("/car")
                .content(gson.toJson(carDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Tesla Model X"));
    }

}
