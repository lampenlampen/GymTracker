package eu.lampenlampen.common.myLibrary

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize

class PrivateMyLibraryDBConnectorTest : FunSpec({

	test("getData") {
		val path = "C:\\Users\\felix\\Development\\BookLibrary\\common\\src\\jvmTest\\resources\\private\\mylibrary.db"
		val myLibraryData = MyLibraryDBConnector().getData(path)
		myLibraryData.books shouldHaveSize 238
		myLibraryData.authors shouldHaveSize 206
	}
})