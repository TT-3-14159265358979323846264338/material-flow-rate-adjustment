package com.example.material_flow_rate_adjustment.savedata.historydata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "account_history", indexes = {
		@Index(name = "idx_account_history_date", columnList = "date")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistorySQL extends BaseHistorySQL{
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
}