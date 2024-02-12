package eu.lampenlampen.gymtracker.ui.topLevelModules.exercisePicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.lampenlampen.gymtracker.domain.ExerciseRepo
import eu.lampenlampen.gymtracker.domain.ExerciseRepoTestImpl
import eu.lampenlampen.gymtracker.ui.exercise.picker.ExercisePickerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExercisePickerViewModel(private val exerciseRepo: ExerciseRepo) : ViewModel() {
    private val _uiState: MutableStateFlow<ExercisePickerState> = MutableStateFlow(ExercisePickerState(emptyList()))
    val uiState: StateFlow<ExercisePickerState> = _uiState.asStateFlow()

    fun loadExercises() {
        viewModelScope.launch {
            val exercises = withContext(Dispatchers.IO) {
                exerciseRepo.getAllExercises()
            }

            _uiState.update { ExercisePickerState(exercises) }
        }

    }

    companion object Factory {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repo = ExerciseRepoTestImpl()
                ExercisePickerViewModel(repo)
            }
        }
    }
}
