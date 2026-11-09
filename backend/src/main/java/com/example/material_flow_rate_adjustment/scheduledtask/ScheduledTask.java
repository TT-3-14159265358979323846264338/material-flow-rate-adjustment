package com.example.material_flow_rate_adjustment.scheduledtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {
	private final CreateAllDefaultCalendar createAllDefaultCalendar;
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void beginningOfMonthTask() {
		try {
			createAllDefaultCalendar.createCalendar();
		}catch(Exception e) {
			log.error("月初処理中にエラーが発生しました", e);
		}
	}
}