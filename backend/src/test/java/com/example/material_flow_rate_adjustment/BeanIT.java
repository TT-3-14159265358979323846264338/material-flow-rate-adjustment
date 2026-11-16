package com.example.material_flow_rate_adjustment;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.material_flow_rate_adjustment.logindata.RequestFilter;
import com.example.material_flow_rate_adjustment.logindata.TokenProvider;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.scheduledtask.CreateAllDefaultCalendar;
import com.example.material_flow_rate_adjustment.scheduledtask.CreateDefaultCalendar;
import com.example.material_flow_rate_adjustment.scheduledtask.ScheduledTask;

@SpringBootTest
@ActiveProfiles("test")
class BeanIT {
	@Autowired
	private Config config;
	
	@Autowired
	private RequestFilter requestFilter;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ScheduledTask scheduledTask;
	
	@Autowired
	private CreateAllDefaultCalendar createAllDefaultCalendar;
	
	@Autowired
	private CreateDefaultCalendar createDefaultCalendar;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Test
	void existsBean() {
		assertThat(requestFilter).isNotNull();
		assertThat(tokenProvider).isNotNull();
		assertThat(config).isNotNull();
		assertThat(passwordEncoder).isNotNull();
		assertThat(authenticationManager).isNotNull();
		assertThat(scheduledTask).isNotNull();
		assertThat(createAllDefaultCalendar).isNotNull();
		assertThat(createDefaultCalendar).isNotNull();
		assertThat(materialRepository).isNotNull();
	}
}