package com.example.fitlog.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.fitlog.data.model.exercise.entity.ExerciseEntity
import com.example.fitlog.data.model.exercise.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import java.time.YearMonth

class CalendarViewModel(
    private val exerciseRepository: ExerciseRepository
) : ContainerHost<CalendarState, CalendarSideEffect>, ViewModel() {
    override val container = container<CalendarState, CalendarSideEffect>(CalendarState())

    fun selectDay(day: LocalDate?) = intent {
        val curSelection = state.selection
        val selection = if (curSelection == day) null else day

        reduce {
            state.copy(selection = selection)
        }
    }

    fun moveMonth(nextYearMonth: YearMonth) = intent {
        reduce {
            state.copy(currentMonth = nextYearMonth)
        }
    }

    fun moveToAddExercise() = intent {
        postSideEffect(CalendarSideEffect.NavigateToAddExercise(date = state.selection.toString()))
    }

    fun fetchData(yearMonth: YearMonth) = intent {
        val exerciseInSelectedData = withContext(Dispatchers.IO) {
            exerciseRepository.getExercisesByDate(yearMonth)
        }
        reduce {
            state.copy(exerciseEntityMonthInfo = exerciseInSelectedData)
        }
    }

    fun deleteExercise(exercise: ExerciseEntity) = intent {
        withContext(Dispatchers.IO) {
            exerciseRepository.removeExercise(exercise)
        }
    }

    fun deleteExerciseAndFetchData(exercise: ExerciseEntity, yearMonth: YearMonth) = intent {
        deleteExercise(exercise)
        fetchData(yearMonth)
    }
}