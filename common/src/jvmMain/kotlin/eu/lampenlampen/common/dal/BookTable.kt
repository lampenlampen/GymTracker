package eu.lampenlampen.common.dal

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Table
import java.util.*

object BookTable : IdTable<UUID>() {
	override val id = uuid("id").entityId()
	val title = text("title")
	val subtitle = text("subtitle").nullable()
	val summary = text("summary").nullable()
	val isbn = varchar("isbn", 50)
	val type = varchar("type", 50)
	val publishedDate = varchar("published_date", 50)
	val pages = integer("pages").nullable()
	val read = bool("read").nullable()
	val externalId = text("external_id").nullable()
}

object AuthorTable : IdTable<UUID>() {
	override val id = uuid("id").entityId()
	val firstname = text("firstname")
	val lastname = text("lastname")
	val externalId = text("external_id").nullable()
}

object BookAuthorTable : Table() {
	val book = reference("book_id", BookTable)
	val author = reference("author_id", AuthorTable)
	override val primaryKey = PrimaryKey(book, author)
}

object BookSeriesTable : IdTable<UUID>() {
	override val id = uuid("id").entityId()
	val name = text("name").uniqueIndex()
}

object BookBookSeriesTable : Table() {
	val book = reference("book_id", BookTable)
	val bookSeries = reference("bookSeries_id", BookSeriesTable)
	val volume = integer("volume")
	override val primaryKey = PrimaryKey(book, bookSeries, volume)
}

object CategoryTable : IdTable<UUID>() {
	override val id = uuid("id").entityId()
	val category = text("category")
}

object BookCategoryTable : Table() {
	val book = reference("book_id", BookTable)
	val category = reference("category_id", CategoryTable)
	override val primaryKey = PrimaryKey(book, category)
}

object PublisherTable : IdTable<UUID>() {
	override val id = uuid("id").entityId()
	val publisher = text("publisher").uniqueIndex()
}

object BookPublisherTable : Table() {
	val book = reference("book_id", BookTable)
	val publisher = reference("publisher_id", PublisherTable)
	override val primaryKey = PrimaryKey(book, publisher)
}