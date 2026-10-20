package com.example.material_flow_rate_adjustment.savedata.historydata;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseHistoryRepository<T extends BaseHistorySQL, U> extends JpaRepository<T, U>{
	List<T> findByDateBetween(LocalDateTime minDate, LocalDateTime maxDate, Sort sort);
}