package com.example.fitlog.data.room.exercise

import com.example.fitlog.common.SetInfo

class ExerciseRepositoryImpl(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    override suspend fun getExerciseByDate(date: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertExercise(
        date: String,
        exerciseName: String,
        numOfSet: Int,
        sets: List<SetInfo>,
        color: Int
    ) {
        val exerciseEntity = ExerciseEntity(
            numOfSets = numOfSet,
            color = color,
            date = date
        )
        val exerciseId: Long = exerciseDao.insertExercise(exerciseEntity)
        val setEntityList = sets.mapIndexed { idx, set ->
            SetEntity(
                exerciseId = exerciseId,
                order = idx + 1,
                weight = set.weight.toFloat(),
                reps = set.reps
            )
        }
        exerciseDao.insertSets(setEntityList)
    }
}