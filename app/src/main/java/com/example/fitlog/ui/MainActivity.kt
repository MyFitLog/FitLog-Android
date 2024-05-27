package com.example.fitlog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlog.ui.add.AddExerciseViewModel
import com.example.fitlog.ui.add.composable.AddExerciseScreen
import com.example.fitlog.ui.calendar.CalendarViewModel
import com.example.fitlog.ui.calendar.composable.CalendarScreen
import com.example.fitlog.ui.theme.FitLogTheme
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLogTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.Calendar.route) {
                        addCalendar()
                        addNewExercise()
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.addCalendar() {
    composable(route = Screen.Calendar.route) {
        val viewModel: CalendarViewModel = koinViewModel()
        val state by viewModel.collectAsState()

        CalendarScreen(
            state = state,
            selectDay = { day -> viewModel.selectDay(day) }
        )
    }
}

fun NavGraphBuilder.addNewExercise() {
    composable(route = Screen.AddExercise.route) {
        val viewModel: AddExerciseViewModel = koinViewModel()
        val state by viewModel.collectAsState()

        AddExerciseScreen(
            state = state,
            changeExerciseName = { name -> viewModel.changeExerciseName(name) },
            changeNumOfSet = { num -> viewModel.changeNumOfSet(num) },
            changeReps = { idx, reps -> viewModel.changeReps(idx, reps) },
            changeWeight = { idx, weight -> viewModel.changeWeight(idx, weight) },
            removeSetInfo = { idx -> viewModel.removeSetInfo(idx) }
        )
    }
}

sealed class Screen(val route: String) {
    data object Calendar : Screen(route = "calendar")
    data object AddExercise : Screen(route = "add")
}