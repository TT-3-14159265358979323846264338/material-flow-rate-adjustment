package com.example.material_flow_rate_adjustment.scheduledtask;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.defaulttestdata.BaseSQLNoJoinColumn;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterial;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterialTransaction;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;

@SpringBootTest
@ActiveProfiles("test")
class ScheduledTaskIT{
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private CalendarHistoryRepository calendarHistoryRepository;
	
	@Autowired
	private ScheduledTask scheduledTask;
	
	@Autowired
	private DefaultMaterialTransaction defaultMaterialTransaction;
	
	@Test
	@Transactional
	void scheduledComit() {
		defaultMaterialTransaction.saveDefaultMaterial();
		scheduledTask.beginningOfMonthTask();
		int expected = holidayCount() * materialCount();
		List<CalendarSQL> calendarResult = calendarRepository.findAll();
		assertThat(calendarResult).hasSize(expected);
		List<CalendarHistorySQL> historyResult = calendarHistoryRepository.findAll();
		assertThat(historyResult).hasSize(expected);
	}
	
	int holidayCount() {
		LocalDate targetMonth = YearMonth.now().plusMonths(2).atDay(1);
		return (int) targetMonth.datesUntil(targetMonth.plusMonths(1))
							.filter(i -> i.getDayOfWeek().equals(DayOfWeek.SATURDAY) || i.getDayOfWeek().equals(DayOfWeek.SUNDAY))
							.count();
	}
	
	int materialCount() {
		return (int) BaseSQLNoJoinColumn.getSQLList(DefaultMaterial.values()).stream().filter(i -> !i.getHasDeleted()).count();
	}
}