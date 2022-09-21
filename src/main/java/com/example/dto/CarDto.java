package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long id;
    private int weight;
    private Long colorId;
    private Long carTypeId;
    private Long userId;
    private String model;
}
