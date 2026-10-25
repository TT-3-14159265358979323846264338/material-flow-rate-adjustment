package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseJpaRepository<AccountSQL, Integer>{
	boolean existsByLoginUserAndHasDeletedFalse(String user);
	boolean existsByDisplayedUserAndHasDeletedFalse(String user);
	boolean existsByRoleAndHasDeletedFalse(AccountRole role);
	long countByRoleAndHasDeletedFalse(AccountRole role);
	Optional<AccountSQL> findByLoginUserAndHasDeletedFalse(String user);
	List<AccountSQL> findByRoleInAndHasDeletedFalse(List<AccountRole> roles, Sort sort);
}