package eu.lampenlampen.common.myLibrary

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize

class PrivateMyLibraryCSVParserTests : FunSpec({
	test("parse testCSV") {
		val testCSV =
			"Authors;Title;Series;Categories;Published date;Publisher;Pages;ISBN;Read;Reading periods;Comments;Summary\n" +
					"Aaronovitch, Ben;Die Flüsse von London;Peter Grant (volume 1);;2013;dtv;478;9783423213417;No;;;Können Sie beweisen, dass sie tot sind?\n"

		val expectedBook = MyLibraryCSVModel(
			listOf("Aaronovitch, Ben"),
			"Die Flüsse von London",
			"Peter Grant (volume 1)",
			emptyList(),
			"2013",
			"dtv",
			478,
			"9783423213417",
			false,
			"",
			"",
			"Können Sie beweisen, dass sie tot sind?"
		)

		val parser = MyLibraryCSVParser()
		val books = parser.parse(testCSV.byteInputStream())

		books shouldHaveSingleElement expectedBook
	}

	test("Read whole file") {
		val stream = ClassLoader.getSystemResourceAsStream(".\\private\\MyLibraryByAuthor.csv")

		val parser = MyLibraryCSVParser()
		val books = parser.parse(stream)

		books shouldHaveSize 238
	}

})