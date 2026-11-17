package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AccountInitializerTest {
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private AccountHistoryRepository historyRepository;
	
	@InjectMocks
	private AccountInitializer accountInitializer;
	
	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void initializeAccount(boolean hasSize) {
		doReturn(hasSize).when(accountRepository).existsByRoleAndHasDeletedFalse(any());
		ArgumentCaptor<AccountSQL> accountCaptor = ArgumentCaptor.forClass(AccountSQL.class);
		ArgumentCaptor<AccountHistorySQL> historyCaptor = ArgumentCaptor.forClass(AccountHistorySQL.class);
		doReturn(null).when(accountRepository).save(any());
		doReturn(null).when(historyRepository).save(any());
		accountInitializer.initAdminAccount();
		int count = hasSize? 0: 1;
		verify(accountRepository, times(count)).save(accountCaptor.capture());
		verify(historyRepository, times(count)).save(historyCaptor.capture());
		if(!hasSize) {
			assertThat(accountCaptor.getValue()).satisfies(account -> {
				assertThat(account.getDisplayedUser()).isNotBlank();
				assertThat(account.getRole()).isEqualTo(AccountRole.ADMIN);
				assertThat(account.getHasDeleted()).isFalse();
				assertThat(historyCaptor.getValue()).satisfies(history -> {
					assertThat(history.getNewDisplayedUser()).isEqualTo(account.getDisplayedUser());
					assertThat(history.getNewRole()).isEqualTo(account.getRole().name());
					assertThat(history.getHasDeletedNew()).isEqualTo(account.getHasDeleted());
					assertThat(history.getAction()).isEqualTo(HistoryEnum.CREATE.name());
					assertThat(history.getActionId()).isLessThanOrEqualTo(0);
					assertThat(history.getActionUser()).isNotBlank();
				});
			});
		}
	}
}