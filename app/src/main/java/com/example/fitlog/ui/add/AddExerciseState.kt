package com.example.fitlog.ui.add

import android.graphics.Color
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.fitlog.common.SetInfo

data class AddExerciseState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val datePickerState: DatePickerState,
    val exerciseName: String = "",
    val numOfSet: Int = 1,
    val expanded: Boolean = false,
    val setInfo: List<SetInfo> = listOf(SetInfo("", 0)),
    val color: Int = Color.RED,
    val showDialog: Boolean = false,
    val exerciseNameList: List<String> = listOf("선택"),
    val showSelectableList: Boolean = false,
    val selectedIndex: Int = 0,
)