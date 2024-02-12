package eu.lampenlampen.common.dataModels

import java.util.*

data class Book(
	val id: BookId,
	val title: String,
	val subtitle: String?,
	val authors: List<Author>,
	val summary: String?,
	val series: BookSeries?,
	val category: List<Category>,
	val publisher: Publisher,
	val isbn: ISBN,
	val type: BookType,
	val read: Boolean?,
	val publishedDate: String
)

@JvmInline
value class BookId(
	val id: UUID
) {
	companion object {
		fun NewId() = BookId(UUID.randomUUID())
	}
}

