package com.example.fitlog.ui.calendar.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.fitlog.common.displayText
import com.example.fitlog.common.getFirstDayAndDaysInMonth
import com.example.fitlog.common.getPreviousLastDate
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarGrid(
    year: Int,
    month: Int,
    exerciseColors: List<List<Color>>,
    selection: LocalDate?,
    selectDay: (LocalDate?) -> Unit,
) {
    val (firstDayOfMonth, daysInMonth) = getFirstDayAndDaysInMonth(year, month)
    val previousLastDate = getPreviousLastDate(year, month)

    val days = mutableListOf<CalendarDayDate>()

    var curYear = year
    var curMonth = month
    if (curMonth - 1 < 1) {
        curYear -= 1
        curMonth = 12
    } else {
        curMonth -= 1
    }

    for (i in previousLastDate - (firstDayOfMonth - 1)..previousLastDate) {
        days.add(CalendarDayDate(LocalDate.of(curYear, curMonth, i), Color.Gray, emptyList()))
    }
    for (i in 1..daysInMonth) {
        days.add(CalendarDayDate(LocalDate.of(year, month, i), Color.White, exerciseColors[i - 1]))
    }
    if (month + 1 > 12) {
        curMonth = 1
        curYear = year + 1
    } else {
        curMonth = month + 1
        curYear = year
    }
    var curDay = 1
    while (days.size < 42) {
        days.add(CalendarDayDate(LocalDate.of(curYear, curMonth, curDay++), Color.Gray, emptyList()))
    }

    Column {
        // 달력 해더 (일 ~ 토)
        Row(Modifier.fillMaxWidth()) {
            for (dayOfWeek in DayOfWeek.values().toList().let { it.drop(6) + it.take(6) }) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.White,
                    text = dayOfWeek.displayText(uppercase = true),
                    fontWeight = FontWeight.Light,
                )
            }
        }

        for (week in 0 until 6) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOfWeek in 0 until 7) {
                    val dayDate = days[week * 7 + dayOfWeek]
                    CalendarDay(
                        modifier = Modifier.weight(1f),
                        isSelected = dayDate.localDate == selection,
                        isClickable = dayDate.color == Color.White,
                        localDate = dayDate.localDate,
                        dayColor = dayDate.color,
                        exerciseColors = dayDate.exerciseColors,
                        selectDay = selectDay
                    )
                }
            }
        }
    }
}

data class CalendarDayDate(val localDate: LocalDate, val color: Color, val exerciseColors: List<Color>)