package com.example.material_flow_rate_adjustment.authpage.commonpage.correctpassword;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectPasswordService {
	private final UtilityService utility;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public void userCorrectPassword(NewPass data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		setPassword(account, data.oldPass(), data.newPass());
	}
	
	void setPassword(AccountSQL targetAccount, String oldPass, String newPass) {
		if(StringUtils.hasLength(oldPass) && StringUtils.hasLength(newPass)) {
			if(passwordEncoder.matches(oldPass, targetAccount.getPassword())) {
				targetAccount.setPassword(passwordEncoder.encode(newPass));
			}else {
				throw new DataBaseException("以前のパスワードが一致しないため、処理を停止しました。");
			}
		}
	}
}