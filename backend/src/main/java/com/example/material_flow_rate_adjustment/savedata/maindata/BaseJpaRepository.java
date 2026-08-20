package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseSQL, U> extends JpaRepository<T, U>{
	List<T> findByHasDeletedFalse();
}