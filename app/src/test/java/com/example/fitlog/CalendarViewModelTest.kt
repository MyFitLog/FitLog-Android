package com.example.fitlog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fitlog.data.model.exercise.entity.ExerciseEntity
import com.example.fitlog.data.model.exercise.entity.ExerciseWithSetInfo
import com.example.fitlog.data.model.exercise.entity.SetEntity
import com.example.fitlog.data.model.exercise.repository.ExerciseRepository
import com.example.fitlog.ui.calendar.CalendarSideEffect
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.orbitmvi.orbit.test.test
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarViewModelTest : KoinTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository: ExerciseRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
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
                ),
            )
        )

        whenever(mockRepository.getExercisesByDate(yearMonth)).thenReturn(mockExerciseData)

        CalendarViewModel(mockRepository).test(this) {
            runOnCreate()
            expectInitialState()
            containerHost.fetchData(yearMonth)
            advanceUntilIdle()
            expectState { copy(exerciseEntityMonthInfo = mockExerciseData) }
            verify(mockRepository).getExercisesByDate(yearMonth)
        }

    }

    @Test
    fun `selectDay should toggle selected day`() = runTest {
        val selectedDay = LocalDate.now()

        CalendarViewModel(mockRepository).test(this) {
            runOnCreate()
            expectInitialState()
            containerHost.selectDay(selectedDay)
            expectState { copy(selection = selectedDay) }
            containerHost.selectDay(selectedDay)
            expectState { copy(selection = null) }
        }
    }

    @Test
    fun `moveMonth should update state with currentMonth`() = runTest {
        val nextMonth = YearMonth.now().plusMonths(1)
        val prevMonth = YearMonth.now().minusMonths(1)

        CalendarViewModel(mockRepository).test(this) {
            runOnCreate()
            expectInitialState()
            containerHost.moveMonth(nextMonth)
            expectState { copy(currentMonth = nextMonth) }
            containerHost.moveMonth(prevMonth)
            expectState { copy(currentMonth = prevMonth) }
        }
    }

    @Test
    fun `moveToAddExercise should occur postEffect navigate to AddExercise`() = runTest {
        val selectedDay = LocalDate.now()

        CalendarViewModel(mockRepository).test(this) {
            runOnCreate()
            expectInitialState()
            containerHost.selectDay(selectedDay)
            skipItems(1)
            containerHost.moveToAddExercise()
            expectSideEffect(CalendarSideEffect.NavigateToAddExercise(date = selectedDay.toString()))
        }
    }

    @Test
    fun `deleteExercise should call removeExercise function in repository`() = runTest {
        val removedExercise =
            ExerciseEntity(1, "벤치 프레스", 3, 1, LocalDate.now().toString())

        whenever(mockRepository.removeExercise(removedExercise)).thenReturn(Unit)

        CalendarViewModel(mockRepository).test(this) {
            expectInitialState()
            val job = containerHost.deleteExercise(removedExercise)
            job.join()
            verify(mockRepository).removeExercise(removedExercise)
        }
    }
}