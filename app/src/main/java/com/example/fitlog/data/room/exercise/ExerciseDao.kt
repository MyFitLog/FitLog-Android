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
    @Query("SELECT * FROM exercise WHERE date >= :startDate AND date <= :endDate")
    suspend fun getExercisesByDate(startDate: String, endDate: String): List<ExerciseWithSetInfo>

    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Insert
    suspend fun insertSets(sets: List<SetEntity>)

    @Update
    suspend fun updateSets(sets: List<SetEntity>)
}