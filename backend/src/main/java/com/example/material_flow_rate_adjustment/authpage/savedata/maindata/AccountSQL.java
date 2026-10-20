package com.example.material_flow_rate_adjustment.savedata.maindata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "account", indexes = {
		@Index(name = "idx_account_role", columnList = "role, has_deleted")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountSQL extends BaseSQL{
	@Column(name = "login_user", unique = true, length = 20, nullable = false)
	private String loginUser;
	
	@Column(name = "displayed_user", unique = true, length = 10, nullable = false)
	private String displayedUser;
	
	@Column(length = 255, nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private AccountRole role;
}