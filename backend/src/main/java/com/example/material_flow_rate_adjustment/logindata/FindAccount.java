package com.example.material_flow_rate_adjustment.logindata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.savedata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.AccountSQL;

@Service
public class FindAccount implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountSQL account = accountRepository.findByUser(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + "が登録されていません"));
		return User.withUsername(account.getUser())
				.password(account.getPassword())
				.roles(account.getRole())
				.build();
	}
}