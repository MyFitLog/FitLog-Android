package com.example.fitlog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.fitlog.data.model.exercise.entity.ExerciseEntity
import com.example.fitlog.data.model.exercise.entity.ExerciseWithSetInfo
import com.example.fitlog.data.model.exercise.entity.SetEntity
import com.example.fitlog.data.model.exercise.repository.ExerciseRepository
import com.example.fitlog.ui.calendar.CalendarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.YearMonth
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarViewModelTest : KoinTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository: ExerciseRepository = mock()
    private lateinit var viewModel: CalendarViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
//        viewModel = CalendarViewModel(mockRepository)
    }

    @Test
    fun `fetchData should update state with exercise data`() = runTest {
        val yearMonth = YearMonth.now()

        val mockExerciseData = mapOf(
            LocalDate.now() to listOf(
                ExerciseWithSetInfo(
                    ExerciseEntity(0, "스쿼트", 3, 0, "2024-11-16"),
                    listOf(
                        SetEntity(0, 0, 0, "40", 10),
                        SetEntity(0, 0, 0, "60", 10),
                        SetEntity(0, 0, 0, "80", 10),
                    )
                )
            )
        )
        whenever(mockRepository.getExercisesByDate(yearMonth)).thenReturn(mockExerciseData)

        viewModel = CalendarViewModel(mockRepository)
        viewModel.fetchData(yearMonth)

        advanceUntilIdle()

        viewModel.container.stateFlow.test {
            val state = awaitItem()
            assertEquals(mockExerciseData, state.exerciseEntityMonthInfo)
        }

        verify(mockRepository, times(2)).getExercisesByDate(yearMonth)
    }
}