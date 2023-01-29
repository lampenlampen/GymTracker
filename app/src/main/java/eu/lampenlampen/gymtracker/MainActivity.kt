package eu.lampenlampen.gymtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import eu.lampenlampen.gymtracker.ui.DeviceFormFactor
import eu.lampenlampen.gymtracker.ui.GymTrackerApp
import eu.lampenlampen.gymtracker.ui.theme.GymTrackerTheme

class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			GymTrackerTheme {
				val deviceFormFactorHints = DeviceFormFactor.getDeviceFromFactorHints(this)

				Surface(color = MaterialTheme.colorScheme.background) {

					GymTrackerApp(
						navigationType = deviceFormFactorHints.navigationType,
					)
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun GymTrackerAppPreview() {
	GymTrackerTheme {
		val windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp))
		val navigationType = DeviceFormFactor.getDeviceFromFactorHints(windowSize, emptyList()).navigationType

		Surface(color = MaterialTheme.colorScheme.background) {
			GymTrackerApp(navigationType)
		}
	}
}

