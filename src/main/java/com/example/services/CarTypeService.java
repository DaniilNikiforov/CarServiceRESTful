package com.example.services;

import com.example.entities.CarType;
import com.example.repository.CarTypeRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarTypeService {

    @Autowired
    private CarTypeRepository carTypeRepository;

    public Optional<CarType> getCarTypeByName(String name) {
        return carTypeRepository.findByName(name);
    }

    public CarType save(CarType carType) {
        return carTypeRepository.save(carType);
    }

    public void delete(Long id) {
        carTypeRepository.deleteById(id);
    }

    public List<CarType> getAll() {
        return carTypeRepository.findAll();
    }

    public CarType getById(Long id) {
        return carTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find car type by id " + id));
    }

}
