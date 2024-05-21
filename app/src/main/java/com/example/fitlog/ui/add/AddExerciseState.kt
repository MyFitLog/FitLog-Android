package com.example.fitlog.ui.add

import com.example.fitlog.common.SetInfo

data class AddExerciseState(
    val exerciseName: String = "",
    val numOfSet: Int = 1,
    val expanded: Boolean = false,
    val setInfo: List<SetInfo> = listOf(SetInfo("", 0))
)