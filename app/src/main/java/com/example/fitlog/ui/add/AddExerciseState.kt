package com.example.fitlog.ui.add

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.fitlog.common.SetInfo
import java.time.LocalDate
import java.util.Locale

data class AddExerciseState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val exerciseName: String = "",
    val numOfSet: Int = 1,
    val expanded: Boolean = false,
    val setInfo: List<SetInfo> = listOf(SetInfo("", 0)),
    val showDialog: Boolean = false,
    val curDate: LocalDate = LocalDate.now(),
    val datePickerState: DatePickerState = DatePickerState(Locale.KOREA),
)