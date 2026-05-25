package com.example.material_flow_rate_adjustment.savedata.maindata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSQL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED AUTO_INCREMENT", updatable = false)
	@Setter(AccessLevel.NONE)
	private Integer id;
	
	@Column(name = "login_user", unique = true, length = 20, nullable = false)
	private String loginUser;
	
	@Column(name = "displayed_user", unique = true, length = 10, nullable = false)
	private String displayedUser;
	
	@Column(length = 255, nullable = false)
	private String password;
	
	@Column(length = 10, nullable = false)
	private String role;
}