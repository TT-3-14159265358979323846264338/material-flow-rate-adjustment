package com.example.material_flow_rate_adjustment.authpage.adminpage.newmaterial;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;
import com.example.material_flow_rate_adjustment.scheduledtask.CreateAllDefaultCalender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewMaterialService {
	private final MaterialRepository materialRepository;
	private final MaterialHistoryRepository historyRepository;
	private final UtilityService utility;
	private final CreateAllDefaultCalender createAllDefaultCalender;
	
	@Transactional
	public String createNewMaterial(NewMaterial data, String loginUser){
		AccountSQL account = utility.getAccountSQL(loginUser);
		MaterialSQL newMaterial = createMaterialSQL(data.name(), data.destination(), data.base(), data.unit());
		materialRepository.save(newMaterial);
		MaterialHistorySQL newHistory = createMaterialHistorySQL(newMaterial, account);
		historyRepository.save(newHistory);
		createAllDefaultCalender.createCalender(newMaterial, account);
		return "新規製品を登録しました。";
	}
	
	MaterialSQL createMaterialSQL(String name, String destination, Integer base, String unit) {
		return MaterialSQL.builder()
				.name(name)
				.destination(destination)
				.base(base)
				.unit(unit)
				.hasDeleted(false)
				.build();
	}
	
	MaterialHistorySQL createMaterialHistorySQL(MaterialSQL newMaterial, AccountSQL account) {
		return MaterialHistorySQL.builder()
				.targetId(newMaterial.getId())
				.newName(newMaterial.getName())
				.newDestination(newMaterial.getDestination())
				.newBase(newMaterial.getBase())
				.newBase(newMaterial.getBase())
				.newUnit(newMaterial.getUnit())
				.action(HistoryEnum.CREATE.name())
				.actionId(account.getId())
				.actionUser(account.getDisplayedUser())
				.hasDeletedNew(false)
				.build();
	}
}