package com.example.fitlog.data.room.exercise

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ExerciseDao {
    @Transaction
    @Query("SELECT * FROM exercise WHERE date = :date")
    suspend fun getExerciseByDate(date: String): ExerciseWithSetInfo

    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)
}