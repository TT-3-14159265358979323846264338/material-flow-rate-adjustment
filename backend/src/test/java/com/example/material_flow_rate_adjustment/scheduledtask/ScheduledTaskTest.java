package com.example.material_flow_rate_adjustment.scheduledtask;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronExpression;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@ExtendWith(MockitoExtension.class)
class ScheduledTaskTest {
	@Mock
	private CreateAllDefaultCalendar createAllDefaultCalendar;
	
	@InjectMocks
	private ScheduledTask scheduledTask;
	
	private ListAppender<ILoggingEvent> listAppender;
	private Logger logger;
	
	@BeforeEach
	void setUp() {
		listAppender = new ListAppender<>();
		listAppender.start();
		logger = (Logger) LoggerFactory.getLogger(ScheduledTask.class);
		logger.setAdditive(false);
		logger.addAppender(listAppender);
	}
	
	@AfterEach
	void tearDown() {
		if (listAppender != null) {
			logger.detachAppender(listAppender);
			listAppender.stop();
		}
	}
	
	@Test
	void scheduledCron() {
		assertThat(CronExpression.isValidExpression("0 0 0 1 * *")).isTrue();
	}
	
	@Test
	void callMethod() {
		scheduledTask.beginningOfMonthTask();
		verify(createAllDefaultCalendar, times(1)).createCalendar();
	}
	
	@Test
	void callErrorLog() {
		doThrow(new RuntimeException("dummy error")).when(createAllDefaultCalendar).createCalendar();
		scheduledTask.beginningOfMonthTask();
		assertThat(listAppender.list).isNotEmpty();
	}
}