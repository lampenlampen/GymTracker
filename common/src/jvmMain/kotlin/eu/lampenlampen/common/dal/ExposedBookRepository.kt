package eu.lampenlampen.common.dal

import eu.lampenlampen.common.BookRepository
import eu.lampenlampen.common.dataModels.Book

class ExposedBookRepository : BookRepository {
	override fun getBook(bookId: Int) {
		TODO("Not yet implemented")
	}

	override fun getAllBooks() {
		TODO("Not yet implemented")
	}

	override fun createBook(book: Book) {
		TODO("Not yet implemented")
	}

	override fun deleteBook(bookId: Int) {
		TODO("Not yet implemented")
	}
}