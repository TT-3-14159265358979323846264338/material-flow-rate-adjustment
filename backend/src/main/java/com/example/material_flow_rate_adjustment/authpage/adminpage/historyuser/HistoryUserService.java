package com.example.material_flow_rate_adjustment.authpage.adminpage.historyuser;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;
import com.example.material_flow_rate_adjustment.authpage.HistoryService;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryUserService {
	private final AccountHistoryRepository repository;
	private final HistoryService historyService;
	
	@Transactional(readOnly = true)
	public List<History> getHistory(DefaultHistoryFilterRecord filter) {
		return historyService.getHistory(filter, repository).map(this::createHistory).toList();
	}
	
	History createHistory(AccountHistorySQL history) {
		return new History(history.getId(),
				history.getOldLoginUser(),
				history.getNewLoginUser(),
				history.getOldDisplayedUser(),
				history.getNewDisplayedUser(),
				history.getOldRole(),
				history.getNewRole(),
				history.getAction(),
				history.getActionUser(),
				history.getDate());
	}
	
	record History(Integer id,
			String oldLoginUser, 
			String newLoginUser, 
			String oldDisplayedUser, 
			String newDisplayedUser, 
			String oldRole, 
			String newRole, 
			String action, 
			String actionUser, 
			LocalDateTime date) {}
}