package com.example.services;

import com.example.dto.CarDto;
import com.example.entities.Car;
import com.example.entities.CarType;
import com.example.entities.User;
import com.example.exception.ValidationException;
import com.example.repository.CarRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserService userService;
    private final CarTypeService carTypeService;
    private final ColorService colorService;

    public Car save(CarDto carDto) {
        Car car = carRepository.save(mapCarDtoToCar(carDto));
        car.getUser().setPassword(null);
        return car;
    }

    public Car update(CarDto carDto) {
        Optional<Car> currentCar = carRepository.findById(carDto.getId());
        if (currentCar.isPresent()) {
            Car car = carRepository.save(mapCarDtoToCar(carDto));
            car.getUser().setPassword(null);
            return car;
        }
        throw new ValidationException("Can't update car! This car does't exist!");
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> getCarsByUserId(Long id) {
        return removeUserPassword(carRepository.findAllByUserId(id));
    }

    public void addCarToUser(Long userId, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("This car doesn't exist!"));
        User user = userService.findById(userId).orElseThrow(() -> new NoSuchElementException("Can;t find user by id " + userId));
        car.setUser(user);
        carRepository.save(car);
    }

    public List<Car> getAllCar() {
        return removeUserPassword(carRepository.findAll());
    }

    public Car mapCarDtoToCar(CarDto carDto) {
        Car car = new Car();
        car.setId(carDto.getId());
        car.setCarType(carTypeService.getById(carDto.getCarTypeId()));
        car.setColor(colorService.getById(carDto.getColorId()));
        car.setUser(userService.findById(carDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("Can;t find user by id " + carDto.getUserId())));
        car.setWeight(carDto.getWeight());
        car.setModel(carDto.getModel());
        return car;
    }

    public List<Car> removeUserPassword(List<Car> list) {
        for (Car car : list) {
            car.getUser().setPassword(null);
        }
        return list;
    }

}
