package eu.lampenlampen.gymtracker.ui.topLevelModules

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.lampenlampen.gymtracker.data.Exercise
import eu.lampenlampen.gymtracker.domain.ExerciseRepo
import eu.lampenlampen.gymtracker.domain.ExerciseRepoTestImpl
import eu.lampenlampen.gymtracker.domain.WorkoutRepoTestImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*


fun NavController.navigateToExercise(exerciseId: String) {
	navigate("ExerciseDetail/$exerciseId")
}

private const val ExerciseDetailIdArg = "exerciseId"

fun NavGraphBuilder.exerciseDetailScreen(
	onNavigateToExercise: () -> Unit
) {
	composable("ExerciseDetail/{$ExerciseDetailIdArg}") {
		val exerciseId = UUID.fromString(it.arguments?.getString(ExerciseDetailIdArg)) ?: throw IllegalStateException("No exercise id")
		val viewModel: ExerciseDetailViewModel = viewModel(factory = ExerciseDetailViewModel.Factory)
		val uiState by viewModel.uiState.collectAsState()
		ExerciseDetailScreen(uiState)
		viewModel.loadExercise(exerciseId)
	}
}

@Composable
fun ExerciseDetailScreen(state: ExerciseDetailUiState) {
	Text("ExerciseDetailScreen: ${state.exercise?.name ?: "No Exercise"}")
}

internal class ExerciseDetailViewModel(val exerciseRepo: ExerciseRepo) : ViewModel() {
	private val _uiState = MutableStateFlow(ExerciseDetailUiState(null))
	val uiState: StateFlow<ExerciseDetailUiState> = _uiState.asStateFlow()

	fun loadExercise(id: UUID) {
		viewModelScope.launch {
			val exercise = exerciseRepo.getExercise(id)
			_uiState.update { ExerciseDetailUiState(exercise) }
		}
	}

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val repo = ExerciseRepoTestImpl()
				ExerciseDetailViewModel(repo)
			}
		}
	}
}

class ExerciseDetailUiState(
	val exercise : Exercise?
)