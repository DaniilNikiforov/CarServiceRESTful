package com.example.repository;

import com.example.entities.Car;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    void deleteById(Long id);

    Optional<Car> findByUserId(Long userId);

    List<Car> findAllByUserId(Long userId);
}
