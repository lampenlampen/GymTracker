package eu.lampenlampen.gymtracker.ui

import android.app.Activity
import android.graphics.Rect
import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Information about the posture of the device
 */
sealed interface DevicePosture {
	object NormalPosture : DevicePosture

	data class BookPosture(
		val hingePosition: Rect
	) : DevicePosture

	data class Separating(
		val hingePosition: Rect, var orientation: FoldingFeature.Orientation
	) : DevicePosture
}

/**
 * Different type of navigation supported by app depending on device size and state.
 */
enum class ReplyNavigationType {
	BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

/**
 * Different position of navigation content inside Navigation Rail, Navigation Drawer depending on device size and state.
 */
enum class NavigationContentPosition {
	TOP, CENTER
}

/**
 * App Content shown depending on device size and state.
 */
enum class ContentType {
	SINGLE_PANE, DUAL_PANE
}

data class DeviceFormFactorHints(
	/**
	 * Position at which the content inside the navigation drawer should be placed
	 * for ergonomics and reachability depending upon the height of the device
	 */
	val navigationContentPosition: NavigationContentPosition,
	val navigationType: ReplyNavigationType,
	val contentType: ContentType,
)

object DeviceFormFactor {
	/**
	 * This will help us select type of navigation and content type depending on window size and
	 * fold state of the device.
	 */
	@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
	@Composable
	fun getDeviceFromFactorHints(activity: ComponentActivity): DeviceFormFactorHints {
		val windowSize = calculateWindowSizeClass(activity)
		val displayFeatures = calculateDisplayFeatures(activity)

		return getDeviceFromFactorHints(windowSize, displayFeatures)
	}

	/**
	 * This will help us select type of navigation and content type depending on window size and
	 * fold state of the device.
	 */
	@Composable
	fun getDeviceFromFactorHints(windowSize: WindowSizeClass, displayFeatures: List<DisplayFeature>): DeviceFormFactorHints {
		val foldingDevicePosture = getDeviceFoldingPosture(displayFeatures)
		val a = abc(windowSize, foldingDevicePosture)


		return DeviceFormFactorHints(
			getNavigationContentPosition(windowSize),
			a.first,
			a.second,
		)
	}

	/**
	 * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
	 * ergonomics and reachability depending upon the height of the device.
	 */
	@Composable
	fun getNavigationContentPosition(windowSize: WindowSizeClass): NavigationContentPosition = when (windowSize.heightSizeClass) {
		WindowHeightSizeClass.Compact -> {
			NavigationContentPosition.TOP
		}

		WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
			NavigationContentPosition.CENTER
		}

		else -> {
			NavigationContentPosition.TOP
		}
	}

	/**
	 * We are using display's folding features to map the device postures a fold is in.
	 * In the state of folding device If it's half fold in BookPosture we want to avoid content
	 * at the crease/hinge
	 */
	@Composable
	private fun getDeviceFoldingPosture(displayFeatures: List<DisplayFeature>): DevicePosture {
		val foldingFeature = getDeviceFoldingFeature(displayFeatures)

		return when {
			isBookPosture(foldingFeature) -> DevicePosture.BookPosture(foldingFeature.bounds)

			isSeparating(foldingFeature) -> DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

			else -> DevicePosture.NormalPosture
		}
	}

	@Composable
	private fun abc(windowSize: WindowSizeClass, foldingDevicePosture: DevicePosture): Pair<ReplyNavigationType, ContentType> {
		/**
		 * This will help us select type of navigation and content type depending on window size and
		 * fold state of the device.
		 */
		val navigationType: ReplyNavigationType
		val contentType: ContentType

		when (windowSize.widthSizeClass) {
			WindowWidthSizeClass.Compact -> {
				navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
				contentType = ContentType.SINGLE_PANE
			}

			WindowWidthSizeClass.Medium -> {
				navigationType = ReplyNavigationType.NAVIGATION_RAIL
				contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
					ContentType.DUAL_PANE
				} else {
					ContentType.SINGLE_PANE
				}
			}

			WindowWidthSizeClass.Expanded -> {
				navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
					ReplyNavigationType.NAVIGATION_RAIL
				} else {
					ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
				}
				contentType = ContentType.DUAL_PANE
			}

			else -> {
				navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
				contentType = ContentType.SINGLE_PANE
			}
		}

		return Pair(navigationType, contentType)
	}

	@Composable
	private fun getDeviceFoldingFeature(displayFeatures: List<DisplayFeature>): FoldingFeature? =
		displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

	/**
	 * Calculates the list of [DisplayFeature]s from the given [activity].
	 * @see <a href="https://github.com/google/accompanist/blob/main/adaptive/src/main/java/com/google/accompanist/adaptive/DisplayFeatures.kt#L27">Google Accompanist</a>
	 */
	@Composable
	fun calculateDisplayFeatures(activity: Activity): List<DisplayFeature> {
		val windowInfoTracker = remember(activity) { WindowInfoTracker.getOrCreate(activity) }
		val windowLayoutInfo = remember(windowInfoTracker, activity) {
			windowInfoTracker.windowLayoutInfo(activity)
		}

		val displayFeatures by produceState(initialValue = emptyList<DisplayFeature>()) {
			windowLayoutInfo.collect { info ->
				value = info.displayFeatures
			}
		}

		return displayFeatures
	}

	@OptIn(ExperimentalContracts::class)
	private fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
		contract { returns(true) implies (foldFeature != null) }
		return foldFeature?.state == FoldingFeature.State.HALF_OPENED && foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
	}

	@OptIn(ExperimentalContracts::class)
	private fun isSeparating(foldFeature: FoldingFeature?): Boolean {
		contract { returns(true) implies (foldFeature != null) }
		return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
	}
}