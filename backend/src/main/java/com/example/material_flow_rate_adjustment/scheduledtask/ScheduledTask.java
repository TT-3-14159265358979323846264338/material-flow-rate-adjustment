package com.example.material_flow_rate_adjustment.scheduledtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
	private final CreateAllDefaultCalender createAllDefaultCalender;
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void beginningOfMonthTask() {
		try {
			createAllDefaultCalender.createCalender();
		}catch(Exception e) {
			System.err.println("月初処理中にエラーが発生しました: " + e);
		}
	}
}