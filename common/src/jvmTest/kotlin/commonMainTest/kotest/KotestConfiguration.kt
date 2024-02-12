package commonMainTest.kotest

import io.kotest.core.config.AbstractProjectConfig

@Suppress("unused")
object KotestConfiguration : AbstractProjectConfig() {
	override val parallelism = 8
}