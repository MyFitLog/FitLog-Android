package com.example.fitlog.common

import androidx.compose.ui.graphics.Color

data class Exercise(
    val name: String,
    val numOfSets: Int,
    val sets: List<ExerciseSet>,
    val color: Color
)

data class ExerciseSet(
    val exercise: String,
    val weight: Int,
    val reps: Int,
)