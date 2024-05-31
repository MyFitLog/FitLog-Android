package com.example.fitlog.ui.add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.common.SetInfo
import com.example.fitlog.data.room.exercise.ExerciseRepository
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
class AddExerciseViewModel(
    private val exerciseRepository: ExerciseRepository
) : ContainerHost<AddExerciseState, AddExerciseSideEffect>, ViewModel() {
    override val container = container<AddExerciseState, AddExerciseSideEffect>(AddExerciseState())

    fun changeExerciseName(text: String) = intent {
        reduce {
            state.copy(exerciseName = text)
        }
    }

    fun addSet() = intent {
        val curNumOfSet = state.numOfSet
        val info = state.setInfo + listOf(SetInfo("", 0))
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
                date = state.curDate.toString(),
                exerciseName = state.exerciseName,
                numOfSet = state.numOfSet,
                sets = state.setInfo,
                color = state.color
            )
            postSideEffect(AddExerciseSideEffect.navigateToCalendar)
        }
    }

    fun selectDay(selectedDate: LocalDate) = intent {
        reduce {
            state.copy(curDate = selectedDate)
        }
    }
}