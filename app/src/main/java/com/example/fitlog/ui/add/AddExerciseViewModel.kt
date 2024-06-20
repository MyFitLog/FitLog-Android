package com.example.fitlog.ui.add

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.common.toDateString
import com.example.fitlog.data.room.exercise.ExerciseEntity
import com.example.fitlog.data.room.exercise.ExerciseRepository
import com.example.fitlog.data.room.exercise.SetEntity
import kotlinx.coroutines.Dispatchers
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
            ),
        )
        )

    init {
        getExerciseNames()
    }

    private fun getExerciseNames() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseNames = listOf("운동 선택") + exerciseRepository.getExerciseNames()
            reduce {
                state.copy(exerciseNameList = exerciseNames)
            }
        }
    }

    fun addSet() = intent {
        val curNumOfSet = state.numOfSet
        val newSet =
            if (state.numOfSet == 0) SetEntity(order = curNumOfSet, weight = "", reps = 0)
            else SetEntity(order = curNumOfSet, weight = state.setInfo.last().weight, reps = state.setInfo.last().reps)
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
                this[index] = this[index].copy(weight = weight)
            }
        }
        reduce {
            state.copy(setInfo = info)
        }
    }

    fun changeReps(index: Int, reps: Int) = intent {
        val info = state.setInfo.toMutableList().apply {
            if (index in indices) {
                this[index] = this[index].copy(reps = reps)
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
                exerciseEntity = ExerciseEntity(
                    name = state.exerciseNameList[state.selectedIndex],
                    numOfSets = state.numOfSet,
                    color = state.color,
                    date = state.datePickerState.selectedDateMillis?.toDateString() ?: "",
                ),
                listOfSet = state.setInfo
            )
            postSideEffect(AddExerciseSideEffect.navigateToCalendar)
        }
    }

    fun changeShowSelectableList(value: Boolean) = intent {
        reduce {
            state.copy(showSelectableList = value)
        }
    }

    fun selectListItem(index: Int) = intent {
        reduce {
            state.copy(selectedIndex = index)
        }
    }
}