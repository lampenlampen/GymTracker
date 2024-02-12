package eu.lampenlampen.gymtracker.ui.topLevelModules.addExercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.lampenlampen.gymtracker.domain.ExerciseRepo
import eu.lampenlampen.gymtracker.domain.ExerciseRepoTestImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AddExerciseViewModel(private val repo: ExerciseRepo) : ViewModel() {
    private val _uiState: MutableStateFlow<AddExerciseUiState> = MutableStateFlow(AddExerciseUiState())
    val uiState: StateFlow<AddExerciseUiState> = _uiState.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repo = ExerciseRepoTestImpl()
                AddExerciseViewModel(repo)
            }
        }
    }
}