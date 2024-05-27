package com.example.fitlog.ui.calendar

sealed class CalendarSideEffect {
    data object NavigateToAddExercise : CalendarSideEffect()
}
