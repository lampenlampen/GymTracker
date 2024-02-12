package eu.lampenlampen.common.myLibrary

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class MyLibraryDBConnector {

	fun getData(path: String): MyLibraryData {
		val connString = "jdbc:sqlite:$path"
		var conn: Connection? = null

		try {
			conn = DriverManager.getConnection(connString)
		} catch (ex: SQLException) {
			// TODO
		}

		if (conn == null) throw IllegalStateException("Connection must be nonnull")

		conn.use {
			val books = getBooks(it)
			val authors = getAuthors(it)

			return MyLibraryData(books, authors)
		}
	}

	private fun getBooks(conn: Connection): List<MyLibraryBookModel> {
		val sql = "SELECT * FROM BOOK"
		val stmt = conn.createStatement()
		val resultSet = stmt.executeQuery(sql)

		return convertResultSetToBookModel(resultSet)
	}

	private fun getAuthors(conn: Connection): List<MyLibraryAuthorModel> {
		val sql = "SELECT * FROM AUTHOR"
		val stmt = conn.createStatement()
		val resultSet = stmt.executeQuery(sql)

		return convertResultSetToAuthorModel(resultSet)
	}

	private fun convertResultSetToBookModel(rs: ResultSet): List<MyLibraryBookModel> {
		val books = mutableListOf<MyLibraryBookModel>()

		while (rs.next()) {
			val authors = mutableListOf<Int>()
			authors.add(rs.getInt(MyLibraryDBSchema.BookSchema.AUTHOR))
			authors.addAll(
				rs.getString(MyLibraryDBSchema.BookSchema.ADDITIONAL_AUTHORS)
					.drop(1)
					.dropLast(1)
					.split(',')
					.map { it.toIntOrNull() }
					.filterNotNull())

			val categories = mutableListOf<String>()
			categories.addAll(
				rs.getString(MyLibraryDBSchema.BookSchema.CATEGORIES)
					.drop(1)
					.dropLast(1)
					.split(',')
			)

			val read = when (rs.getString(MyLibraryDBSchema.BookSchema.READ)) {
				"True" -> true
				"False" -> false
				else -> false
			}

			val a = rs.getString(MyLibraryDBSchema.BookSchema.SERIES)
			val series: MyLibrarySeriesModel? = if (a == null) null
			else {
				Json.decodeFromString<MyLibrarySeriesModel>(a.drop(1).dropLast(1))
			}

			books.add(
				MyLibraryBookModel(
					authors,
					rs.getString(MyLibraryDBSchema.BookSchema.TITLE),
					series,
					categories,
					rs.getString(MyLibraryDBSchema.BookSchema.PUBLISHED_DATE),
					rs.getString(MyLibraryDBSchema.BookSchema.PUBLISHER),
					rs.getInt(MyLibraryDBSchema.BookSchema.PAGES),
					rs.getString(MyLibraryDBSchema.BookSchema.ISBN),
					read,
					rs.getString(MyLibraryDBSchema.BookSchema.COMMENTS),
					rs.getString(MyLibraryDBSchema.BookSchema.SUMMARY),
					rs.getInt(MyLibraryDBSchema.BookSchema.ID),
					rs.getString(MyLibraryDBSchema.BookSchema.AMAZON_URL),
					rs.getString(MyLibraryDBSchema.BookSchema.COVER_PATH),
					rs.getInt(MyLibraryDBSchema.BookSchema.WISHLIST)
				)
			)
		}

		return books
	}

	private fun convertResultSetToAuthorModel(rs: ResultSet): List<MyLibraryAuthorModel> {
		val authors = mutableListOf<MyLibraryAuthorModel>()

		while (rs.next()) {
			authors.add(
				MyLibraryAuthorModel(
					rs.getInt(MyLibraryDBSchema.AuthorSchema.ID),
					rs.getString(MyLibraryDBSchema.AuthorSchema.FIRSTNAME),
					rs.getString(MyLibraryDBSchema.AuthorSchema.LASTNAME)
				)
			)
		}

		return authors
	}
}

object MyLibraryDBSchema {
	object AuthorSchema {
		const val ID = "ID"
		const val FIRSTNAME = "FIRSTNAME"
		const val LASTNAME = "LASTNAME"
	}

	object BookSchema {
		const val ID = "ID"
		const val AUTHOR = "AUTHOR"
		const val ADDITIONAL_AUTHORS = "ADDITIONAL_AUTHORS"
		const val CATEGORIES = "CATEGORIES"
		const val COMMENTS = "COMMENTS"
		const val WISHLIST = "IN_WISHLIST"
		const val ISBN = "ISBN"
		const val PAGES = "PAGES"
		const val PUBLISHED_DATE = "PUBLISHED_DATE"
		const val PUBLISHER = "PUBLISHER"
		const val COVER_PATH = "COVER_PATH"
		const val READ = "READ"
		const val SUMMARY = "SUMMARY"
		const val TITLE = "TITLE"
		const val SERIES = "SERIES"
		const val AMAZON_URL = "AMAZON_URL"
	}
}