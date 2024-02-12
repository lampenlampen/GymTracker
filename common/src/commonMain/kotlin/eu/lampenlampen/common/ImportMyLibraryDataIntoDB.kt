package eu.lampenlampen.common

import eu.lampenlampen.common.cqrs.CommandHandler
import eu.lampenlampen.common.dataModels.Book
import eu.lampenlampen.common.myLibrary.MyLibraryCSVModel

class ImportMyLibraryDataIntoDB(private val repo: BookRepository) : CommandHandler<ImportMyLibraryDataIntoDBCommand> {

	override fun execute(command: ImportMyLibraryDataIntoDBCommand) {
		val books = command.books

		for (book in books) repo.createBook(convertMyLibraryModelToDBModel(book))
	}

	private fun convertMyLibraryModelToDBModel(book: MyLibraryCSVModel): Book {
		val series = book.series
		if (series != null) {

		}

		/*
				repo.createBook(
					Book(
						book.title,
						null,
						emptyList(),
						book.summary,
						if (book.series == null) null else BookSeries(book.series),
						book.categories.map { Category(book) },
						Publisher(book.publisher),
						book.isbn?.let { book1 -> ISBN(book1) },
						BookType.PhysicalBook,
						book.read
					)
				)
		*/
		TODO()
	}
}

data class ImportMyLibraryDataIntoDBCommand(val books: List<MyLibraryCSVModel>)