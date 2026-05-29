package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.authpage.HistoryService;
import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.MaterialHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectMaterialService {
	private final MaterialRepository materialRepository;
	private final MaterialHistoryRepository historyRepository;
	private final UtilityService utility;
	private final HistoryService historyService;
	
	@Transactional(readOnly = true)
	public List<Material> getMaterial(){
		return materialRepository.findAll().stream().map(this::createMaterial).toList();
	}
	
	Material createMaterial(MaterialSQL material) {
		return new Material(material.getId(), 
				material.getName(), 
				material.getDestination(), 
				material.getBase(), 
				material.getUnit());
	}
	
	record Material(int id, 
			String name, 
			String destination, 
			Integer base, 
			String unit) {}
	
	@Transactional
	public boolean adminCorrectMaterialData(AdminCorrectMaterial data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		MaterialSQL material = getMaterialSQL(data.targetId());
		MaterialHistorySQL newHistory = createHistorySQL();
		if(data.isDeleted()) {
			delete(account, material, newHistory);
			return true;
		}
		setName(material, newHistory, data.newName());
		setDestination(material, newHistory, data.newDestination());
		setBase(material, newHistory, data.newBase());
		setUnit(material, newHistory, data.newUnit());
		historyService.saveHistory(account, newHistory, historyRepository, HistoryEnum.CHANGE);
		return false;
	}
	
	MaterialSQL getMaterialSQL(int id) {
		return materialRepository.findById(id)
				.orElseThrow(() -> new NotFindException("製品が見つかりません。"));
	}
	
	MaterialHistorySQL createHistorySQL() {
		return new MaterialHistorySQL();
	}
	
	void setName(MaterialSQL material, MaterialHistorySQL newHistory, String newName) {
		if(!StringUtils.hasLength(newName)) {
			return;
		}
		if(material.getName().equals(newName)) {
			return;
		}
		newHistory.setTargetId(material.getId());
		newHistory.setOldName(material.getName());
		newHistory.setNewName(newName);
		material.setName(newName);
	}
	
	void setDestination(MaterialSQL material, MaterialHistorySQL newHistory, String destination) {
		if(!StringUtils.hasLength(destination)) {
			return;
		}
		if(material.getDestination().equals(destination)) {
			return;
		}
		newHistory.setTargetId(material.getId());
		newHistory.setOldDestination(material.getDestination());
		newHistory.setNewDestination(destination);
		material.setDestination(destination);
	}
	
	void setBase(MaterialSQL material, MaterialHistorySQL newHistory, String base) {
		Integer newBase = StringUtils.hasLength(base)? Integer.parseInt(base): null;
		if(Objects.equals(material.getBase(), newBase)) {
			return;
		}
		newHistory.setTargetId(material.getId());
		newHistory.setOldBase(material.getBase());
		newHistory.setNewBase(newBase);
		material.setBase(newBase);
	}
	
	void setUnit(MaterialSQL material, MaterialHistorySQL newHistory, String unit) {
		if(!StringUtils.hasLength(unit)) {
			return;
		}
		if(material.getUnit().equals(unit)) {
			return;
		}
		newHistory.setTargetId(material.getId());
		newHistory.setOldUnit(material.getUnit());
		newHistory.setNewUnit(unit);
		material.setUnit(unit);
	}
	
	void delete(AccountSQL account, MaterialSQL material, MaterialHistorySQL newHistory) {
		newHistory.setTargetId(material.getId());
		newHistory.setOldName(material.getName());
		newHistory.setOldDestination(material.getDestination());
		materialRepository.delete(material);
		historyService.saveHistory(account, newHistory, historyRepository, HistoryEnum.DELETE);
	}
}