package com.example.material_flow_rate_adjustment.commontest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;

@Component
public class DefaultMaterialTransaction {
	@Autowired
	private MaterialRepository materialRepository;
	
	/**
	 * 別の@Transactional(propagation = Propagation.REQUIRES_NEW)はsaveAll後にComitする必要がある。
	 * そのため、saveAllを切り出して、確実にComitしてからテストを実行しないといけない。
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveDefaultMaterial() {
		materialRepository.saveAll(DefaultMaterial.getMaterialList());
	}
}
