package com.example.material_flow_rate_adjustment.authpage.adminpage.getMaterial;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class GetMaterialService {
	private final MaterialRepository materialRepository;
	
	@Transactional(readOnly = true)
	public List<Material> getMaterial(GetMaterialRecord materialSort){
		return materialRepository.findByHasDeletedFalse(materialSort.target().getMaterialSort(materialSort.order()))
								.stream()
								.map(this::createMaterial)
								.toList();
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
}