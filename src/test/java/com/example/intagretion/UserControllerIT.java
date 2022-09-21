package com.example.intagretion;


import com.example.entities.Role;
import com.example.entities.User;
import com.example.repository.RoleRepository;
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
public class UserControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    private User userForRegister;
    private Gson gson = new Gson();
    private static int count;

    @Before
    public void init() {
        userForRegister = new User();
        userForRegister.setUsername("username");
        userForRegister.setPassword("123");

        Optional<Role> roleOptional = roleRepository.findByName("USER");
        if(!roleOptional.isPresent()){
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }
    }

    @Test
    public void postRequestTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .content(gson.toJson(userForRegister))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userForRegister.getUsername()));
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void deleteRequestTest() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("123");
        User savedUser = userService.saveNewUser(user);
        mvc.perform(MockMvcRequestBuilders
                .delete("/user/{id}", savedUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "USER")
    public void deleteRequestTestByUserRole() throws Exception {
        User user = new User();
        user.setUsername("user4");
        user.setPassword("123");
        User savedUser = userService.saveNewUser(user);
        mvc.perform(MockMvcRequestBuilders
                .delete("/user/{id}", savedUser.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void getAllRequestTest() throws Exception {
        User user = new User();
        user.setUsername("user2");
        user.setPassword("123");
        userService.saveNewUser(user);
        mvc.perform(MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void getByIdRequestTest() throws Exception {
        User user = new User();
        user.setUsername("user3");
        user.setPassword("123");
        User savedUser = userService.saveNewUser(user);
        mvc.perform(MockMvcRequestBuilders
                .get("/user/{id}", savedUser.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void putRequestTest() throws Exception {
        User user = new User();
        user.setUsername("user5");
        user.setName("Ban");
        user.setPassword("123");
        User savedUser = userService.saveNewUser(user);
        savedUser.setName("Mark");
        savedUser.setPassword("123");
        mvc.perform(MockMvcRequestBuilders
                .put("/user")
                .content(gson.toJson(savedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mark"));
    }

}
