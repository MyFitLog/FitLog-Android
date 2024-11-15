package com.example.fitlog.data.model.exercise.repository

import com.example.fitlog.data.model.exercise.entity.ExerciseEntity
import com.example.fitlog.data.model.exercise.entity.ExerciseWithSetInfo
import com.example.fitlog.data.model.exercise.entity.SetEntity
import com.example.fitlog.data.model.exercise.service.ExerciseDao
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

interface ExerciseRepository {
    suspend fun getExercisesByDate(yearMonth: YearMonth): Map<LocalDate, List<ExerciseWithSetInfo>>

    suspend fun insertExercise(exerciseEntity: ExerciseEntity, listOfSet: List<SetEntity>)

    suspend fun getExerciseNames(): List<String>

    suspend fun removeExercise(exerciseEntity: ExerciseEntity)
}

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

// Spring Server 용
//class ExerciseRepository(
//    private val exerciseService: ExerciseService
//) {
//    suspend fun getExerciseNames(): List<String> = exerciseService.getExerciseNames()
//    suspend fun addExercise(exercise: Exercise) = exerciseService.addExercise(exercise)
//    suspend fun deleteExercise(id: Long) = exerciseService.deleteExerciseById(id)
//    suspend fun updateExercise(exercise: Exercise) = exerciseService.updateExercise(exercise)
//
//    suspend fun getExerciseByDate(yearMonth: YearMonth): Map<LocalDate, List<Exercise>> {
//        val startDate = "$yearMonth-01"
//        val endDate = "$yearMonth-31"
//        return exerciseService.getExerciseByDate(startDate, endDate).groupBy(
//            keySelector = { LocalDate.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) },
//            valueTransform = { it }
//        )
//    }
//}