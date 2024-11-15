package com.example.fitlog.common

import androidx.compose.ui.graphics.Color
import com.example.fitlog.data.model.exercise.entity.ExerciseWithSetInfo
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale


fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.KOREA)
}

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.KOREA).let { value ->
        if (uppercase) value.uppercase(Locale.KOREA) else value
    }
}

fun isDoubleFormat(str: String): Boolean {
    val weightRegex = """^(1000(\.0+)?|0|[0-9]?[0-9]?[0-9](\.\d+)?)$""".toRegex()
    return weightRegex.matches(str)
}

fun Long.toDateString(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(this))

fun getFirstDayAndDaysInMonth(year: Int, month: Int): Pair<Int, Int> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val dayOfWeek = firstDayOfMonth.dayOfWeek
    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()

    return Pair(dayOfWeek.value, daysInMonth)
}

fun getPreviousLastDate(year: Int, month: Int) = YearMonth.of(year, month).minusMonths(1).lengthOfMonth()

fun convertToMonthlyColorList(
    yearMonth: YearMonth,
    exerciseEntityMonthInfo: Map<LocalDate, List<ExerciseWithSetInfo>>
): List<List<Color>> {
//    val TAG = "UTILS - convertToMonthlyColorList"
    val daysInMonth = yearMonth.lengthOfMonth()
    return (1..daysInMonth).map { day ->
        val date = LocalDate.of(yearMonth.year, yearMonth.monthValue, day)
        exerciseEntityMonthInfo[date]?.map { Color(it.exercise.color) } ?: emptyList()
    }
}

