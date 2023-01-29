package eu.lampenlampen.gymtracker.ui.topLevelModules.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.lampenlampen.gymtracker.domain.WorkoutRepo
import eu.lampenlampen.gymtracker.domain.WorkoutRepoTestImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class WorkoutViewModel(private val workoutRepo: WorkoutRepo) : ViewModel() {
	private val _uiState: MutableStateFlow<WorkoutUiState> = MutableStateFlow(WorkoutUiState.Loading)
	val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

	fun loadWorkouts() {
		viewModelScope.launch {
			val workouts = withContext(Dispatchers.IO) {
				val workouts = workoutRepo.getAllWorkouts()
				workouts
			}

			if (workouts.isEmpty()) _uiState.update { WorkoutUiState.Empty }
			else _uiState.update {WorkoutUiState.Success(workouts)}
		}
	}

	fun addWorkout() {
		// TODO Add Workout

		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				workoutRepo.saveWorkout(WorkoutRepoTestImpl.getTestWorkout())
			}
			// TODO
			loadWorkouts()
		}
	}

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val repo = WorkoutRepoTestImpl()
				WorkoutViewModel(repo)
			}
		}
	}
}