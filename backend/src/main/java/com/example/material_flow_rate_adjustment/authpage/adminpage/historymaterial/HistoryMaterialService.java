package com.example.material_flow_rate_adjustment.authpage.adminpage.historymaterial;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;
import com.example.material_flow_rate_adjustment.authpage.HistoryService;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryMaterialService {
	private final MaterialHistoryRepository repository;
	private final HistoryService historyService;
	
	@Transactional(readOnly = true)
	public List<History> getHistory(DefaultHistoryFilterRecord filter) {
		return historyService.getHistory(filter, repository).map(this::createHistory).toList();
	}
	
	History createHistory(MaterialHistorySQL history) {
		return new History(history.getId(),
				history.getOldName(),
				history.getNewName(),
				history.getOldDestination(),
				history.getNewDestination(),
				history.getOldBase(),
				history.getNewBase(),
				history.getOldUnit(),
				history.getNewUnit(),
				history.getAction(),
				history.getActionUser(),
				history.getDate());
	}
	
	record History(Integer id,
			String oldName, 
			String newName, 
			String oldDestination, 
			String newDestination, 
			Integer oldBase, 
			Integer newBase, 
			String oldUnit, 
			String newUnit, 
			String action, 
			String actionUser, 
			LocalDateTime date) {}
}