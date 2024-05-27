package com.example.fitlog.ui.calendar

import androidx.lifecycle.ViewModel
import com.kizitonwose.calendar.core.CalendarDay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class CalendarViewModel : ContainerHost<CalendarState, CalendarSideEffect>, ViewModel() {
    override val container = container<CalendarState, CalendarSideEffect>(CalendarState())

//    fun add(number: Int) = intent {
//        postSideEffect(CalendarSideEffect.Toast("Adding $number to ${state.total}!"))
//
//        reduce {
//            state.copy(total = state.total + number)
//        }
//    }

    fun selectDay(day: CalendarDay?) = intent {
        val curSelection = state.selection
        reduce {
            if (curSelection == day) state.copy(selection = null)
            else state.copy(selection = day)
        }
    }

    fun moveToAddExercise() = intent {
        postSideEffect(CalendarSideEffect.NavigateToAddExercise)
    }
}