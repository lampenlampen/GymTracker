package eu.lampenlampen.common.myLibrary

import eu.lampenlampen.common.ExposedTestDB
import eu.lampenlampen.common.dal.*
import eu.lampenlampen.common.dataModels.Author
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class MyLibraryDataImporterTest : FunSpec({
	test("Insert and Get Books ") {
		val books = PrivateMyLibraryExamples.books

		val db = autoClose(ExposedTestDB())
		val importer = MyLibraryDataImporter(db)

		val data = MyLibraryData(books, emptyList())
		importer.importData(data)

		val booksCount = transaction(db.db) {
			BookTable.selectAll().count().toInt()
		}

		withClue("Check Number of Books") {
			booksCount shouldBeExactly books.size
		}
	}

	test("Insert and Get Authors") {
		val authors = PrivateMyLibraryExamples.authors

		val db = autoClose(ExposedTestDB())
		val importer = MyLibraryDataImporter(db)

		val data = MyLibraryData(emptyList(), authors)
		importer.importData(data)

		val authorsCount = transaction(db.db) {
			AuthorTable.selectAll().count().toInt()
		}

		withClue("Check Number of Authors") {
			authorsCount shouldBeExactly authors.size
		}
	}

	test("Insert and Get Book Series") {
		val books = PrivateMyLibraryExamples.books
		val series = PrivateMyLibraryExamples.series

		val db = autoClose(ExposedTestDB())
		val importer = MyLibraryDataImporter(db)

		val data = MyLibraryData(books, emptyList())
		importer.importData(data)

		val bookSeriesCount: Int = transaction(db.db) {
			BookSeriesTable.selectAll().count().toInt()
		}

		withClue("Check Number of Book Series.") {
			bookSeriesCount shouldBeExactly series.size
		}
	}

	test("Insert and Get Book Series2") {
		val books = PrivateMyLibraryExamples.books
		val series = PrivateMyLibraryExamples.series

		val db = autoClose(ExposedTestDB())
		val importer = MyLibraryDataImporter(db)

		val data = MyLibraryData(books, emptyList())
		importer.importData(data)

		val bookSeriesCount: Int = transaction(db.db) {
			BookTable.innerJoin(BookBookSeriesTable).innerJoin(BookSeriesTable)
				.select { BookSeriesTable.name eq series[0].title }
				.count().toInt()
		}

		withClue("Check Number of Book Series.") {
			bookSeriesCount shouldBeExactly 2
		}
	}

	test("Insert and Get Book Author2") {
		val book = PrivateMyLibraryExamples.books[1]
		val authors = PrivateMyLibraryExamples.authors.filter { book.authorIds.contains(it.myLibraryId) }

		val db = autoClose(ExposedTestDB())
		val importer = MyLibraryDataImporter(db)

		val data = MyLibraryData(listOf(book), authors)
		importer.importData(data)

		val bookAuthors = transaction(db.db) {
			BookTable.innerJoin(BookAuthorTable).innerJoin(AuthorTable)
				.select { BookTable.externalId eq book.myLibraryId.toString() }
				.map {
					Author(
						it[AuthorTable.id].value,
						it[AuthorTable.firstname],
						it[AuthorTable.lastname],
						it[AuthorTable.externalId]
					)
				}
		}

		withClue("Check Number of Book Authors.") {
			bookAuthors shouldHaveSize 2
		}

		withClue("Check Authors") {
			bookAuthors[0].externalId shouldBeIn book.authorIds.map { it.toString() }
			bookAuthors[1].externalId shouldBeIn book.authorIds.map { it.toString() }
		}
	}

	test("Insert and Check Book, Series Relationship") {
		val book = PrivateMyLibraryExamples.books[1]

		val db = autoClose(ExposedTestDB())
		val importer = MyLibraryDataImporter(db)

		val data = MyLibraryData(listOf(book), emptyList())
		importer.importData(data)

		val series = transaction(db.db) {
			BookTable.innerJoin(BookBookSeriesTable).innerJoin(BookSeriesTable)
				.select { BookTable.externalId eq book.myLibraryId.toString() }
				.map { MyLibrarySeriesModel(it[BookSeriesTable.name], it[BookBookSeriesTable.volume].toDouble()) }
				.single()
		}

		withClue("Check Series") {
			series.title shouldBe book.series!!.title
			series.volume shouldBe book.series!!.volume
		}
	}
})