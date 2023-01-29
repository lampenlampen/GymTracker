package eu.lampenlampen.gymtracker.ui.topLevelModules.workout

import androidx.compose.runtime.Stable
import eu.lampenlampen.gymtracker.data.Workout

@Stable
sealed interface WorkoutUiState {
	object Loading : WorkoutUiState

	object Empty : WorkoutUiState

	class Success(val workouts: List<Workout>) : WorkoutUiState
}