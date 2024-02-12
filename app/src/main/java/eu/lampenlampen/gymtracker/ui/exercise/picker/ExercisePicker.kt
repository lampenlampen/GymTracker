package eu.lampenlampen.gymtracker.ui.exercise.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.lampenlampen.gymtracker.data.Exercise
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisePicker(
    state: ExercisePickerState, onExerciseSelected: (UUID?) -> Unit, onCreateNewExercise: () -> Unit
) {
    val exercises = state.exercises

    Column {
        TopAppBar(modifier = Modifier, title = { Text(text = "Pick Exercise") }, navigationIcon = {
            IconButton(onClick = { onExerciseSelected(null) }) {
                Icon(Icons.Default.Close, "Close")
            }
        })

        // TODO SearchBar

        LazyColumn {
            items(items = exercises, key = { it.id }) {
                ListItem(modifier = Modifier.clickable { onExerciseSelected(it.id) },
                    headlineText = { Text(text = it.name)})
            }

            item {
                ListItem(modifier = Modifier.clickable { onCreateNewExercise() },
                    headlineText = { Text(text = "New Exercise", color = MaterialTheme.colorScheme.primary) },
                    leadingContent = {
                        Icon(Icons.Default.Add, "Create new Exercise", tint = MaterialTheme.colorScheme.primary)
                    })
            }
        }
    }
}

@Preview
@Composable
fun ExercisePickerPreview() {
    val state = ExercisePickerState(listOf(Exercise.dip, Exercise.pushUp))
    ExercisePicker(state = state, onExerciseSelected = {}, onCreateNewExercise = {})
}