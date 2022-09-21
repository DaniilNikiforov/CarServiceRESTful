package com.example.services;

import com.example.dto.CarDto;
import com.example.entities.*;
import com.example.repository.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private RoleRepository roleRepository;

    private List<Car> list;

    @Before
    public void init() {
        list = new ArrayList<>();

        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("123");

        Car car = new Car();
        car.setId(1L);
        car.setUser(user);
        list.add(car);
    }

    @Test
    public void removeUserPasswordTest() {
        for (Car car : carService.removeUserPassword(list)) {
            assertNull(car.getUser().getPassword());
        }
    }

    @Test
    public void mapCarDtoToCarTest(){
        Optional<Role> roleOptional = roleRepository.findByName("USER");
        if(!roleOptional.isPresent()){
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }

        User user = new User();
        user.setUsername("ulitka");
        user.setPassword("123");
        user = userService.saveNewUser(user);

        Color color = new Color();
        color.setName("Black");
        color = colorService.save(color);

        CarType carType = new CarType();
        carType.setName("Sport");
        carType = carTypeService.save(carType);

        CarDto carDto = new CarDto();
        carDto.setModel("BMW M5");
        carDto.setWeight(1350);
        carDto.setColorId(color.getId());
        carDto.setCarTypeId(carType.getId());
        carDto.setUserId(user.getId());

        Car car = carService.mapCarDtoToCar(carDto);
        assertNotNull(car);
        assertEquals(carDto.getModel(), car.getModel());
        assertEquals(user.getUsername(), car.getUser().getUsername());
        assertEquals(color, car.getColor());
        assertEquals(carType, car.getCarType());
        assertEquals(carDto.getWeight(), car.getWeight());
    }

}
