package com.example.fitlog.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.common.Exercise
import com.example.fitlog.data.room.exercise.ExerciseRepository
import com.kizitonwose.calendar.core.CalendarDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.YearMonth

class CalendarViewModel(
    private val exerciseRepository: ExerciseRepository
) : ContainerHost<CalendarState, CalendarSideEffect>, ViewModel() {
    override val container = container<CalendarState, CalendarSideEffect>(CalendarState())

    init {
        fetchData(YearMonth.now())
    }

    fun selectDay(day: CalendarDay?) = intent {
        val curSelection = state.selection
        val date = day?.date
        val newSelectedInfo: List<Exercise> =
            if (date == null) emptyList()
            else state.exerciseMonthInfo[date].orEmpty()
        val selection = if (curSelection == day) null else day

        reduce {
            state.copy(selection = selection, exercisesInSelection = newSelectedInfo)
        }
    }

    fun moveToAddExercise() = intent {
        postSideEffect(CalendarSideEffect.NavigateToAddExercise(date = state.selection?.date.toString()))
    }

    fun fetchData(yearMonth: YearMonth) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseInSelectedData = exerciseRepository.getExercisesByDate(yearMonth)
            reduce {
                state.copy(exerciseMonthInfo = exerciseInSelectedData)
            }
        }
    }
}