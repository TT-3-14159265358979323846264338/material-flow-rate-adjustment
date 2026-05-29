package com.example.material_flow_rate_adjustment.authpage.adminpage.newmaterial;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
		Integer baseValue = StringUtils.hasLength(data.base())? Integer.parseInt(data.base()): null;
		MaterialSQL newMaterial = createMaterialSQL(data.name(), data.destination(), baseValue, data.unit());
		materialRepository.save(newMaterial);
		MaterialHistorySQL newHistory = createMaterialHistorySQL(newMaterial, loginUser);
		historyRepository.save(newHistory);
		return "新規製品を登録しました。";
	}
	
	MaterialSQL createMaterialSQL(String name, String destination, Integer base, String unit) {
		MaterialSQL newMaterial = new MaterialSQL();
		newMaterial.setName(name);
		newMaterial.setDestination(destination);
		newMaterial.setBase(base);
		newMaterial.setUnit(unit);
		return newMaterial;
	}
	
	MaterialHistorySQL createMaterialHistorySQL(MaterialSQL newMaterial, String loginUser) {
		MaterialHistorySQL newHistory = new MaterialHistorySQL();
		newHistory.setTargetId(newMaterial.getId());
		newHistory.setNewName(newMaterial.getName());
		newHistory.setNewDestination(newMaterial.getDestination());
		newHistory.setNewBase(newMaterial.getBase());
		newHistory.setNewBase(newMaterial.getBase());
		newHistory.setNewUnit(newMaterial.getUnit());
		newHistory.setAction(HistoryEnum.CREATE.name());
		newHistory.setActionId(Integer.parseInt(loginUser));
		newHistory.setActionUser(utility.getAccountSQL(loginUser).getDisplayedUser());
		return newHistory;
	}
}