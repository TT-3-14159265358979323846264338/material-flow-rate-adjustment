package com.example.material_flow_rate_adjustment.commontest;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public abstract class LoggerSetUp {
	protected RuntimeException dummyError = new RuntimeException("dummy error");
	ListAppender<ILoggingEvent> listAppender;
	Logger logger;
	protected abstract Class<?> targetClass();
	
	@BeforeEach
	void setUp() {
		listAppender = new ListAppender<>();
		listAppender.start();
		logger = (Logger) LoggerFactory.getLogger(targetClass());
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
	
	protected void assertNotEmptyErrorLog() {
		assertThat(listAppender.list).isNotEmpty();
	}
}