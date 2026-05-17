package com.example.material_flow_rate_adjustment.savedata;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountSQL, Integer>{
	boolean existsByRole(String role);
	Optional<AccountSQL> findByUser(String user);
}