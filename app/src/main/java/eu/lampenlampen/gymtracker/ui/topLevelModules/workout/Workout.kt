package eu.lampenlampen.gymtracker.ui.topLevelModules.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.lampenlampen.gymtracker.data.ExerciseWithSets
import eu.lampenlampen.gymtracker.data.Workout
import eu.lampenlampen.gymtracker.domain.WorkoutRepoTestImpl.Companion.getTestWorkout
import eu.lampenlampen.gymtracker.ui.theme.GymTrackerTheme
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID


fun NavGraphBuilder.workoutScreen(
    onNavigateToExercise: (String) -> Unit,
    onNavigateToAddExerciseToWorkout: () -> Unit
) {
    composable("Workout") {

        val viewModel: WorkoutViewModel = viewModel(factory = WorkoutViewModel.Factory)

        if (pickedExercise. != null) {
            viewModel.addWorkout()
        }

        viewModel.loadWorkouts()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        WorkoutScreen(state, addWorkout = {
            onNavigateToAddExerciseToWorkout()
        }, onNavigateToExercise = {
            onNavigateToExercise(it.toString())
        })
    }
}

@Composable
fun WorkoutScreen(state: WorkoutUiState, addWorkout: () -> Unit, onNavigateToExercise: (exerciseId: UUID) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        when (state) {
            WorkoutUiState.Empty -> WorkoutEmpty()
            WorkoutUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is WorkoutUiState.Success -> WorkoutList(state.workouts, onNavigateToExercise)
        }

        ExtendedFloatingActionButton(onClick = { addWorkout() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            icon = {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add Exercise"
                )
            },
            text = { Text("Add Exercise") })
    }
}

@Composable
fun WorkoutList(workouts: List<Workout>, onNavigateToExercise: (exerciseId: UUID) -> Unit) {
    Column(modifier = Modifier.padding(12.dp)) {
        val timeZone = TimeZone.currentSystemDefault()

        DateHeader(
            instant = workouts[0].date, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )

        LazyColumn {
            items(
                items = workouts,
                key = { it.id }
            ) {
                val time = it.date.toLocalDateTime(timeZone).time

                Text(
                    "${time.hour}:${time.minute}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                WorkoutCard(it, onNavigateToExercise)
            }
        }
    }
}

@Composable
fun DateHeader(instant: Instant, modifier: Modifier = Modifier) {
    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

    Text("${date.dayOfWeek}, ${date.dayOfMonth}.${date.monthNumber}.${date.year}",
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge)
}

@Composable
fun WorkoutCard(workout: Workout, onNavigateToExercise: (exerciseId: UUID) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        val modifier = Modifier.fillMaxWidth()

        Column(modifier = Modifier.padding(20.dp)) {

            workout.exercises.forEachIndexed { index, it ->
                if (index == 0) ExerciseItem(it, onNavigateToExercise, modifier)
                else {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp), thickness = 2.dp, color = MaterialTheme.colorScheme.tertiary
                    )

                    ExerciseItem(it, onNavigateToExercise, modifier.padding(top = 16.dp))
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(
    exerciseWithSets: ExerciseWithSets, onNavigateToExercise: (exerciseId: UUID) -> Unit, modifier: Modifier = Modifier
) {
    val exercise = exerciseWithSets.exercise
    val sets = exerciseWithSets.sets
    Column(modifier = modifier.clickable { onNavigateToExercise(exercise.id) }) {
        Text(exercise.name, style = MaterialTheme.typography.titleLarge, textDecoration = TextDecoration.Underline)
        Spacer(Modifier.height(4.dp))

        sets.forEach { set ->
            Row(modifier = Modifier.align(Alignment.End)) {
                Text("${set.weight.weight} ${set.weight.unit}")
                Text("|", modifier = Modifier.padding(horizontal = 16.dp))
                Text("${set.repetition} reps")
            }
        }
    }
}

@Composable
fun WorkoutEmpty() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("No Trainings", style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Preview
@Composable
fun WorkoutPreview() {
    GymTrackerTheme {

        WorkoutScreen(state = WorkoutUiState.Success(listOf(getTestWorkout())), addWorkout = { /*TODO*/ }, onNavigateToExercise = { } )

    }
}
