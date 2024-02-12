package eu.lampenlampen.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun App() {
	val appState = rememberAppState()

	MyTheme(appState.isDarkModeEnabled) {
		MainContent()
	}
}

class AppState {
	val isDarkModeEnabled = false
}

@Composable
fun rememberAppState() = remember {
	AppState()
}