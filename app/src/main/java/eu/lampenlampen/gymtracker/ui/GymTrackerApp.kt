package eu.lampenlampen.gymtracker.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.lampenlampen.gymtracker.TopLevelNavigation
import eu.lampenlampen.gymtracker.ui.topLevelModules.exerciseDetailScreen
import eu.lampenlampen.gymtracker.ui.topLevelModules.exerciseScreen
import eu.lampenlampen.gymtracker.ui.topLevelModules.navigateToExercise
import eu.lampenlampen.gymtracker.ui.topLevelModules.workout.workoutScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymTrackerApp(
	navigationType: ReplyNavigationType
) {
	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	val scope = rememberCoroutineScope()

	if (navigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER) {
		PermanentNavigationDrawer(drawerContent = { PermanentDrawerSheet { NavigationDrawerContent() } }) {
			GymTrackerAppContent(navigationType)
		}
	} else {
		ModalNavigationDrawer(
			drawerContent = {
				ModalDrawerSheet {
					NavigationDrawerContent(onDrawerIconClicked = {
						scope.launch {
							drawerState.close()
						}
					})
				}
			}, drawerState = drawerState
		) {
			GymTrackerAppContent(navigationType)
		}
	}
}

@Composable
private fun GymTrackerAppContent(navigationType: ReplyNavigationType, onDrawerClicked: () -> Unit = {}) {
	val navController = rememberNavController()
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val selectedTopLevelRoute = navBackStackEntry?.destination?.route ?: TopLevelNavigation.home.route

	Row(modifier = Modifier.fillMaxSize()) {
		AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
			NavigationRail(
				modifier = Modifier.fillMaxHeight(), containerColor = MaterialTheme.colorScheme.inverseOnSurface
			) {
				Column(
					horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)
				) {
					NavigationRailItem(selected = false, onClick = onDrawerClicked, icon = {
						Icon(imageVector = Icons.Default.Menu, contentDescription = "Drawer Icon")
					})
				}
			}
		}

		Column {
			NavigationHost(
				navController, modifier = Modifier.weight(1f)
			)

			AnimatedVisibility(visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
				BottomNavigationBar(selectedTopLevelRoute) {
					navController.navigate(it)
				}
			}
		}
	}
}

@Composable
fun NavigationHost(
	navController: NavHostController, modifier: Modifier
) {
	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = TopLevelNavigation.home.route
	) {
		composable(TopLevelNavigation.home.route) {
			HomeScreen()
		}
		exerciseDetailScreen { }
		exerciseScreen { }
		workoutScreen(onNavigateToExercise = { navController.navigateToExercise(it) })
	}
}


@Composable
fun BottomNavigationBar(
	selectedItem: String, navigateToItem: (String) -> Unit
) {
	NavigationBar(modifier = Modifier.fillMaxWidth()) {
		TopLevelNavigation.routes.forEach {
			NavigationBarItem(selected = selectedItem == it.route, onClick = { navigateToItem(it.route) }, icon = {
				Icon(
					imageVector = it.selectedItem, contentDescription = it.iconText
				)
			}, label = { Text(it.route) })
		}
	}
}

@Composable
fun HomeScreen() {
	Row(
		modifier = Modifier.fillMaxSize()
	) {
		Text(text = TopLevelNavigation.home.route, style = MaterialTheme.typography.displayLarge)
	}
}