package com.example.services;

import com.example.entities.Color;
import com.example.repository.ColorRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public Optional<Color> getColorByName(String name) {
        return colorRepository.findByName(name);
    }

    public Color save(Color color) {
        return colorRepository.save(color);
    }

    public void delete(Long id) {
        colorRepository.deleteById(id);
    }

    public List<Color> getAll() {
        return colorRepository.findAll();
    }

    public Color getById(Long id) {
        return colorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find color by id" + id));
    }

}
