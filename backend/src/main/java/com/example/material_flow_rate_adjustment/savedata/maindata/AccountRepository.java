package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountSQL, Integer>{
	boolean existsByLoginUser(String user);
	boolean existsByDisplayedUser(String user);
	boolean existsByRole(String role);
	Optional<AccountSQL> findByLoginUser(String user);
}