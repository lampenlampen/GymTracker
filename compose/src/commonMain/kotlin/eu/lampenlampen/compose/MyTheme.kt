package eu.lampenlampen.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun MyTheme(
	darkTheme: Boolean = false,
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colors = if (darkTheme) darkColors() else lightColors(),
		content = content
	)
}