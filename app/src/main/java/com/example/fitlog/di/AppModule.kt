package com.example.fitlog.di

import com.example.fitlog.BuildConfig.BASE_URL
import com.example.fitlog.data.model.exercise.repository.ExerciseRepository
import com.example.fitlog.data.model.exercise.service.ExerciseService
import com.example.fitlog.ui.add.AddExerciseViewModel
import com.example.fitlog.ui.calendar.CalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModelOf(::AddExerciseViewModel)
    viewModelOf(::CalendarViewModel)
}

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(ExerciseService::class.java) }
    single { ExerciseRepository(get()) }
}

val appModules = listOf(
    viewModelModule,
    dataModule
)