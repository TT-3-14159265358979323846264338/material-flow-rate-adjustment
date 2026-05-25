package com.example.material_flow_rate_adjustment.authpage.newmaterial;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewMaterialService {
	private final MaterialRepository materialRepository;
	private final MaterialHistoryRepository historyRepository;
	private final UtilityService utility;
	
	@Transactional
	public String createNewMaterial(NewMaterial data, String loginUser){
		MaterialSQL newMaterial = createMaterialSQL(data.name(), data.destination());
		materialRepository.save(newMaterial);
		MaterialHistorySQL newHistory = createMaterialHistorySQL(newMaterial, loginUser);
		historyRepository.save(newHistory);
		return "新規製品を登録しました。";
	}
	
	MaterialSQL createMaterialSQL(String name, String destination) {
		MaterialSQL newMaterial = new MaterialSQL();
		newMaterial.setName(name);
		newMaterial.setDestination(destination);
		return newMaterial;
	}
	
	MaterialHistorySQL createMaterialHistorySQL(MaterialSQL newMaterial, String loginUser) {
		MaterialHistorySQL newHistory = new MaterialHistorySQL();
		newHistory.setTargetId(newMaterial.getId());
		newHistory.setNewName(newMaterial.getName());
		newHistory.setNewDestination(newMaterial.getDestination());
		newHistory.setAction(HistoryEnum.CREATE.name());
		newHistory.setActionId(Integer.parseInt(loginUser));
		newHistory.setActionUser(utility.getAccountSQL(loginUser).getDisplayedUser());
		return newHistory;
	}
}