package com.example.fitlog.ui.calendar.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlog.R
import com.example.fitlog.common.displayText
import com.example.fitlog.common.rememberFirstCompletelyVisibleMonth
import com.example.fitlog.data.model.exercise.dto.Exercise
import com.example.fitlog.ui.calendar.CalendarState
import com.example.fitlog.ui.theme.ItemBackgroundColor
import com.example.fitlog.ui.theme.PageBackgroundColor
import com.example.fitlog.ui.theme.TextGray
import com.example.fitlog.ui.theme.TextGrayLight
import com.example.fitlog.ui.theme.ToolbarColor
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth

private val toolbarColor = ToolbarColor
private val itemBackgroundColor = ItemBackgroundColor
private val selectedItemColor = TextGray
private val pageBackgroundColor = PageBackgroundColor
private val inActiveTextColor = TextGrayLight

@Composable
fun CalendarScreen(
    state: CalendarState,
    selectDay: (CalendarDay?) -> Unit,
    fetchData: (YearMonth) -> Unit,
    moveAddExercise: () -> Unit,
    removeExercise: (Exercise) -> Unit,
//    changeShowAlertDialog: (Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val calendarState = rememberCalendarState(
            startMonth = state.startMonth,
            endMonth = state.endMonth,
            firstVisibleMonth = state.currentMonth,
            firstDayOfWeek = state.firstDayOfWeek,
            outDateStyle = OutDateStyle.EndOfGrid,
        )
        val visibleMonth = rememberFirstCompletelyVisibleMonth(calendarState)
        LaunchedEffect(visibleMonth) {
            selectDay(null)
            fetchData(visibleMonth.yearMonth)
        }

        // Draw light content on dark background.
        CompositionLocalProvider(LocalContentColor provides darkColorScheme().onSurface) {
            SimpleCalendarTitle(
                modifier = Modifier
                    .background(toolbarColor)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch(Dispatchers.Main) {
                        calendarState.animateScrollToMonth(visibleMonth.yearMonth.previousMonth)
                    }
                },
                goToNext = {
                    coroutineScope.launch(Dispatchers.Main) {
                        calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.nextMonth)
                    }
                },
            )
            HorizontalCalendar(
                modifier = Modifier.wrapContentWidth(),
                state = calendarState,
                dayContent = { day ->
                    CompositionLocalProvider(LocalRippleTheme provides Example3RippleTheme) {
                        val colors = if (day.position == DayPosition.MonthDate) {
                            state.exerciseEntityMonthInfo[day.date].orEmpty().map { Color(it.color) }
                        } else {
                            emptyList()
                        }
                        Day(
                            day = day,
                            isSelected = state.selection == day,
                            colors = colors,
                        ) { clicked ->
                            selectDay(clicked)
                        }
                    }
                },
                monthHeader = {
                    MonthHeader(
                        modifier = Modifier.padding(vertical = 8.dp),
                        daysOfWeek = daysOfWeek(),
                    )
                },
            )
            HorizontalDivider(color = pageBackgroundColor)
            Column(modifier = Modifier.fillMaxWidth()) {
                state.exerciseEntityMonthInfo[state.selection?.date]?.let {
                    it.forEach { exercise ->
                        ExerciseInformation(
                            exercise = exercise,
                            removeExercise = removeExercise,
//                        showAlertDialog = state.showAlertDialog,
//                        changeShowAlertDialog = changeShowAlertDialog
                        )
                    }
                }
//                state.exercisesInSelection.forEach { exercise ->
//                    ExerciseInformation(
//                        exerciseWithSetInfo = exercise,
//                        removeExercise = removeExercise,
////                        showAlertDialog = state.showAlertDialog,
////                        changeShowAlertDialog = changeShowAlertDialog
//                    )
//                }
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

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    colors: List<Color> = emptyList(),
    onClick: (CalendarDay) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) selectedItemColor else Color.Transparent,
            )
            .padding(1.dp)
            .background(color = itemBackgroundColor)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> inActiveTextColor
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 4.dp),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 12.sp,
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            for (color in colors) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(color),
                )
            }
        }
    }
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
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
}


// The default dark them ripple is too bright so we tone it down.
private object Example3RippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(Color.Gray, lightTheme = false)

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(Color.Gray, lightTheme = false)
}

