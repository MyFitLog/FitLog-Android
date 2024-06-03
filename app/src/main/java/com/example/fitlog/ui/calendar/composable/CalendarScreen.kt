package com.example.fitlog.ui.calendar.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlog.R
import com.example.fitlog.common.Exercise
import com.example.fitlog.common.SetInfo
import com.example.fitlog.common.displayText
import com.example.fitlog.common.rememberFirstCompletelyVisibleMonth
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
import kotlinx.coroutines.launch
import java.time.DayOfWeek

private val toolbarColor = ToolbarColor
private val itemBackgroundColor = ItemBackgroundColor
private val selectedItemColor = TextGray
private val pageBackgroundColor = PageBackgroundColor
private val inActiveTextColor = TextGrayLight

@Composable
fun CalendarScreen(
    state: CalendarState,
    selectDay: (CalendarDay?) -> Unit,
    moveAddExercise: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

//    StatusBarColorUpdateEffect(toolbarColor)
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
        }

        // Draw light content on dark background.
        CompositionLocalProvider(LocalContentColor provides darkColorScheme().onSurface) {
            SimpleCalendarTitle(
                modifier = Modifier
                    .background(toolbarColor)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(state.currentMonth.previousMonth)
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(state.currentMonth.nextMonth)
                    }
                },
            )
            HorizontalCalendar(
                modifier = Modifier.wrapContentWidth(),
                state = calendarState,
                dayContent = { day ->
                    CompositionLocalProvider(LocalRippleTheme provides Example3RippleTheme) {
                        val colors = if (day.position == DayPosition.MonthDate) {
                            state.exerciseMonthInfo[day.date].orEmpty().map { it.color }
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
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = state.exercisesInSelection) { exercise ->
                    ExerciseInformation(exercise = exercise)
                }
            }
        }
        if (state.selection != null) {
            FloatingActionButton(
                onClick = { moveAddExercise() },
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_circle),
                    contentDescription = "add new Exercise"
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

private val ExerciseInformationFontSize = 15.sp

@Composable
private fun LazyItemScope.ExerciseInformation(exercise: Exercise) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillParentMaxWidth()
            .height(IntrinsicSize.Max)
            .clickable { expanded = !expanded },
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Box(
            modifier = Modifier
                .background(color = exercise.color)
                .fillParentMaxWidth(1 / 18f)
                .aspectRatio(1 / 3f)
        )
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = exercise.name,
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = ExerciseInformationFontSize,
            )
        }
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(.3f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${exercise.numOfSets} 세트",
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = ExerciseInformationFontSize,
            )
        }
    }
    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(tween(400)),
        exit = shrinkVertically(tween(400))
    ) {
        Column {
            exercise.sets.mapIndexed { index, exerciseSet ->
                ExerciseSetInformation(
                    setInfo = exerciseSet,
                    setNum = index + 1
                )
            }
        }
    }
    HorizontalDivider(thickness = 2.dp, color = pageBackgroundColor)
}

@Composable
fun ExerciseSetInformation(
    setInfo: SetInfo,
    setNum: Int
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
    ) {
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$setNum 세트",
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = 15.sp,
            )
        }
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${setInfo.weight} kg",
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = 15.sp,
            )
        }
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${setInfo.reps} 회",
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = 15.sp,
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

