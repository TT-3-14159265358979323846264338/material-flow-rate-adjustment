package com.example.material_flow_rate_adjustment.commontest;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.BaseJpaRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.BaseSQL;

public abstract class BaseMainRepositoryIT<T extends BaseJpaRepository<U, Integer>, U extends BaseSQL> extends BaseRepositoryIT<T, U>{
	@Test
	@Transactional
	void findByHasDeletedFalse() {
		List<Integer> savedId = savedIdHasDeletedFalse(saveTestData());
		List<U> result = repository().findByHasDeletedFalse();
		assertThat(result)
			.extracting(U::getId)
			.containsExactlyInAnyOrderElementsOf(savedId);
	}
	
	@Test
	@Transactional
	void findByHasDeletedFalseSort() {
		List<Integer> savedId = savedIdHasDeletedFalse(saveTestData());
		List<U> result = repository().findByHasDeletedFalse(Sort.by(OrderSortEnum.DESCENDING.getOrderSort(), "id"));
		assertThat(result)
			.extracting(U::getId)
			.containsExactlyElementsOf(savedId.reversed());
	}
	
	protected List<Integer> savedIdHasDeletedFalse(List<U> saveData) {
		return saveData.stream().filter(i -> !i.getHasDeleted()).map(U::getId).toList();
	}
}