package com.example.fitlog.data.model.exercise.service

import com.example.fitlog.data.model.exercise.dto.Exercise
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExerciseService {
    @GET("api/exercise")
    suspend fun getExerciseNames(): List<String>

    @POST("api/exercise")
    suspend fun addExercise(@Body exercise: Exercise)

    @DELETE("api/exercise/{id}")
    suspend fun deleteExerciseById(
        @Path("id") id: Long
    )

    @PUT("api/exercise")
    suspend fun updateExercise(exercise: Exercise)

    @GET("api/exercise/{startDate}/{endDate}")
    suspend fun getExerciseByDate(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): List<Exercise>
}