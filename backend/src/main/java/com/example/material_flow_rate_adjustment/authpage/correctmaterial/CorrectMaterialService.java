package com.example.material_flow_rate_adjustment.authpage.correctmaterial;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	
	@Transactional(readOnly = true)
	public List<Material> getMaterial(){
		return materialRepository.findAll().stream().map(this::createMaterial).toList();
	}
	
	Material createMaterial(MaterialSQL material) {
		return new Material(material.getId(), material.getName(), material.getDestination());
	}
	
	record Material(int id, String name, String destination) {}
	
	@Transactional
	public boolean adminCorrectMaterialData(AdminCorrectMaterial data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		MaterialSQL material = getMaterialSQL(data.targetId());
		MaterialHistorySQL newHistory = createHistorySQL();
		if(data.isDeleted()) {
			delete(account, material, newHistory);
			return true;
		}
		setName(account, material, newHistory, data.newName());
		setDestination(account, material, newHistory, data.newDestination());
		if(newHistory.getTargetId() != null) {
			save(account, newHistory, HistoryEnum.CHANGE);
		}
		return false;
	}
	
	MaterialSQL getMaterialSQL(int id) {
		return materialRepository.findById(id)
				.orElseThrow(() -> new NotFindException("製品が見つかりません。"));
	}
	
	MaterialHistorySQL createHistorySQL() {
		return new MaterialHistorySQL();
	}
	
	void setName(AccountSQL account, MaterialSQL material, MaterialHistorySQL newHistory, String newName) {
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
	
	void setDestination(AccountSQL account, MaterialSQL material, MaterialHistorySQL newHistory, String destination) {
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
	
	void delete(AccountSQL account, MaterialSQL material, MaterialHistorySQL newHistory) {
		newHistory.setTargetId(material.getId());
		newHistory.setOldName(material.getName());
		newHistory.setOldDestination(material.getDestination());
		materialRepository.delete(material);
		save(account, newHistory, HistoryEnum.DELETE);
	}
	
	void save(AccountSQL account, MaterialHistorySQL newHistory, HistoryEnum code) {
		newHistory.setAction(code.name());
		newHistory.setActionId(account.getId());
		newHistory.setActionUser(account.getDisplayedUser());
		historyRepository.save(newHistory);
	}
}