package com.example.material_flow_rate_adjustment.scheduledtask;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateAllDefaultCalender {
	private final MaterialRepository materialRepository;
	private final CreateDefaultCalender createDefaultCalender;
	private final int PLUS_MONTH = 2;
	
	@Transactional(readOnly = true)
	public void createCalender() {
		try {
			LocalDate targetMonth = YearMonth.now().plusMonths(PLUS_MONTH).atDay(1);
			materialRepository.findByHasDeletedFalse().forEach(i -> handle(i.getId(), i.getName(), targetMonth));
		}catch(Exception e) {
			System.err.println("月初処理中に製品の取り込みに失敗しました: " + e);
		}
	}
	
	void handle(int materialId, String name, LocalDate targetMonth) {
		try {
			createDefaultCalender.createCalenderRollback(materialId, targetMonth);
		}catch(Exception e) {
			System.err.println("月初処理中に" + name + "の休日登録に失敗しました: " + e);
		}
	}
	
	public void createCalender(MaterialSQL material) {
		LocalDate now = YearMonth.now().atDay(1);
		IntStream.range(0, PLUS_MONTH + 1).mapToObj(i -> now.plusMonths(i)).forEach(i -> createDefaultCalender.createCalender(material, i));
	}
}