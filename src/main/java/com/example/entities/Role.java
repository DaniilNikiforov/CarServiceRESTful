package com.example.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	public String getAuthority() {
		return name;
	}
}
