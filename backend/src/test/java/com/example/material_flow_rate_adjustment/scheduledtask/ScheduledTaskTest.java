package com.example.material_flow_rate_adjustment.scheduledtask;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.support.CronExpression;

import com.example.material_flow_rate_adjustment.commontest.LoggerSetUp;

@ExtendWith(MockitoExtension.class)
class ScheduledTaskTest extends LoggerSetUp{
	@Mock
	private CreateAllDefaultCalendar createAllDefaultCalendar;
	
	@InjectMocks
	private ScheduledTask scheduledTask;
	
	@Override
	protected Class<?> targetClass() {
		return ScheduledTask.class;
	}
	
	@Test
	void scheduledCron() {
		assertThat(CronExpression.isValidExpression("0 0 0 1 * *")).isTrue();
	}
	
	@Test
	void callMethod() {
		scheduledTask.beginningOfMonthTask();
		verify(createAllDefaultCalendar).createCalendar();
	}
	
	@Test
	void callErrorLog() {
		doThrow(dummyError).when(createAllDefaultCalendar).createCalendar();
		scheduledTask.beginningOfMonthTask();
		assertNotEmptyErrorLog();
	}
}