package com.example.repository;

import com.example.entities.CarType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarTypeRepository extends JpaRepository<CarType, Long> {

    Optional<CarType> findByName(String name);

    void deleteById(Long id);
}
