package com.example.fitlog.ui.calendar.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlog.R
import com.example.fitlog.data.model.exercise.dto.Exercise
import com.example.fitlog.data.model.exercise.dto.SetInfo
import com.example.fitlog.ui.theme.EditGreen
import com.example.fitlog.ui.theme.ItemBackgroundColor
import com.example.fitlog.ui.theme.PageBackgroundColor
import com.example.fitlog.ui.theme.RemoveRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseInformation(
    exercise: Exercise,
    removeExercise: (Exercise) -> Unit,
) {
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.Settled -> false
                SwipeToDismissBoxValue.StartToEnd -> {
                    // 수정
                    false
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    // 삭제
                    removeExercise(exercise)
                    false
                }
            }
        }
    )

    var expanded by remember { mutableStateOf(false) }
    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            val painter = when (state.targetValue) {
                SwipeToDismissBoxValue.Settled -> painterResource(id = R.drawable.ic_circle)
                SwipeToDismissBoxValue.EndToStart -> painterResource(id = R.drawable.ic_delete)
                SwipeToDismissBoxValue.StartToEnd -> painterResource(id = R.drawable.ic_edit)
            }
            val backgroundColor by animateColorAsState(
                when (state.targetValue) {
                    SwipeToDismissBoxValue.Settled -> ItemBackgroundColor.copy(alpha = .5f)
                    SwipeToDismissBoxValue.EndToStart -> RemoveRed
                    SwipeToDismissBoxValue.StartToEnd -> EditGreen
                }, label = ""
            )
            val align = when (state.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                SwipeToDismissBoxValue.Settled -> Alignment.Center
            }
            val scale by animateFloatAsState(
                when (state.targetValue == SwipeToDismissBoxValue.Settled) {
                    true -> .8f
                    else -> 1.3f
                }, label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor),
                contentAlignment = align
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .scale(scale),
//                        .minimumInteractiveComponentSize(),
                    painter = painter,
                    contentDescription = "dismiss"
                )
            }
        }
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .background(Color.Black)
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(exercise.color))
//                .fillParentMaxWidth(1 / 18f)
                    .aspectRatio(1 / 3f)
            )
            Box(
                modifier = Modifier
                    .background(color = ItemBackgroundColor)
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
                    .background(color = ItemBackgroundColor)
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

    }
    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(tween(400)),
        exit = shrinkVertically(tween(400))
    ) {
        Column {
            exercise.setInfos.mapIndexed { index, exerciseSet ->
                ExerciseSetInformation(
                    setInfo = exerciseSet,
                    setNum = index + 1
                )
            }
        }
    }
    HorizontalDivider(thickness = 2.dp, color = PageBackgroundColor)
}

@Composable
fun ExerciseSetInformation(
    setInfo: SetInfo,
    setNum: Int
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
//            .height(40.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = ItemBackgroundColor)
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
                .background(color = ItemBackgroundColor)
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
                .background(color = ItemBackgroundColor)
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