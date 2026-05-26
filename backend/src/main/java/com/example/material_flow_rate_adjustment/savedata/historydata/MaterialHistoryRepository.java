package com.example.material_flow_rate_adjustment.savedata.historydata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialHistoryRepository extends JpaRepository<MaterialHistorySQL, Integer>{
}