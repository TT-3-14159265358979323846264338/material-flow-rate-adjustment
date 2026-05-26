package com.example.material_flow_rate_adjustment.authpage.adminpage.historymaterial;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryMaterialService {
	private final MaterialHistoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<History> getHistory() {
		return repository.findAll().stream().map(this::createHistory).toList();
	}
	
	History createHistory(MaterialHistorySQL history) {
		return new History(history.getTargetId(),
				history.getOldName(),
				history.getNewName(),
				history.getOldDestination(),
				history.getNewDestination(),
				history.getAction(),
				history.getActionUser(),
				history.getDate());
	}
	
	record History(Integer targetId,
			String oldName, 
			String newName, 
			String oldDestination, 
			String newDestination, 
			String action, 
			String actionUser, 
			LocalDateTime date) {}
}