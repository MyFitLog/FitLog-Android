package com.example.fitlog.di

import androidx.room.Room
import com.example.fitlog.data.room.FitLogDatabase
import com.example.fitlog.data.room.exercise.ExerciseRepository
import com.example.fitlog.data.room.exercise.ExerciseRepositoryImpl
import com.example.fitlog.ui.add.AddExerciseViewModel
import com.example.fitlog.ui.calendar.CalendarViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

}

val viewModelModule = module {
    viewModelOf(::AddExerciseViewModel)
    viewModelOf(::CalendarViewModel)
}

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), FitLogDatabase::class.java, "fitlog_database")
            .build()
    }
    single { get<FitLogDatabase>().exerciseDao() }
    single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
}

val appModules = listOf(
    appModule,
    viewModelModule,
    roomModule
)