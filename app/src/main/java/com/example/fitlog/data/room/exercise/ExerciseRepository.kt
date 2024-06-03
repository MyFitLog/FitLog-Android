package com.example.fitlog.data.room.exercise

import com.example.fitlog.common.Exercise
import com.example.fitlog.common.SetInfo
import java.time.LocalDate
import java.time.YearMonth

interface ExerciseRepository {
    suspend fun getExercisesByDate(yearMonth: YearMonth): Map<LocalDate, List<Exercise>>

    suspend fun insertExercise(
        date: String,
        exerciseName: String,
        numOfSet: Int,
        sets: List<SetInfo>,
        color: Int,
    )
}