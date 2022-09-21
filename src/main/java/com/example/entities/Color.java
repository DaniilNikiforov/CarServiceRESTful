package com.example.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "colors")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Color {

	@Id
	@Column(name = "color_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
}
