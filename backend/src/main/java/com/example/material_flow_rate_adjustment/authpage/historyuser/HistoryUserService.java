package com.example.material_flow_rate_adjustment.authpage.historyuser;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryUserService {
	private final AccountHistoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<History> getHistory() {
		return repository.findAll().stream().map(this::createHistory).toList();
	}
	
	History createHistory(AccountHistorySQL history) {
		return new History(history.getTargetId(),
				history.getOldUser(),
				history.getNewUser(),
				history.getOldRole(),
				history.getNewRole(),
				history.getAction(),
				history.getActionUser(),
				history.getDate());
	}
	
	record History(Integer targetId,
			String oldUser, 
			String newUser, 
			String oldRole, 
			String newRole, 
			String action, 
			String actionUser, 
			LocalDateTime date) {}
}