package com.example.fitlog.ui.calendar.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlog.common.Exercise
import com.example.fitlog.common.SetInfo
import com.example.fitlog.ui.theme.ItemBackgroundColor
import com.example.fitlog.ui.theme.PageBackgroundColor

private val itemBackgroundColor = ItemBackgroundColor
private val pageBackgroundColor = PageBackgroundColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ExerciseInformation(exercise: Exercise) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillParentMaxWidth()
            .height(IntrinsicSize.Max)
            .combinedClickable(
                onClick = { expanded = !expanded },
                onLongClick = {

                }
            ),
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
                fontSize = 15.sp,
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
                fontSize = 15.sp,
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