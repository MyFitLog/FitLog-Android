package com.example.fitlog.ui.calendar

import com.example.fitlog.data.model.exercise.dto.Exercise
import java.time.LocalDate
import java.time.YearMonth

data class CalendarState(
    val currentMonth: YearMonth = YearMonth.now(),  //ex) 2024년 6월
    val startMonth: YearMonth = currentMonth.minusMonths(500), //  표시할 년월 중 가장 작은 값
    val endMonth: YearMonth = currentMonth.plusMonths(500),     // 표시할 년월 중 가장 큰 값
    val firstVisibleMonth: YearMonth = currentMonth,                        //  현재 년월
    val selection: LocalDate? = null,                                       // 현재 선택된 날짜
    val exerciseEntityMonthInfo: Map<LocalDate, List<Exercise>> = mapOf(),   // 현재 달의 저장된 운동 기록 목록
)
