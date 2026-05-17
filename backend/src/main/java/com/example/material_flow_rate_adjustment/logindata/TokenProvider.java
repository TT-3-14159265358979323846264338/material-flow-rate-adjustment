package com.example.material_flow_rate_adjustment.logindata;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.material_flow_rate_adjustment.savedata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.AccountSQL;

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
	
	public String createToken(Authentication authentication) {
		Date nowDate = new Date();
		AccountSQL account = accountRepository.findByUser(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("アカウントはあるのに権限の取り込みに失敗しました。"));
		return Jwts.builder()
				.subject(authentication.getName())
				.claim("auth", account.getRole())
				.issuedAt(nowDate)
				.expiration(new Date(nowDate.getTime() + period.toMillis()))
				.signWith(key)
				.compact();
    }
	
	public String getUser(String token) {
		Claims claims = Jwts.parser()
				.verifyWith((javax.crypto.SecretKey) key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((javax.crypto.SecretKey) key).build().parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}