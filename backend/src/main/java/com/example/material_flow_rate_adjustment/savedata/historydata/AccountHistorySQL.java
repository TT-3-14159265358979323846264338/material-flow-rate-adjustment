package com.example.material_flow_rate_adjustment.savedata.historydata;

import java.time.LocalDateTime;

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
@Table(name = "account_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistorySQL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED AUTO_INCREMENT", updatable = false)
	@Setter(AccessLevel.NONE)
	private Integer id;
	
	@Column(name = "target_id", columnDefinition = "INT UNSIGNED", nullable = false, updatable = false)
	private Integer targetId;
	
	@Column(name = "old_login_user", length = 20, updatable = false)
	private String oldLoginUser;
	
	@Column(name = "new_login_user", length = 20, updatable = false)
	private String newLoginUser;
	
	@Column(name = "old_displayed_user", length = 10, updatable = false)
	private String oldDisplayedUser;
	
	@Column(name = "new_displayed_user", length = 10, updatable = false)
	private String newDisplayedUser;
	
	@Column(name = "old_role", length = 10, updatable = false)
	private String oldRole;
	
	@Column(name = "new_role", length = 10, updatable = false)
	private String newRole;
	
	@Column(length = 10, nullable = false, updatable = false)
	private String action;
	
	@Column(name = "action_id", columnDefinition = "INT UNSIGNED", nullable = false, updatable = false)
	private Integer actionId;
	
	@Column(name = "action_user", length = 10, nullable = false, updatable = false)
	private String actionUser;
	
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
	private LocalDateTime date;
}