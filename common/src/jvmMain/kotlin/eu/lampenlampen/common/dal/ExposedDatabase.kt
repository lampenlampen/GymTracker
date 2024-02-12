package eu.lampenlampen.common.dal

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

interface IExposedDatabase {
	val path: String
	val db: Database
}

class ExposedDatabase(override val path: String = "./data.db") : IExposedDatabase {
	override val db by lazy {
		val db = Database.connect("jdbc:sqlite:$path", "org.sqlite.JDBC")

		transaction(db) {
			addLogger(StdOutSqlLogger)

			SchemaUtils.create(
				BookTable,
				AuthorTable,
				BookAuthorTable,
				BookSeriesTable,
				BookBookSeriesTable,
				CategoryTable,
				BookCategoryTable,
				PublisherTable,
				BookPublisherTable
			)
		}

		return@lazy db
	}
}