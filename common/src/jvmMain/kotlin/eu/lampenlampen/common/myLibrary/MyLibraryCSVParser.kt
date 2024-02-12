package eu.lampenlampen.common.myLibrary

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.InputStream

class MyLibraryCSVParser {

	fun parse(stream: InputStream): List<MyLibraryCSVModel> = csvReader { delimiter = ';' }.readAll(stream)
		.drop(1)        // first line are headers
		.map {
			MyLibraryCSVModel(
				it[0].split(';'),
				it[1],
				it[2].ifBlank { null },
				if (it[3].isNotBlank()) it[3].split(',') else emptyList(),
				it[4].ifBlank { null },
				it[5],
				it[6].toIntOrNull(),
				it[7].ifBlank { null },
				if (it[8] == "Yes") true else if (it[8] == "No") false else null,
				it[9].ifBlank { null },
				it[10].ifBlank { null },
				it[11].ifBlank { null },
			)
		}
}