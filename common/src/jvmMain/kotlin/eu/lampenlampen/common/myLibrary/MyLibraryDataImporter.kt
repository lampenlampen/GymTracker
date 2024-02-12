package eu.lampenlampen.common.myLibrary

import eu.lampenlampen.common.dal.*
import eu.lampenlampen.common.dataModels.BookType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class MyLibraryDataImporter(
	private val db: IExposedDatabase
) {
	fun importData(data: MyLibraryData) = saveDataInDB(data)

	private fun saveDataInDB(data: MyLibraryData) {
		val books = data.books
		val authors = data.authors

		saveAuthorsInDB(authors)
		saveSeriesInDB(books.mapNotNull { it.series }.distinctBy { it.title })
		saveBooksInDB(books)
		saveBookAuthorRelations(books)
		saveBookSeriesRelations(books)
		savePublishersAndBookRelations(books)
	}

	private fun savePublishersAndBookRelations(books: List<MyLibraryBookModel>) {
		books.forEach { book ->
			transaction(db.db) {
				val bookId = BookTable
					.slice(BookTable.id)
					.select { BookTable.externalId eq book.myLibraryId.toString() }

				PublisherTable.insertIgnore {
					it[id] = UUID.randomUUID()
					it[publisher] = book.publisher
				}

				val publisherId = PublisherTable
					.slice(PublisherTable.id)
					.select { PublisherTable.publisher eq book.publisher }

				BookPublisherTable.insert {
					it[this.book] = bookId
					it[publisher] = publisherId
				}
			}
		}
	}

	private fun saveBookSeriesRelations(books: List<MyLibraryBookModel>) {
		books
			.forEach { book ->
				val series = book.series ?: return@forEach

				transaction(db.db) {
					val bookIdQuery = BookTable
						.slice(BookTable.id)
						.select { BookTable.externalId eq book.myLibraryId.toString() }

					val seriesId = BookSeriesTable
						.slice(BookSeriesTable.id)
						.select { BookSeriesTable.name eq book.series.title }

					BookBookSeriesTable.insert {
						it[this.book] = bookIdQuery
						it[bookSeries] = seriesId
						it[volume] = series.volume?.toInt() ?: 0
					}
				}
			}
	}

	private fun saveBookAuthorRelations(books: List<MyLibraryBookModel>) {
		books.forEach {
			transaction(db.db) {
				val bookIdQuery = BookTable
					.slice(BookTable.id)
					.select { BookTable.externalId eq it.myLibraryId.toString() }

				val authorsId = AuthorTable
					.slice(AuthorTable.id)
					.select(AuthorTable.externalId inList it.authorIds.map { it.toString() })

				BookAuthorTable.batchInsert(authorsId) {
					this[BookAuthorTable.book] = bookIdQuery
					this[BookAuthorTable.author] = it[AuthorTable.id]
				}
			}
		}
	}

	private fun saveSeriesInDB(series: List<MyLibrarySeriesModel>) {
		transaction(db.db) {
			BookSeriesTable.batchInsert(series, ignore = true) {
				this[BookSeriesTable.id] = UUID.randomUUID()
				this[BookSeriesTable.name] = it.title
			}
		}
	}

	private fun saveBooksInDB(books: List<MyLibraryBookModel>) {
		val existingBooksIds = transaction(db.db) {
			BookTable.slice(BookTable.externalId).select { BookTable.externalId.isNotNull() }
				.mapNotNull { it[BookTable.externalId]?.toInt() }
		}

		val newBooks = books.filter { !existingBooksIds.contains(it.myLibraryId) }

		transaction(db.db) {
			BookTable.batchInsert(newBooks) {
				this[BookTable.id] = UUID.randomUUID()
				this[BookTable.title] = it.title
				this[BookTable.subtitle] = null
				this[BookTable.isbn] = it.isbn
				this[BookTable.type] = BookType.PhysicalBook.name
				this[BookTable.summary] = it.summary
				this[BookTable.publishedDate] = it.publishedDate.toString()
				this[BookTable.read] = it.read
				this[BookTable.pages] = it.pages
				this[BookTable.externalId] = it.myLibraryId.toString()
			}
		}
	}

	private fun saveAuthorsInDB(authors: List<MyLibraryAuthorModel>) {
		val existingSeriesIds = transaction(db.db) {
			AuthorTable.slice(AuthorTable.externalId).select { AuthorTable.externalId.isNotNull() }
				.mapNotNull { it[AuthorTable.externalId]?.toInt() }
		}

		val newAuthors = authors.filter {
			!existingSeriesIds.contains(it.myLibraryId)
		}

		transaction(db.db) {
			AuthorTable.batchInsert(newAuthors) {
				this[AuthorTable.id] = UUID.randomUUID()
				this[AuthorTable.firstname] = it.firstname.orEmpty()
				this[AuthorTable.lastname] = it.lastname
				this[AuthorTable.externalId] = it.myLibraryId.toString()
			}
		}
	}
}