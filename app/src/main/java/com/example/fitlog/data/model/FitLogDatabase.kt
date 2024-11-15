package com.example.fitlog.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitlog.data.model.exercise.entity.ExerciseEntity
import com.example.fitlog.data.model.exercise.entity.ExerciseNameEntity
import com.example.fitlog.data.model.exercise.entity.SetEntity
import com.example.fitlog.data.model.exercise.service.ExerciseDao

@Database(
    entities = [ExerciseEntity::class, SetEntity::class, ExerciseNameEntity::class],
    version = 3,
    exportSchema = false
)
abstract class FitLogDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}