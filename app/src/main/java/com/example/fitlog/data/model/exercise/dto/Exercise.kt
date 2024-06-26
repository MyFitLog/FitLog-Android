package com.example.fitlog.data.model.exercise.dto

data class Exercise(
    val id: Long? = null,
    val name: String,
    val numOfSets: Int,
    val color: Int,
    val date: String,
    val setInfo: List<SetInfo>
)

data class SetInfo(
    val id: Long? = null,
    val weight: String,
    val reps: Int
)