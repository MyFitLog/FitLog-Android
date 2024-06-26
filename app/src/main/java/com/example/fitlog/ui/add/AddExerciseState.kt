package com.example.fitlog.ui.add

import android.graphics.Color
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.fitlog.data.room.exercise.SetEntity

data class AddExerciseState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val datePickerState: DatePickerState,
    val numOfSet: Int = 1,
    val expanded: Boolean = false,
    val setInfo: List<SetEntity> = listOf(SetEntity(exerciseId = 0, order = 0, weight = "", reps = 0)),
    val color: Int = Color.RED,
    val showDialog: Boolean = false,
    val exerciseNameList: List<String> = listOf("선택"),
    val showSelectableList: Boolean = false,
    val selectedIndex: Int = 0,
)