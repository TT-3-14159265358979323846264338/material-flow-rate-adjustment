package com.example.material_flow_rate_adjustment.authpage;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilityService {
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final MaterialRepository materialRepository;
	
	public AccountSQL getAccountSQL(String loginUser) {
		return getAccountSQL(Integer.parseInt(loginUser));
	}
	
	public AccountSQL getAccountSQL(int userId) {
		AccountSQL account = accountRepository.findById(userId)
			.orElseThrow(() -> new NotFindException("ユーザーが見つかりません。"));
		return account;
	}
	
	public void setPassword(AccountSQL targetAccount, String oldPass, String newPass) {
		if(StringUtils.hasLength(oldPass) && StringUtils.hasLength(newPass)) {
			if(passwordEncoder.matches(oldPass, targetAccount.getPassword())) {
				targetAccount.setPassword(passwordEncoder.encode(newPass));
			}else {
				throw new DataBaseException("以前のパスワードが一致しないため、処理を停止しました。");
			}
		}
	}
	
	public MaterialSQL getMaterialSQL(int id) {
		return materialRepository.findById(id).orElseThrow(() -> new NotFindException("製品が見つかりません。"));
	}
}