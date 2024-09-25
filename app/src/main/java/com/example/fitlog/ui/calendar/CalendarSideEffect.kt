package com.example.fitlog.ui.calendar

sealed class CalendarSideEffect {
    data class NavigateToAddExercise(val date: String) : CalendarSideEffect()
}
