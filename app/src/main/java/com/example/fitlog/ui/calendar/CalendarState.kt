package com.example.fitlog.ui.calendar

import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.YearMonth

data class CalendarState(
    val currentMonth: YearMonth = YearMonth.now(),
    val startMonth: YearMonth = currentMonth.minusMonths(500),
    val endMonth: YearMonth = currentMonth.plusMonths(500),
    val firstVisibleMonth: YearMonth = currentMonth,
    val firstDayOfWeek: DayOfWeek = daysOfWeek().first(),
    val selection: CalendarDay? = null
)
