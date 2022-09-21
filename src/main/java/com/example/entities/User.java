package com.example.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	//@Pattern(regexp = "[^0-9]*")
	private String name;
	
	@Column(name = "surname")
	//@Pattern(regexp = "[^0-9]*")
	private String surname;
	
	@Column(name = "username")
	//@Pattern(regexp = "[^0-9]*")
	@NotNull
	private String username;

	@Column(name = "password_hash")
	//@Pattern(regexp = "^\\$2[ayb]\\$.{56}$")
    @NotNull
	private String password;

  	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_type", nullable = false,
    			foreignKey = @ForeignKey(name = "fk_users_role_account_type"))
	@NotNull
  	private Role role;
  	
}
