package com.example.fitlog.ui.calendar.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

@Composable
fun CalendarDay(
    day: Int,
    dayColor: Color,
    exerciseColors: List<Color>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(ItemBackgroundColor)
            .border(1.dp, Color.Transparent)
    ) {
        Text(
            text = day.toString(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
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