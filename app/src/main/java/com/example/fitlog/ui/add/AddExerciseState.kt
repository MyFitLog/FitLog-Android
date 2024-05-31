package com.example.fitlog.ui.add

import android.graphics.Color
import com.example.fitlog.common.SetInfo
import java.time.LocalDate

data class AddExerciseState constructor(
    val exerciseName: String = "",
    val numOfSet: Int = 1,
    val expanded: Boolean = false,
    val setInfo: List<SetInfo> = listOf(SetInfo("", 0)),
    val color: Int = Color.RED,
    val showDialog: Boolean = false,
    val curDate: LocalDate = LocalDate.now(),
)