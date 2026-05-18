package com.example.material_flow_rate_adjustment.logindata;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {
	private final AuthenticationManager authenticationManager;
	private final TokenProvider tokenProvider;
	
	@PostMapping("/api/login")
	public ResponseEntity<?> authenticateUser(@RequestBody Account account) {
		UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(account.user(), account.password());
		Authentication authentication = authenticationManager.authenticate(request);
		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new Token(token, "Bearer"));
	}
	
	record Account(String user, String password) {};
	
	record Token(String accessToken, String tokenType) {};
}