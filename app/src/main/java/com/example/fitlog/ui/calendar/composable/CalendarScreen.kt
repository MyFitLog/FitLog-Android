package com.example.fitlog.ui.calendar.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fitlog.R
import com.example.fitlog.common.convertToMonthlyColorList
import com.example.fitlog.data.model.exercise.dto.Exercise
import com.example.fitlog.ui.calendar.CalendarState
import com.example.fitlog.ui.theme.PageBackgroundColor
import com.example.fitlog.ui.theme.ToolbarColor
import java.time.LocalDate
import java.time.YearMonth

private val toolbarColor = ToolbarColor
private val pageBackgroundColor = PageBackgroundColor

@Composable
fun CalendarScreen(
    state: CalendarState,
    selectDay: (LocalDate?) -> Unit,
    fetchData: (YearMonth) -> Unit,
    moveMonth: (YearMonth) -> Unit,
    moveAddExercise: () -> Unit,
    removeExercise: (Exercise) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(state.currentMonth) {
            selectDay(null)
            fetchData(state.currentMonth)
        }

        // Draw light content on dark background.
        CompositionLocalProvider(LocalContentColor provides darkColorScheme().onSurface) {
            SimpleCalendarTitle(
                modifier = Modifier
                    .background(toolbarColor)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                currentMonth = state.currentMonth,
                goToPrevious = { moveMonth(state.currentMonth.minusMonths(1)) },
                goToNext = { moveMonth(state.currentMonth.plusMonths(1)) },
            )

            CalendarGrid(
                year = state.currentMonth.year,
                month = state.currentMonth.month.value,
                exerciseColors = convertToMonthlyColorList(state.currentMonth, state.exerciseEntityMonthInfo)
            )
//            HorizontalCalendar(
//                modifier = Modifier.wrapContentWidth(),
//                state = calendarState,
//                dayContent = { day ->
//                    CompositionLocalProvider(LocalRippleTheme provides Example3RippleTheme) {
//                        val colors = if (day.position == DayPosition.MonthDate) {
//                            state.exerciseEntityMonthInfo[day.date].orEmpty().map { Color(it.color) }
//                        } else {
//                            emptyList()
//                        }
//                        Day(
//                            day = day,
//                            isSelected = state.selection == day,
//                            colors = colors,
//                        ) { clicked ->
//                            selectDay(clicked)
//                        }
//                    }
//                },
//                monthHeader = {
//                    MonthHeader(
//                        modifier = Modifier.padding(vertical = 8.dp),
//                        daysOfWeek = daysOfWeek(),
//                    )
//                },
//            )
            HorizontalDivider(color = pageBackgroundColor)
            Column(modifier = Modifier.fillMaxWidth()) {
                state.exerciseEntityMonthInfo[state.selection]?.let {
                    it.forEach { exercise ->
                        ExerciseInformation(
                            exercise = exercise,
                            removeExercise = removeExercise,
                        )
                    }
                }
            }
        }
        if (state.selection != null) {
            IconButton(
                onClick = { moveAddExercise() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_circle),
                    contentDescription = "add new Exercise",
                    tint = Color.White
                )
            }
        }
    }
}