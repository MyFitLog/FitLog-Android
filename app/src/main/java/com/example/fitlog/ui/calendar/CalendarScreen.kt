package com.example.fitlog.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel
) {
    val state by viewModel.container.stateFlow.collectAsState()
    Column {
        Button(onClick = { viewModel.add(1) }) {
            Text(text = "1이 증가 하는 버튼")
        }
        Text(text = "${state.total}")

    }
}
