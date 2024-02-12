package eu.lampenlampen.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.lampenlampen.common.dataModels.Author
import eu.lampenlampen.common.dataModels.Book

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookCard(book: BookCardState, modifier: Modifier = Modifier) {
	Card(
		elevation = 4.dp,
		modifier = modifier.padding(6.dp).height(256.dp),
		onClick = {
			// TODO onClick
		}
	) {
		Column {
			Image(
				modifier = Modifier.sizeIn(maxHeight = 128.dp).align(Alignment.CenterHorizontally),
				painter = painterResource(book.coverPath),
				contentDescription = "Book Cover"
			)
			Divider()

			Column(modifier = Modifier.padding(16.dp)) {
				Text(book.title, style = MaterialTheme.typography.h6)
				if (book.subtitle != null) {
					Text(book.subtitle, modifier = Modifier.padding(top = 4.dp))
				}

				Text(
					"by ${
						book.authors.joinToString(
							",",
							transform = { author -> "${author.firstname} ${author.lastname}" })
					}",
					modifier = Modifier.padding(top = 4.dp)
				)
			}

		}
	}
}

data class BookCardState(
	val coverPath: String,
	val title: String,
	val subtitle: String?,
	val authors: List<Author>
) {

	constructor(book: Book) : this("book_cover.jpg", book.title, book.subtitle, book.authors)
}