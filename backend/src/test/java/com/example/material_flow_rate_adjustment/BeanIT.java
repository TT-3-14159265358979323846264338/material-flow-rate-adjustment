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
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AchievementHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.PlanHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountInitializer;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AdjustmentRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
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
	private AccountInitializer accountInitializer;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AchievementRepository achievementRepository;
	
	@Autowired
	private AdjustmentRepository adjustmentRepository;
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MonthPlanRepository monthPlanRepository;
	
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	@Autowired
	private AchievementHistoryRepository achievementHistoryRepository;
	
	@Autowired
	private CalendarHistoryRepository calendarHistoryRepository;
	
	@Autowired
	private MaterialHistoryRepository materialHistoryRepository;
	
	@Autowired
	private PlanHistoryRepository planHistoryRepository;
	
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
		assertThat(accountInitializer).isNotNull();
		assertThat(accountRepository).isNotNull();
		assertThat(achievementRepository).isNotNull();
		assertThat(adjustmentRepository).isNotNull();
		assertThat(calendarRepository).isNotNull();
		assertThat(materialRepository).isNotNull();
		assertThat(monthPlanRepository).isNotNull();
		assertThat(accountHistoryRepository).isNotNull();
		assertThat(achievementHistoryRepository).isNotNull();
		assertThat(calendarHistoryRepository).isNotNull();
		assertThat(materialHistoryRepository).isNotNull();
		assertThat(planHistoryRepository).isNotNull();
	}
}