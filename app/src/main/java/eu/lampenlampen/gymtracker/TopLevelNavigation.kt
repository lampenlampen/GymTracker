package eu.lampenlampen.gymtracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelNavigationItems(
	val route: String,
	val iconText: String,
	val selectedItem: ImageVector,
	val unselectedIcon: ImageVector = selectedItem
)

object TopLevelNavigation {
	val home = TopLevelNavigationItems(
		"Home",
		"Home",
		Icons.Default.Home
	)

	val workout = TopLevelNavigationItems(
		"Workout",
		"Workout",
		Icons.Default.FitnessCenter
	)

	val exercise = TopLevelNavigationItems(
		"Exercise",
		"Exercise",
		Icons.Default.ListAlt
	)

	val routes = listOf(
		home,
		workout,
		exercise)
}