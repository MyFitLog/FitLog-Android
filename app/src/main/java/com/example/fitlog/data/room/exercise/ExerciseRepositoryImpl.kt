package com.example.fitlog.data.room.exercise

import androidx.compose.ui.graphics.Color
import com.example.fitlog.common.Exercise
import com.example.fitlog.common.SetInfo
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ExerciseRepositoryImpl(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    override suspend fun getExercisesByDate(yearMonth: YearMonth): Map<LocalDate, List<Exercise>> {
        val startDate = "$yearMonth-01"
        val endDate = "$yearMonth-31"
        val exerciseEntities = exerciseDao.getExercisesByDate(startDate, endDate)
        val exerciseInSelectedData = exerciseEntities.groupBy(
            keySelector = { LocalDate.parse(it.exercise.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) },
            valueTransform = {
                Exercise(
                    name = it.exercise.name,
                    numOfSets = it.exercise.numOfSets,
                    sets = it.setsInfo.map { setEntity ->
                        SetInfo(
                            weight = setEntity.weight,
                            reps = setEntity.reps
                        )
                    },
                    color = Color(it.exercise.color)
                )
            }
        )
        return exerciseInSelectedData
    }

    override suspend fun insertExercise(
        date: String,
        exerciseName: String,
        numOfSet: Int,
        sets: List<SetInfo>,
        color: Int
    ) {
        val exerciseEntity = ExerciseEntity(
            name = exerciseName,
            numOfSets = numOfSet,
            color = color,
            date = date
        )
        val exerciseId: Long = exerciseDao.insertExercise(exerciseEntity)
        val setEntityList = sets.mapIndexed { idx, set ->
            SetEntity(
                exerciseId = exerciseId,
                order = idx + 1,
                weight = set.weight,
                reps = set.reps
            )
        }
        exerciseDao.insertSets(setEntityList)
    }
}