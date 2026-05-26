package com.example.material_flow_rate_adjustment.logindata;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

import jakarta.annotation.PostConstruct;

@Component
public class TokenProvider {
	@Autowired
	private AccountRepository accountRepository;
	
	@Value("${jwt.password}")
	private String password;
	
	@Value("${jwt.period}")
	private Duration period;
	
	private Key key;
	
	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(password.getBytes());
	}
	
	public Token createToken(Authentication authentication) {
		Date nowDate = new Date();
		AccountSQL account = accountRepository.findByLoginUser(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("アカウントはあるのにIDの取り込みに失敗しました。"));
		String token = Jwts.builder()
				.subject(account.getId().toString())
				.issuedAt(nowDate)
				.expiration(new Date(nowDate.getTime() + period.toMillis()))
				.signWith(key)
				.compact();
		return new Token(token, account.getRole());
    }
	
	record Token(String token, String role) {};
	
	public String getUser(String token) {
		return getJws(token).getPayload().getSubject();
	}
	
	public String getRole(String token) {
		int id = Integer.parseInt(getUser(token));
		AccountSQL account = accountRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("アカウントはあるのにIDの取り込みに失敗しました。"));
		return account.getRole();
	}
	
	public boolean validateToken(String token) {
		try {
			getJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
	
	Jws<Claims> getJws(String token) {
		return Jwts.parser()
				.verifyWith((javax.crypto.SecretKey) key)
				.build()
				.parseSignedClaims(token);
	}
}