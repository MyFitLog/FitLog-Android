package com.example.fitlog.ui.add

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.common.SetInfo
import com.example.fitlog.common.toDateString
import com.example.fitlog.data.room.exercise.ExerciseRepository
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
class AddExerciseViewModel(
    date: String,
    private val exerciseRepository: ExerciseRepository
) : ContainerHost<AddExerciseState, AddExerciseSideEffect>, ViewModel() {
    override val container =
        container<AddExerciseState, AddExerciseSideEffect>(AddExerciseState(
            datePickerState = DatePickerState(
                locale = Locale.KOREA,
                initialSelectedDateMillis = date.let {
                    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }
                    formatter.parse(it)?.time
                        ?: System.currentTimeMillis() // 날짜 파싱 실패 시 현재 시간을 기본값으로 사용
                }
            )
        ))

    fun changeExerciseName(text: String) = intent {
        reduce {
            state.copy(exerciseName = text)
        }
    }

    fun addSet() = intent {
        val curNumOfSet = state.numOfSet
        val newSet =
            if (state.numOfSet == 0) SetInfo()
            else SetInfo(state.setInfo.last().weight, state.setInfo.last().reps)
        val info = state.setInfo + newSet
        reduce {
            state.copy(numOfSet = curNumOfSet + 1, setInfo = info)
        }
    }

    fun removeSetInfo(index: Int) = intent {
        val info = state.setInfo.filterIndexed { idx, _ -> idx != index }
        reduce {
            state.copy(setInfo = info)
        }
    }

    fun changeWeight(index: Int, weight: String) = intent {
        val info = state.setInfo.toMutableList().apply {
            if (index in indices) {
                this[index] = SetInfo(weight, this[index].reps)
            }
        }
        reduce {
            state.copy(setInfo = info)
        }
    }

    fun changeReps(index: Int, reps: Int) = intent {
        val info = state.setInfo.toMutableList().apply {
            if (index in indices) {
                this[index] = SetInfo(this[index].weight, reps)
            }
        }
        reduce {
            state.copy(setInfo = info)
        }
    }

    fun changeShowDialog() = intent {
        val curShowDialog = state.showDialog
        reduce {
            state.copy(showDialog = !curShowDialog)
        }
    }

    fun addExercise() = intent {
        viewModelScope.launch {
            exerciseRepository.insertExercise(
                date = state.datePickerState.selectedDateMillis?.toDateString() ?: "",
                exerciseName = state.exerciseName,
                numOfSet = state.numOfSet,
                sets = state.setInfo,
                color = state.color
            )
            postSideEffect(AddExerciseSideEffect.navigateToCalendar)
        }
    }
}