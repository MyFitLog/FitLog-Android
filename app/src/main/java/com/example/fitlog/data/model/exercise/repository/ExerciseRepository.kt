package com.example.fitlog.data.model.exercise.repository

import com.example.fitlog.data.model.exercise.dto.Exercise
import com.example.fitlog.data.model.exercise.service.ExerciseService
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ExerciseRepository(
    private val exerciseService: ExerciseService
) {
    suspend fun getExerciseNames(): List<String> = exerciseService.getExerciseNames()
    suspend fun addExercise(exercise: Exercise) = exerciseService.addExercise(exercise)
    suspend fun deleteExercise(id: Long) = exerciseService.deleteExerciseById(id)
    suspend fun updateExercise(exercise: Exercise) = exerciseService.updateExercise(exercise)

    suspend fun getExerciseByDate(yearMonth: YearMonth): Map<LocalDate, List<Exercise>> {
        val startDate = "$yearMonth-01"
        val endDate = "$yearMonth-31"
        return exerciseService.getExerciseByDate(startDate, endDate).groupBy(
            keySelector = { LocalDate.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) },
            valueTransform = { it }
        )
    }
}