package com.example.fitlog.common

import androidx.compose.ui.graphics.Color

data class Exercise(
    val name: String,
    val numOfSets: Int,
    val sets: List<SetInfo>,
    val color: Color
)

data class SetInfo(
    val weight: String,
    val reps: Int,
)