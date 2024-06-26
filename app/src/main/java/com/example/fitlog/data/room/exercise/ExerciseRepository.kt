package com.example.fitlog.data.room.exercise

import java.time.LocalDate
import java.time.YearMonth

interface ExerciseRepository {
    suspend fun getExercisesByDate(yearMonth: YearMonth): Map<LocalDate, List<ExerciseWithSetInfo>>

    suspend fun insertExercise(exerciseEntity: ExerciseEntity, listOfSet: List<SetEntity>)

    suspend fun getExerciseNames(): List<String>

    suspend fun removeExercise(exerciseEntity: ExerciseEntity)
}