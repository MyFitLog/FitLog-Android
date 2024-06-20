package com.example.fitlog.data.room.exercise

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ExerciseRepositoryImpl(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    override suspend fun getExercisesByDate(yearMonth: YearMonth): Map<LocalDate, List<ExerciseWithSetInfo>> {
        val startDate = "$yearMonth-01"
        val endDate = "$yearMonth-31"
        val exerciseEntities = exerciseDao.getExercisesByDate(startDate, endDate)
        return exerciseEntities.groupBy(
            keySelector = { LocalDate.parse(it.exercise.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) },
            valueTransform = { it }
        )
    }

    override suspend fun insertExercise(
        exerciseEntity: ExerciseEntity,
        listOfSet: List<SetEntity>
    ) {
        val exerciseId: Long = exerciseDao.insertExercise(exerciseEntity)
        val setEntityList = listOfSet.mapIndexed { idx, set ->
            SetEntity(
                exerciseId = exerciseId,
                order = idx + 1,
                weight = set.weight,
                reps = set.reps
            )
        }
        exerciseDao.insertSets(setEntityList)
    }

    override suspend fun getExerciseNames(): List<String> = exerciseDao.getExerciseNames().map { it.name }

    override suspend fun removeExercise(exerciseEntity: ExerciseEntity) {
        exerciseDao.deleteExercise(exerciseEntity)
    }
}