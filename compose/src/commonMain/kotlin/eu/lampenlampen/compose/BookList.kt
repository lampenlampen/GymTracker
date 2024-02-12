package eu.lampenlampen.compose

import BookExamples
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import eu.lampenlampen.common.dataModels.Book
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookGridView(state: BookListState = BookListState()) {
	val books = state.books.collectAsState()

	LazyVerticalGrid(
		contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
		columns = GridCells.Adaptive(minSize = 256.dp),
		verticalArrangement = Arrangement.spacedBy(4.dp),
		horizontalArrangement = Arrangement.spacedBy(4.dp)
	) {
		items(books.value, { book -> book.id }) {
			BookCard(BookCardState(it))
		}
	}
}

@Composable
fun BooksListView() {

}

class BookListState {
	val books: MutableStateFlow<List<Book>> = MutableStateFlow(BookExamples.books)
}