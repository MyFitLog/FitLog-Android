package com.example.fitlog.data.room.exercise

interface ExerciseRepository {
    suspend fun getExerciseByDate(date: String)
}