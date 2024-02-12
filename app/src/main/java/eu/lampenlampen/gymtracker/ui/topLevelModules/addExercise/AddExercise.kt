package eu.lampenlampen.gymtracker.ui.topLevelModules.addExercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavController.navigateToAddExerciseToWorkout() {
    navigate("AddExerciseToWorkout")
}

fun NavGraphBuilder.addExerciseScreen(
    onNavigateTo: () -> Unit
) {
    composable("AddExerciseToWorkout") {
        val viewModel: AddExerciseViewModel = viewModel(factory = AddExerciseViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        AddExerciseScreen(state)
    }
}

@Composable
fun AddExerciseScreen(state: AddExerciseUiState) {

}
