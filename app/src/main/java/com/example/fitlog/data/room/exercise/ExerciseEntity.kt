package com.example.fitlog.data.room.exercise

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val exerciseId: Long = 0,
    val name: String,
    val numOfSets: Int,
    val color: Int,
    val date: String,
)

@Entity(tableName = "set_info")
data class SetEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "set_info_id") val setInfoId: Long = 0,
    val exerciseId: Long = 0,
    val order: Int,
    val weight: String,
    val reps: Int
)

@Entity(tableName = "exercise_name")
data class ExerciseNameEntity(
    @PrimaryKey val name: String
)

data class ExerciseWithSetInfo(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseId"
    )
    val setsInfo: List<SetEntity>
)