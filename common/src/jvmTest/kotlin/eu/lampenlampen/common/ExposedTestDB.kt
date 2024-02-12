package eu.lampenlampen.common

import eu.lampenlampen.common.dal.ExposedDatabase
import eu.lampenlampen.common.dal.IExposedDatabase
import io.kotest.core.spec.AutoCloseable
import java.io.File
import java.util.*

class ExposedTestDB(override val path: String = "./${UUID.randomUUID()}.db") : IExposedDatabase, AutoCloseable {

	private val _db = ExposedDatabase(path)

	override val db = _db.db

	override fun close() {
		File(path).delete()
	}
}