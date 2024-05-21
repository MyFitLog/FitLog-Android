package com.example.fitlog.ui.add.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitlog.R
import com.example.fitlog.ui.add.AddExerciseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseScreen(
    viewModel: AddExerciseViewModel
) {
    val state by viewModel.container.stateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Box(modifier = Modifier.weight(4f)) {
                OutlinedTextField(
                    value = state.exerciseName,
                    onValueChange = { viewModel.changeExerciseName(it) },
                    label = { Text(text = "운동 종목") }
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = "${state.numOfSet}회",
                textAlign = TextAlign.Center
            )
        }
        LazyColumn {
            items(state.numOfSet) { index ->
                SetInfoScreen(
                    index = index,
                    weight = state.setInfo[index].weight,
                    reps = state.setInfo[index].reps,
                    changeWeight = { idx, weight -> viewModel.changeWeight(idx, weight) },
                    changeReps = { idx, reps -> viewModel.changeReps(idx, reps) },
                    removeSetInfo = { idx -> viewModel.removeSetInfo(idx) }
                )
            }
        }
        IconButton(onClick = { viewModel.changeNumOfSet(state.numOfSet + 1) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_circle),
                contentDescription = "add setInfo"
            )
        }
    }
}