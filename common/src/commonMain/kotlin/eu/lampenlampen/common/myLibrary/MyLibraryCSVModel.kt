package eu.lampenlampen.common.myLibrary

import kotlinx.serialization.Serializable

data class MyLibraryCSVModel(
	val authors: List<String>,
	val title: String,
	val series: String?,
	val categories: List<String>,
	val publishedDate: String?,
	val publisher: String,
	val pages: Int?,
	val isbn: String?,
	val read: Boolean?,
	val readingPeriods: String?,
	val comments: String?,
	val summary: String?
)

data class MyLibraryBookModel(
	val authorIds: List<Int>,
	val title: String,
	val series: MyLibrarySeriesModel?,
	val categories: List<String>?,
	val publishedDate: String?,
	val publisher: String,
	val pages: Int?,
	val isbn: String,
	val read: Boolean,
	val comments: String?,
	val summary: String?,
	val myLibraryId: Int,
	val amazonUrl: String?,
	val coverPath: String?,
	val wishlist: Int
)

data class MyLibraryAuthorModel(
	val myLibraryId: Int,
	val firstname: String?,
	val lastname: String
)

@Serializable
data class MyLibrarySeriesModel(
	val title: String,
	val volume: Double?
)