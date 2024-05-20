package com.example.fitlog.ui.calendar

import com.example.fitlog.common.Exercise
import com.example.fitlog.common.ExerciseSet
import com.example.fitlog.ui.theme.Purple80
import com.example.fitlog.ui.theme.PurpleGrey40
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
    val selection: CalendarDay? = null,
    val exercisesInSelection: List<Exercise> = listOf(
        Exercise(
            "벤치프레스",
            3,
            listOf(
                ExerciseSet("벤치프레스", 50, 10),
                ExerciseSet("벤치프레스", 50, 10),
                ExerciseSet("벤치프레스", 50, 10),
                ExerciseSet("벤치프레스", 50, 10),
            ),
            Purple80
        ),
        Exercise(
            "렛 풀 다운",
            3,
            listOf(
                ExerciseSet("렛 풀 다운", 50, 10),
                ExerciseSet("렛 풀 다운", 50, 10),
                ExerciseSet("렛 풀 다운", 50, 10),
                ExerciseSet("렛 풀 다운", 50, 10),
            ),
            PurpleGrey40
        ),
        Exercise(
            "이지바 트라이셉스 익스텐션",
            3,
            listOf(
                ExerciseSet("이지바 트라이셉스 익스텐션", 50, 10),
                ExerciseSet("이지바 트라이셉스 익스텐션", 50, 10),
                ExerciseSet("이지바 트라이셉스 익스텐션", 50, 10),
                ExerciseSet("이지바 트라이셉스 익스텐션", 50, 10),
            ),
            androidx.compose.ui.graphics.Color.Green
        ),
    )
)
