package eu.lampenlampen.common.dataModels

import java.util.*

data class Author(
	val id: UUID,
	val firstname: String,
	val lastname: String,
	val externalId: String? = null
)