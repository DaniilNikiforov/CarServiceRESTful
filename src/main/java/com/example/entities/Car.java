package com.example.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "cars")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Car {

	@Id
	@Column(name = "car_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "weight")
	@Min(value = 100)
	@NotNull
	private int weight;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color", nullable = false,
    			foreignKey = @ForeignKey(name = "fk_cars_colors_color"))
	@NotNull
  	private Color color;

	@Column(name = "model", nullable = false)
	private String model;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "car_type", nullable = false,
				foreignKey = @ForeignKey(name = "fk_cars_car_types_car_type"))
	@NotNull
	private CarType carType;
	
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false,
				foreignKey = @ForeignKey(name = "fk_cars_users_owner_id"))
	private User user;
	
}
