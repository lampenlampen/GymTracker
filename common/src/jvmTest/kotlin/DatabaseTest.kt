import eu.lampenlampen.common.ExposedTestDB
import eu.lampenlampen.common.dal.BookTable
import eu.lampenlampen.common.dataModels.BookType
import eu.lampenlampen.common.dataModels.ISBN
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DatabaseTest : FunSpec({
	test("insert into BookTable and get") {
		val db = autoClose(ExposedTestDB()).db

		data class BookTableModel(
			val title: String,
			val subtitle: String?,
			val summary: String?,
			val isbn: ISBN,
			val type: BookType,
			val publishedDate: String
		)

		val book = TestBookExamples.books
			.map {
				BookTableModel(
					it.title,
					it.subtitle,
					it.summary,
					it.isbn,
					it.type,
					it.publishedDate
				)
			}
			.first()

		val bookId = transaction(db) {
			BookTable.insertAndGetId {
				it[id] = UUID.randomUUID()
				it[title] = book.title
				it[subtitle] = book.subtitle
				it[summary] = book.summary
				it[isbn] = book.isbn.isbn
				it[type] = book.type.name
				it[publishedDate] = book.publishedDate
			}
		}

		val actualBook = transaction(db) {
			val actualBook = BookTable.select { BookTable.id eq bookId }.map {
				BookTableModel(
					it[BookTable.title],
					it[BookTable.subtitle],
					it[BookTable.summary],
					ISBN(it[BookTable.isbn]),
					BookType.valueOf(it[BookTable.type]),
					it[BookTable.publishedDate]
				)
			}.first()

			actualBook
		}

		actualBook shouldBe book
	}
})