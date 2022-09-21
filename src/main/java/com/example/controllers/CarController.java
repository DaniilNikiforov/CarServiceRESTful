package com.example.controllers;

import com.example.dto.CarDto;
import com.example.entities.Car;
import com.example.services.CarService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/car")
    public Car save(@RequestBody CarDto carDto) {
        return carService.save(carDto);
    }

    @PutMapping("/car")
    public Car update(@RequestBody CarDto carDto) {
        return carService.update(carDto);
    }

    @DeleteMapping("/car/{id}")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }

    @GetMapping("/cars/user/{userId}")
    public List<Car> getByUserId(@PathVariable Long userId) {
        return carService.getCarsByUserId(userId);
    }

    @GetMapping("/add/user/{userId}/to/car/{carId}")
    public void getByUserId(@PathVariable Long userId, @PathVariable Long carId) {
        carService.addCarToUser(userId, carId);
    }

    @GetMapping("/cars")
    public List<Car> getAll() {
        return carService.getAllCar();
    }
}
