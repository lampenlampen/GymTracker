package eu.lampenlampen.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MainContent(
	//state1: MainContentState = MainContentState()
) {
	val state1 = MainContentState()
	//val state1 = remember { state }
	val bookListState = BookListState()

	Column {
		Header()
		Button(
			onClick = {
				state1.title.value += "!"

			}) {
			Text(state1.title.collectAsState().value)
		}
		//DrawerContent()
		BookGridView(bookListState)
	}
}

class MainContentState {
	val title: MutableStateFlow<String> = MutableStateFlow("Hello World")
}

@Composable
fun Header() {
	TopAppBar(
		title = { Text("BookLibrary") }
	)
}