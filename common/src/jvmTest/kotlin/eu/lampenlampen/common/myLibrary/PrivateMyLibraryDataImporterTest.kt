package eu.lampenlampen.common.myLibrary

import eu.lampenlampen.common.ExposedTestDB
import eu.lampenlampen.common.dal.*
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PrivateMyLibraryDataImporterTest : FunSpec({

	test("get my MyLibrary data and save into db and check") {
		val path = ClassLoader.getSystemResource("./private/mylibrary.db").path
		val db = autoClose(ExposedTestDB())
		val importer = MyLibraryDataImporter(db)

		val data = MyLibraryDBConnector().getData(path)
		importer.importData(data)

		val bookSeriesCount: Int = transaction(db.db) {
			BookSeriesTable.selectAll().count().toInt()
		}

		val authorsCount = transaction(db.db) {
			AuthorTable.selectAll().count().toInt()
		}

		val booksCount = transaction(db.db) {
			BookTable.selectAll().count().toInt()
		}

		val bookSeriesRelationCount = transaction(db.db) {
			BookBookSeriesTable.selectAll().count().toInt()
		}

		val bookAuthorRelationCount = transaction(db.db) {
			BookAuthorTable.selectAll().count().toInt()
		}

		val actualCountOfBookSeries = 54
		withClue("Number of Book Series should be $actualCountOfBookSeries") {
			bookSeriesCount shouldBeExactly actualCountOfBookSeries
		}

		val actualCountOfAuthors = 206
		withClue("Count of Authors should be $actualCountOfAuthors") {
			authorsCount shouldBeExactly actualCountOfAuthors
		}

		val actualCountOfBooks = 238
		withClue("Count of Books should be $actualCountOfBooks") {
			booksCount shouldBeExactly actualCountOfBooks
		}

		val actualCountOfBookSeriesRelations = 134
		withClue("Count of Book/Series relations should be $actualCountOfBookSeriesRelations") {
			bookSeriesRelationCount shouldBeExactly actualCountOfBookSeriesRelations
		}

		val actualCountOfBookAuthorRelations = 276
		withClue("Count of Book/Author relations should be $actualCountOfBookAuthorRelations") {
			bookAuthorRelationCount shouldBeExactly actualCountOfBookAuthorRelations
		}
	}
})

