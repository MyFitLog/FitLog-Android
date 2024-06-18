package com.example.fitlog.ui.add.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlog.common.toDateString
import com.example.fitlog.ui.add.AddExerciseState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseScreen(
    state: AddExerciseState,
    addSet: () -> Unit,
    changeWeight: (Int, String) -> Unit,
    changeReps: (Int, Int) -> Unit,
    removeSetInfo: (Int) -> Unit,
    changeShowDialog: () -> Unit,
    addExercise: () -> Unit,
    selectListItem: (Int) -> Unit,
    changeShowSelectableList: (Boolean) -> Unit
) {
    if (state.showDialog) {
        DatePickerDialog(
            onDismissRequest = changeShowDialog,
            confirmButton = { },
        ) {
            DatePicker(
                state = state.datePickerState,
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 15.dp,
                vertical = 15.dp
            )
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "운동 추가",
            fontSize = 30.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        ReadonlyTextField(
            value = TextFieldValue(
                state.datePickerState.selectedDateMillis?.toDateString() ?: LocalDate.now().toString()
            ),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            onClick = changeShowDialog,
            label = { Text("Date") }
        )

        Row {
            SelectableList(
                list = state.exerciseNameList,
                modifier = Modifier
                    .weight(3f)
                    .background(Color.White),
                selectedIndex = state.selectedIndex,
                showSelectableList = state.showSelectableList,
                changeShowSelectableList = { changeShowSelectableList(it) },
                onItemClick = { selectListItem(it) }
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = "${state.numOfSet}회",
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        SetInfoList(
            numOfSet = state.numOfSet,
            setInfo = state.setInfo,
            addSet = addSet,
            changeWeight = changeWeight,
            changeReps = changeReps,
            removeSetInfo = removeSetInfo
        )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(     // 운동 정보 추가 버튼
            shape = RectangleShape,
            onClick = addExercise,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "추가")
        }
    }
}

@Composable
fun ReadonlyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick)
        )
    }
}