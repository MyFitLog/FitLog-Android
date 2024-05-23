package com.example.fitlog.di

import com.example.fitlog.ui.add.AddExerciseViewModel
import com.example.fitlog.ui.calendar.CalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

}

val viewModelModule = module {
    viewModelOf(::AddExerciseViewModel)
    viewModelOf(::CalendarViewModel)
}

val appModules = listOf(
    appModule,
    viewModelModule
)