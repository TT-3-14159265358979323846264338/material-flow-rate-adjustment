package com.example.material_flow_rate_adjustment.scheduledtask;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.commontest.DefaultCalendar;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseMaterialHistory;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreateDefaultCalendarTest {
	@Mock
	private CalendarRepository calendarRepository;
	
	@Mock
	private CalendarHistoryRepository calendarHistoryRepository;
	
	@Mock
	private UtilityService utility;
	
	@Spy
	@InjectMocks
	private CreateDefaultCalendar createDefaultCalendar;
	
	@Mock
	private MaterialSQL material;
	
	@Test
	void callCreateCalendarExistsRollback() {
		YearMonth mockYearMonth = mock(YearMonth.class);
		doReturn(material).when(utility).getMaterialSQL(anyInt());
		ArgumentCaptor<MaterialSQL> materialCaptor = ArgumentCaptor.forClass(MaterialSQL.class);
		ArgumentCaptor<YearMonth> yearMonthCaptor = ArgumentCaptor.forClass(YearMonth.class);
		ArgumentCaptor<AccountSQL> accountCaptor = ArgumentCaptor.forClass(AccountSQL.class);
		doNothing().when(createDefaultCalendar).createCalendar(any(), any(), any());
		createDefaultCalendar.createCalendarRollback(0, mockYearMonth);
		verify(createDefaultCalendar).createCalendar(materialCaptor.capture(), yearMonthCaptor.capture(), accountCaptor.capture());
		assertThat(materialCaptor.getValue()).isEqualTo(material);
		assertThat(yearMonthCaptor.getValue()).isEqualTo(mockYearMonth);
		assertThat(accountCaptor.getValue()).isNull();
	}
	
	@ParameterizedTest
	@MethodSource("defaultData")
	void saveCalendarRepository(AccountSQL account) {
		mockMaterial();
		doReturn(DefaultCalendar.getCalendarList(material)).when(calendarRepository).findWithEntityGraphByHolidayBetweenAndMaterial(any(), any(), any());
		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<CalendarSQL>> savedCalendar = ArgumentCaptor.forClass(List.class);
		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<CalendarHistorySQL>> savedHistory = ArgumentCaptor.forClass(List.class);
		doReturn(null).when(calendarRepository).saveAll(any());
		doReturn(null).when(calendarHistoryRepository).saveAll(any());
		createDefaultCalendar.createCalendar(material, DefaultCalendar.MONTH, account);
		verify(calendarRepository).saveAll(savedCalendar.capture());
		verify(calendarHistoryRepository).saveAll(savedHistory.capture());
		assertThat(savedCalendar.getValue()).containsExactlyInAnyOrderElementsOf(expectedSavedCalendar());
		assertThat(savedHistory.getValue()).containsExactlyInAnyOrderElementsOf(expectedSavedHistory(account));
	}
	
	static Stream<Arguments> defaultData(){
		return Stream.of(
				Arguments.of((Object) null),
				Arguments.of(mockAccount(1, "dummyAccont")));
	}
	
	static AccountSQL mockAccount(int id, String name) {
		AccountSQL mockAccount = mock(AccountSQL.class);
		doReturn(id).when(mockAccount).getId();
		doReturn(name).when(mockAccount).getDisplayedUser();
		return mockAccount;
	}
	
	void mockMaterial() {
		doReturn(1).when(material).getId();
		doReturn("dummyMaterialName").when(material).getName();
		doReturn("dummyDestination").when(material).getDestination();
	}
	
	List<CalendarSQL> expectedSavedCalendar(){
		return List.of(
				createCalendarSQL(DefaultCalendar.DUMMY_4.getDay(), DefaultCalendar.DUMMY_4.getType(), false),
				createCalendarSQL(DefaultCalendar.DUMMY_5.getDay(), DefaultCalendar.DUMMY_5.getType(), false),
				createCalendarSQL(DefaultCalendar.DUMMY_6.getDay(), DefaultCalendar.DUMMY_6.getType(), false),
				createCalendarSQL(28, HolidayType.ALL_DAY, false),
				createCalendarSQL(29, HolidayType.ALL_DAY, false)
				);
	}
	
	CalendarSQL createCalendarSQL(int day, HolidayType type, boolean hasDeleted) {
		return CalendarSQL.builder()
				.material(material)
				.holiday(DefaultCalendar.getDate(day))
				.type(type)
				.hasDeleted(hasDeleted)
				.build();
	}
	
	List<CalendarHistorySQL> expectedSavedHistory(AccountSQL account){
		int id = account == null? -1: 1;
		String name = account == null? "システム自動": "dummyAccont";
		return List.of(
				createChangeCalendarHistory(DefaultCalendar.DUMMY_4.getDay(), DefaultCalendar.DUMMY_4.getType(), id, name),
				createChangeCalendarHistory(DefaultCalendar.DUMMY_5.getDay(), DefaultCalendar.DUMMY_5.getType(), id, name),
				createChangeCalendarHistory(DefaultCalendar.DUMMY_6.getDay(), DefaultCalendar.DUMMY_6.getType(), id, name),
				createNewCalendarHistory(28, HolidayType.ALL_DAY, id, name),
				createNewCalendarHistory(29, HolidayType.ALL_DAY, id, name)
				);
	}
	
	CalendarHistorySQL createChangeCalendarHistory(int day, HolidayType type, int id, String name) {
		return CalendarHistorySQL.builder()
				.targetId(null)
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.oldMaterialId(1)
						.oldName("dummyMaterialName")
						.oldDestination("dummyDestination")
						.build())
				.oldHoliday(DefaultCalendar.getDate(day))
				.oldType(type.name())
				.hasDeletedOld(true)
				.hasDeletedNew(false)
				.action(HistoryEnum.CHANGE.name())
				.actionId(id)
				.actionUser(name)
				.build();
	}
	
	CalendarHistorySQL createNewCalendarHistory(int day, HolidayType type, int id, String name) {
		return CalendarHistorySQL.builder()
				.targetId(null)
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.newMaterialId(1)
						.newName("dummyMaterialName")
						.newDestination("dummyDestination")
						.build())
				.newHoliday(DefaultCalendar.getDate(day))
				.newType(type.name())
				.hasDeletedNew(false)
				.action(HistoryEnum.CREATE.name())
				.actionId(id)
				.actionUser(name)
				.build();
	}
}