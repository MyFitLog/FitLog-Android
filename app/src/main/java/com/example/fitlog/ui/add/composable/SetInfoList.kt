package com.example.fitlog.ui.add.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitlog.R
import com.example.fitlog.common.MyTextField
import com.example.fitlog.common.isDoubleFormat
import com.example.fitlog.data.room.exercise.SetEntity
import kotlinx.coroutines.launch

@Composable
fun SetInfoList(
    numOfSet: Int,
    setInfo: List<SetEntity>,
    addSet: () -> Unit,
    changeWeight: (Int, String) -> Unit,
    changeReps: (Int, Int) -> Unit,
    removeSetInfo: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(numOfSet) {
        coroutineScope.launch {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp) // 적절한 패딩 추가
    ) {
        setInfo.forEachIndexed { index, setInfo ->
            SetInfoListItem(
                index = index,
                weight = setInfo.weight,
                reps = setInfo.reps,
                changeWeight = { idx, weight -> changeWeight(idx, weight) },
                changeReps = { idx, reps -> changeReps(idx, reps) },
                removeSetInfo = { idx -> removeSetInfo(idx) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // 버튼과 항목 사이에 간격 추가
        IconButton(
            onClick = addSet,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_circle),
                contentDescription = "Add Set"
            )
        }
    }
}

@Composable
fun SetInfoListItem(
    index: Int,
    weight: String,
    reps: Int,
    changeWeight: (Int, String) -> Unit,
    changeReps: (Int, Int) -> Unit,
    removeSetInfo: (Int) -> Unit,
) {
    val isWeightError by derivedStateOf { !isDoubleFormat(weight) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${index + 1} 세트")
        MyTextField(
            modifier = Modifier.weight(1f),
            value = weight,
            onValueChange = { changeWeight(index, it) },
            label = { Text(text = "무게") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isWeightError
        )
        Text(text = "kg")
        MyTextField(
            modifier = Modifier.weight(1f),
            value = "$reps",
            onValueChange = { changeReps(index, if (it == "") 0 else it.toInt()) },
            label = { Text(text = "횟수") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "회")
        IconButton(onClick = { removeSetInfo(index) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_remove_circle),
                contentDescription = "remove setInfo",
                tint = Color.Red
            )
        }
    }
}