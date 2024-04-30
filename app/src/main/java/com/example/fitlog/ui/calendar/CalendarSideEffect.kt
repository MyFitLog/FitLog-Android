package com.example.fitlog.ui.calendar

sealed class CalendarSideEffect {
    data class Toast(val text: String) : CalendarSideEffect()
}
