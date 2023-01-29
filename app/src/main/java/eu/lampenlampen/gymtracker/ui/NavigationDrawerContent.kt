package eu.lampenlampen.gymtracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NavigationDrawerContent(onDrawerIconClicked: () -> Unit = {}) {
	Column {
		Row(
			modifier = Modifier.fillMaxWidth().padding(16.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				"GymTracker",
				style = MaterialTheme.typography.titleMedium
			)
			IconButton(onClick = onDrawerIconClicked) {
				Icon(imageVector = Icons.Default.Menu, contentDescription = "Navigation Drawer Icon")
			}
		}
	}
}

@Preview
@Composable
fun NavigationDrawerContentPreview() {
	NavigationDrawerContent()
}