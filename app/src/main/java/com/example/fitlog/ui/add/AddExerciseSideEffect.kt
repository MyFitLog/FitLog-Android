package com.example.fitlog.ui.add

sealed class AddExerciseSideEffect {
    data object navigateToCalendar : AddExerciseSideEffect()
}