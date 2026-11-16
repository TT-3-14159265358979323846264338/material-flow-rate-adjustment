package com.example.material_flow_rate_adjustment.scheduledtask;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.material_flow_rate_adjustment.commontest.LoggerSetUp;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

@ExtendWith(MockitoExtension.class)
class CreateAllDefaultCalendarTest extends LoggerSetUp{
	@Mock
	private MaterialRepository materialRepository;
	
	@Mock
	private CreateDefaultCalendar createDefaultCalendar;
	
	@InjectMocks
	private CreateAllDefaultCalendar createAllDefaultCalendar;
	
	@Captor
	private ArgumentCaptor<YearMonth> yearMonthCaptor;
	
	@Override
	protected Class<?> targetClass() {
		return CreateAllDefaultCalendar.class;
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 5})
	void callScheduledMethod(int number) {
		ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
		doReturn(mockSQLList(number)).when(materialRepository).findByHasDeletedFalse();
		createAllDefaultCalendar.createCalendar();
		verify(createDefaultCalendar, times(number)).createCalendarRollback(idCaptor.capture(), yearMonthCaptor.capture());
		assertThat(idCaptor.getAllValues()).containsExactlyElementsOf(resultIdList(number));
		assertThat(yearMonthCaptor.getAllValues()).allMatch(i -> Objects.equals(i, YearMonth.now().plusMonths(2)));
	}
	
	List<MaterialSQL> mockSQLList(int number){
		return numberStream(number).mapToObj(this::mockSQL).toList();
	}
	
	IntStream numberStream(int number) {
		return IntStream.range(0, number);
	}
	
	MaterialSQL mockSQL(int number) {
		MaterialSQL mockSQL = mock(MaterialSQL.class);
		doReturn(number).when(mockSQL).getId();
		return mockSQL;
	}
	
	List<Integer> resultIdList(int number){
		return numberStream(number).boxed().toList();
	}
	
	@Test
	void callErrorLogInCreateCalendar() {
		doThrow(dummyError).when(materialRepository).findByHasDeletedFalse();
		createAllDefaultCalendar.createCalendar();
		assertNotEmptyErrorLog();
	}
	
	@Test
	void callErrorLogInHandle() {
		doThrow(dummyError).when(createDefaultCalendar).createCalendarRollback(anyInt(), any());
		createAllDefaultCalendar.handle(0, null, null);
		assertNotEmptyErrorLog();
	}
	
	@Test
	void callSingleMethod() {
		MaterialSQL mockMaterial = mock(MaterialSQL.class);
		AccountSQL mockAccount = mock(AccountSQL.class);
		ArgumentCaptor<MaterialSQL> materialCaptor = ArgumentCaptor.forClass(MaterialSQL.class);
		ArgumentCaptor<AccountSQL> accountCaptor = ArgumentCaptor.forClass(AccountSQL.class);
		createAllDefaultCalendar.createCalendar(mockMaterial, mockAccount);
		verify(createDefaultCalendar, times(3)).createCalendar(materialCaptor.capture(), yearMonthCaptor.capture(), accountCaptor.capture());
		assertThat(materialCaptor.getAllValues()).containsOnly(mockMaterial);
		assertThat(yearMonthCaptor.getAllValues()).containsExactlyElementsOf(resultYearMonth());
		assertThat(accountCaptor.getAllValues()).containsOnly(mockAccount);
	}
	
	List<YearMonth> resultYearMonth(){
		return numberStream(3).mapToObj(i -> YearMonth.now().plusMonths(i)).toList();
	}
}