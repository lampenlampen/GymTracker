package eu.lampenlampen.gymtracker.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TimerDialog() {
	AlertDialog(
		onDismissRequest = {

		},
		confirmButton = {
			Button(
				onClick = {

				}) {
				Text("Start")
			}
		},
		text = {
			Text("Timer")
		}
	)
}