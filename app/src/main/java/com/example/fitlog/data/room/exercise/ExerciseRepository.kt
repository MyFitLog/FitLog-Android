package com.example.fitlog.data.room.exercise

import com.example.fitlog.common.SetInfo

interface ExerciseRepository {
    suspend fun getExerciseByDate(date: String)

    suspend fun insertExercise(
        date: String,
        exerciseName: String,
        numOfSet: Int,
        sets: List<SetInfo>,
        color: Int,
    )
}