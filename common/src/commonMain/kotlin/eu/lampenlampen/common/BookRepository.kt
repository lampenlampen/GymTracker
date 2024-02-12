package eu.lampenlampen.common

import eu.lampenlampen.common.dataModels.Book

interface BookRepository {
	fun getBook(bookId: Int)

	fun getAllBooks()

	fun createBook(book: Book)

	fun deleteBook(bookId: Int)

}