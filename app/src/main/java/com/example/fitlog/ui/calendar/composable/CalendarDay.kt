package com.example.fitlog.ui.calendar.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlog.ui.theme.ItemBackgroundColor
import java.time.LocalDate

val selectedBorderColor = Color.White
val unSelectedBorderColor = Color.Black

@Composable
fun CalendarDay(
    modifier: Modifier,
    isSelected: Boolean,
    isClickable: Boolean,
    localDate: LocalDate,
    dayColor: Color,
    exerciseColors: List<Color>,
    selectDay: (LocalDate?) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(ItemBackgroundColor)
            .border(1.dp, if (isSelected) selectedBorderColor else unSelectedBorderColor)
            .let {
                if (isClickable) it.clickable { selectDay(localDate) }
                else it
            }
    ) {
        Text(
            text = localDate.dayOfMonth.toString(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 4.dp),
            color = dayColor,
            fontSize = 12.sp
        )
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            exerciseColors.forEach { exerciseColor ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(exerciseColor)
                )
            }
        }
    }
}