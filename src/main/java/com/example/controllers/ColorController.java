package com.example.controllers;

import com.example.entities.Color;
import com.example.services.ColorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @PostMapping("/color")
    public Color save(@RequestBody Color color) {
        return colorService.save(color);
    }

    @DeleteMapping("/color/{id}")
    public void delete(@PathVariable Long id) {
        colorService.delete(id);
    }

    @GetMapping("/colors")
    public List<Color> getAll() {
        return colorService.getAll();
    }
}
