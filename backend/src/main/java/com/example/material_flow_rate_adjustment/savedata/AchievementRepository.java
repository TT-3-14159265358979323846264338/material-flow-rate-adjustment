package com.example.material_flow_rate_adjustment.savedata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<AchievementSQL, Integer>{
}