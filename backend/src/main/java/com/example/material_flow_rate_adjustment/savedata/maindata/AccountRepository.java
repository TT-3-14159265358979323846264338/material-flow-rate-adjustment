package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseJpaRepository<AccountSQL, Integer>{
	boolean existsByLoginUser(String user);
	boolean existsByDisplayedUser(String user);
	boolean existsByRole(AccountRole role);
	long countByRole(AccountRole role);
	Optional<AccountSQL> findByLoginUser(String user);
	List<AccountSQL> findByRoleInAndHasDeletedFalse(List<AccountRole> roles, Sort sort);
}