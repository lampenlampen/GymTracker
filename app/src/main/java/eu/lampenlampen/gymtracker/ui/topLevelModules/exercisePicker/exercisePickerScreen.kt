package eu.lampenlampen.gymtracker.ui.topLevelModules.exercisePicker

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.lampenlampen.gymtracker.ui.exercise.picker.ExercisePicker
import eu.lampenlampen.gymtracker.ui.topLevelModules.workout.WorkoutScreen
import eu.lampenlampen.gymtracker.ui.topLevelModules.workout.WorkoutViewModel
import java.util.UUID

fun NavController.navigateToExercisePicker() {
    navigate("ExercisePicker")
}

fun NavGraphBuilder.exercisePickerScreen(
    onExerciseSelected: (UUID?) -> Unit,
    onCreateNewExercise: () -> Unit
) {
    composable("ExercisePicker") {
        val vm: ExercisePickerViewModel = viewModel(factory = ExercisePickerViewModel.Factory)
        vm.loadExercises()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ExercisePicker(state = state, onExerciseSelected = {
            onExerciseSelected(it)
        }, onCreateNewExercise = {
            onCreateNewExercise()
        })
    }
}