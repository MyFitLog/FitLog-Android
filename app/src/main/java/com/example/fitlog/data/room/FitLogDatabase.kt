package com.example.fitlog.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitlog.data.room.exercise.ExerciseDao
import com.example.fitlog.data.room.exercise.ExerciseEntity
import com.example.fitlog.data.room.exercise.SetEntity

@Database(entities = [ExerciseEntity::class, SetEntity::class], version = 1, exportSchema = false)
abstract class FitLogDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}