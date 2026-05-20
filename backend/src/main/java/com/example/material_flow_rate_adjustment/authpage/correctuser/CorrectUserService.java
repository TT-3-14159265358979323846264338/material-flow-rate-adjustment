package com.example.material_flow_rate_adjustment.authpage.correctuser;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
import com.example.material_flow_rate_adjustment.savedata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectUserService {
	private final AccountRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public List<Account> getUser() {
		return repository.findAll().stream().map(this::createAccount).toList();
	}
	
	Account createAccount (AccountSQL account) {
		return new Account(account.getId(), account.getUser(), account.getRole());
	}
	
	record Account(int id, String username, String role) {};
	
	@Transactional
	public void correctUser(AdminCorrectOwnData data, String loginUser) {
		AccountSQL account = repository.findById(Integer.parseInt(loginUser))
				.orElseThrow(() -> new NotFindException("ユーザーが見つかりません。"));
		if(StringUtils.hasLength(data.newName())) {
			account.setUser(data.newName());
		}
		if(StringUtils.hasLength(data.oldPass()) && StringUtils.hasLength(data.newPass())) {
			if(passwordEncoder.matches(data.oldPass(), account.getPassword())) {
				account.setPassword(passwordEncoder.encode(data.newPass()));
			}else {
				throw new DataBaseException("以前のパスワードが一致しないため、処理を停止しました。");
			}
		}
	}
}