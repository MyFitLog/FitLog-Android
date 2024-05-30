package com.example.fitlog.ui.add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import com.example.fitlog.common.SetInfo
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@OptIn(ExperimentalMaterial3Api::class)
class AddExerciseViewModel : ContainerHost<AddExerciseState, AddExerciseSideEffect>, ViewModel() {
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
}