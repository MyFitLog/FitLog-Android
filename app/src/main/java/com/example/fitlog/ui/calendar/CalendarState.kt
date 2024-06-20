package com.example.fitlog.ui.calendar

import com.example.fitlog.data.room.exercise.ExerciseWithSetInfo
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

data class CalendarState(
    val currentMonth: YearMonth = YearMonth.now(),  //ex) 2024년 6월
    val startMonth: YearMonth = currentMonth.minusMonths(500), //  표시할 년월 중 가장 작은 값
    val endMonth: YearMonth = currentMonth.plusMonths(500),     // 표시할 년월 중 가장 큰 값
    val firstVisibleMonth: YearMonth = currentMonth,                        //  현재 년월
    val firstDayOfWeek: DayOfWeek = daysOfWeek().first(),                   // 표시할 가장 첫번째 요일 현재는 일용리
    val selection: CalendarDay? = null,                                     // 선택된 날짜
    val exerciseEntityMonthInfo: Map<LocalDate, List<ExerciseWithSetInfo>> = mapOf(),   // 현재 달의 저장된 운동 기록 목록
//    val exercisesInSelectedDate: List<ExerciseEntity> = emptyList(),
//    val exercisesInSelection: List<ExerciseWithSetInfo> = emptyList(),      // 현재 선택한 날짜의 운동 기록
)
