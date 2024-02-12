object Deps {
	object Plugins {
		object Versions {
			// https://github.com/JetBrains/kotlin/releases
			const val KOTLIN = "1.7.20"
			// https://github.com/Kotlin/kotlinx-kover/releases
			const val KOVER = "0.5.1"

			const val COMPOSE = "1.4.0"


		}

		const val COMPOSE = "org.jetbrains.compose"

		const val KOVER = "org.jetbrains.kotlinx.kover"
	}

	private object Versions {
		// https://mvnrepository.com/artifact/org.jetbrains.kotlin-wrappers/kotlin-css
		const val KTOR_CSS = "1.0.0-pre.325-kotlin-1.6.10"
		// https://github.com/kotest/kotest/releases
		const val KOTEST = "5.5.1"

		// https://github.com/ktorio/ktor/releases
		const val KTOR = "2.0.1"

		// https://github.com/JetBrains/Exposed/releases
		const val EXPOSED = "0.38.2"

		// https://github.com/xerial/sqlite-jdbc/releases
		const val SQLITE = "3.36.0.3"

		// https://github.com/Kotlin/kotlinx.coroutines
		const val Coroutines = "1.6.2"

		// KOTLIN_CSV
		// https://github.com/doyaaaaaken/kotlin-csv
		const val KOTLIN_CSV = "1.6.0"
	}

	// KOTEST
	const val KOTEST_API = "io.kotest:kotest-framework-api:${Versions.KOTEST}"
	const val KOTEST = "io.kotest:kotest-runner-junit5:${Versions.KOTEST}"
	const val KOTEST_ASSERT = "io.kotest:kotest-assertions-core:${Versions.KOTEST}"

	// KTOR
	const val KTOR_SERVER = "io.ktor:ktor-server-core:${Versions.KTOR}"
	const val KTOR_HTML_BUILDER = "io.ktor:ktor-server-html-builder:${Versions.KTOR}"
	const val KTOR_DEFAULT_HEADERS = "io.ktor:ktor-server-default-headers:${Versions.KTOR}"
	const val KTOR_AUTH = "io.ktor:ktor-server-auth:${Versions.KTOR}"
	const val KTOR_NETTY = "io.ktor:ktor-server-netty:${Versions.KTOR}"
	const val KTOR_TEST = "io.ktor:ktor-server-tests:${Versions.KTOR}"
	const val KTOR_CSS = "org.jetbrains.kotlin-wrappers:kotlin-css:${Versions.KTOR_CSS}"

	// Exposed
	const val EXPOSED = "org.jetbrains.exposed:exposed-core:${Versions.EXPOSED}"
	const val EXPOSED_DAO = "org.jetbrains.exposed:exposed-dao:${Versions.EXPOSED}"
	const val EXPOSED_JDBC = "org.jetbrains.exposed:exposed-jdbc:${Versions.EXPOSED}"

	// SQLite
	const val SQLITE = "org.xerial:sqlite-jdbc:${Versions.SQLITE}"

	// Coroutines
	const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines}"

	// Kotlin_CSV
	const val KOTLIN_CSV = "com.github.doyaaaaaken:kotlin-csv-jvm:${Versions.KOTLIN_CSV}"
}