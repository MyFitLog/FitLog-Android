package com.example.fitlog.data.model.exercise.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.fitlog.data.model.exercise.entity.ExerciseEntity
import com.example.fitlog.data.model.exercise.entity.ExerciseNameEntity
import com.example.fitlog.data.model.exercise.entity.ExerciseWithSetInfo
import com.example.fitlog.data.model.exercise.entity.SetEntity

@Dao
interface ExerciseDao {
    @Transaction
    @Query("SELECT * FROM exercise WHERE date >= :startDate AND date <= :endDate")
    suspend fun getExercisesByDate(startDate: String, endDate: String): List<ExerciseWithSetInfo>

    @Query("SELECT * FROM exercise_name")
    suspend fun getExerciseNames(): List<ExerciseNameEntity>

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

// Spring Server 용
//interface ExerciseService {
//    @GET("api/exercise")
//    suspend fun getExerciseNames(): List<String>
//
//    @POST("api/exercise")
//    suspend fun addExercise(@Body exercise: Exercise)
//
//    @DELETE("api/exercise/{id}")
//    suspend fun deleteExerciseById(
//        @Path("id") id: Long
//    )
//
//    @PUT("api/exercise")
//    suspend fun updateExercise(exercise: Exercise)
//
//    @GET("api/exercise/{startDate}/{endDate}")
//    suspend fun getExerciseByDate(
//        @Path("startDate") startDate: String,
//        @Path("endDate") endDate: String
//    ): List<Exercise>
//}