package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseJpaRepository<AccountSQL, Integer>{
	boolean existsByLoginUser(String user);
	boolean existsByDisplayedUser(String user);
	boolean existsByRole(String role);
	long countByRole(String role);
	Optional<AccountSQL> findByLoginUser(String user);
}