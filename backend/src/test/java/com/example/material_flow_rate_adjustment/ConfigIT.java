package com.example.material_flow_rate_adjustment;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.logindata.RequestFilter;
import com.example.material_flow_rate_adjustment.logindata.TokenProvider;

@WebMvcTest(TestController.class)
@Import(Config.class)
class ConfigIT {
	@Value("${front.base.api}")
	private String frontBaseAPI;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoSpyBean
	private RequestFilter requestFilter;
	
	@MockitoBean
	private TokenProvider tokenProvider;
	
	@Test
	void validAnnotations() {
		assertThat(Config.class.isAnnotationPresent(EnableWebSecurity.class)).isTrue();
		assertThat(Config.class.isAnnotationPresent(EnableMethodSecurity.class)).isTrue();
		assertThat(Config.class.isAnnotationPresent(EnableScheduling.class)).isTrue();
	}
	
	@Test
	void existsBeanAuthenticationManager() {
		assertThat(authenticationManager).isNotNull();
	}
	
	@Test
	void existsBeanPasswordEncoder() {
		assertThat(passwordEncoder).isNotNull();
	}
	
	@Test
	void successfulRequestsForSecurityFilterChain() throws Exception {
		mockMvc.perform(get("/api/login")).andExpect(status().isOk());
	}
	
	@Test
	void unsuccessfulRequestsForSecurityFilterChain() throws Exception {
		mockMvc.perform(get("/api/another")).andExpect(status().isForbidden());
	}
	
	@Test
	void successfulNoTokenRequestsForSecurityFilterChain() throws Exception {
		mockMvc.perform(post("/api/another")).andExpect(status().isForbidden());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"POST", "PUT", "DELETE"})
	void successfulRequestsForAPI(String method) throws Exception {
		mockMvc.perform(createRequest(frontBaseAPI, method))
				.andExpect(status().isOk())
				.andExpect(header().string("Access-Control-Allow-Origin", frontBaseAPI))
				.andExpect(header().exists("Access-Control-Allow-Methods"))
				.andExpect(header().string("Access-Control-Allow-Credentials", "true"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"POST", "PUT", "DELETE"})
	void unsuccessfulRequestsForAPI(String method) throws Exception {
		mockMvc.perform(createRequest("http://dummy", method)).andExpect(status().isForbidden());
	}
	
	MockHttpServletRequestBuilder createRequest(String baseAPI, String method) {
		return options("/api/login")
				.header("Origin", baseAPI)
				.header("Access-Control-Request-Method", method)
				.header("Access-Control-Request-Headers", "Authorization, Content-Type");
	}
}

@RestController
class TestController {
	@GetMapping("/api/login")
	public String loginGetMapping() {
		return "OK";
	}
	
	@PostMapping("/api/login")
	public String loginPostMapping() {
		return "OK";
	}
	
	@PutMapping("/api/login")
	public String loginPutMapping() {
		return "OK";
	}
	
	@DeleteMapping("/api/login")
	public String loginDeleteMapping() {
		return "OK";
	}
	
	@GetMapping("/api/another")
	public String anotherGetMapping() {
		return "OK";
	}
	
	@PostMapping("/api/another")
	public String anotherPostMapping() {
		return "OK";
	}
}