package eu.lampenlampen.gymtracker.ui.topLevelModules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.lampenlampen.gymtracker.TopLevelNavigation
import eu.lampenlampen.gymtracker.data.Muscle

fun NavGraphBuilder.exerciseScreen(
	onNavigateTo: () -> Unit
) {
	composable("Exercise") {
		ExerciseScreen()
	}
}

@Composable
fun ExerciseScreen() {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Text(text = TopLevelNavigation.exercise.route, style = MaterialTheme.typography.displayLarge)

		Column(
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Muscle.values().forEach {
				MuscleListItem(it)
			}

			TabRow(0){
				Tab(true,
					onClick = {}) {
					Text("Tab1")
				}
				Tab(true,
					onClick = {}) {
					Text("Tab2  ")
				}
			}
		}
	}
}

@Composable
fun MuscleListItem(muscle: Muscle) {
	Text(muscle.name)
}