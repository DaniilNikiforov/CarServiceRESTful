package com.example.controllers;

import com.example.entities.CarType;
import com.example.repository.CarTypeRepository;
import com.example.services.CarTypeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CarTypeController {
    private final CarTypeService carTypeService;

    @PostMapping("/car_type")
    public CarType save(@RequestBody CarType carType) {
        return carTypeService.save(carType);
    }

    @DeleteMapping("/car_type/{id}")
    public void delete(@PathVariable Long id) {
        carTypeService.delete(id);
    }

    @GetMapping("/car_types")
    public List<CarType> getAll() {
        return carTypeService.getAll();
    }
}
